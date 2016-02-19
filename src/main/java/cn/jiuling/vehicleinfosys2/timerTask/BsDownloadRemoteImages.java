package cn.jiuling.vehicleinfosys2.timerTask;

import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.model.VlprCameraTollgate;
import cn.jiuling.vehicleinfosys2.model.VlprCollectPictures;
import cn.jiuling.vehicleinfosys2.service.CameraService;
import cn.jiuling.vehicleinfosys2.service.CollectPicturesService;
import cn.jiuling.vehicleinfosys2.service.TblVehicleRecordService;
import cn.jiuling.vehicleinfosys2.service.VlprCameraTollgateService;
import cn.jiuling.vehicleinfosys2.util.*;
import cn.jiuling.vehicleinfosys2.vo.TblVehicleRecordVo;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 定时任务，进行一次扫描，如果有新的图片就下载下来(白山)
 * @author wangrb
 * @date 2015-11-25
 */
@Component("bsDownloadRemoteImages")
public class BsDownloadRemoteImages {

    public static final String DATA_SOURCE_ID = "0";

    protected Logger log = Logger.getLogger(this.getClass());

    @Resource
    private TblVehicleRecordService tblVehicleRecordService;
    @Resource
    private VlprCameraTollgateService vlprCameraTollgateService;
    @Resource
    private CollectPicturesService collectPicturesService;
    @Resource
    private CameraService cameraService;
    long count1 = 0;
    /**
     * 定时任务，每2s进行一次扫描，如果有新的图片就下载下来 (吉林白山)
     */
    @Scheduled(cron = "*/2 * * * * ? ")
    public void downloadRealImage1() {
        String timerSwitch = PropertiesUtils.get("downloadRemoteImages1");

        if (timerSwitch.equalsIgnoreCase(Constant.TIMER_POWER_ON)) {
            log.info("开始实时图片下载任务......" + (count1 = count1 + 1));

            List tdList = collectPicturesService.list(new Short[]{0,1}); //获取需要下载的任务信息
            if (tdList!=null && tdList.size() > 0) {
                for (Object td : tdList) {
                    VlprCollectPictures collectPictures = (VlprCollectPictures)td;

                    //通过任务信息查询对应的下载卡口信息
                    String cameraName = collectPictures.getCameraName();
                    Camera c = cameraService.findByName(cameraName);
                    VlprCameraTollgate cameraTollgate = vlprCameraTollgateService.findByCameraId(c.getId());

                    //如果没有找到对应的卡口号则默认状态：已完成
                    if(cameraTollgate == null){
                        collectPictures.setStatus((short) 2); //下载完成
                        collectPicturesService.update(collectPictures);
                        continue;
                    }

                    //当前时间小于采集图片起始时间
                    if(collectPictures.getStartTime()!=null && collectPictures.getStartTime().getTime()>new Date().getTime() ){
                        continue;
                    }

                    //获取过车信息，下载图片。。
                    List list = tblVehicleRecordService.findTblVehicleRecordVo(cameraTollgate.getTollgateId(), collectPictures.getMaxRecordId(),collectPictures.getStartTime(), collectPictures.getEndTime());
                    int downloadImgCount = 0; //记录每次任务下载图片的数量
                    if (list != null && list.size() > 0) {
                        List ctList = vlprCameraTollgateService.getAll(); //监控点与卡口的关系（集合）
                        for (Object o : list) {
                            TblVehicleRecordVo vrVo = (TblVehicleRecordVo) o;
                            String ipAddr = vrVo.getDevAddr();
                            String fileNameUrl = vrVo.getPic1Name();

                            //组装下载地址
                            StringBuilder imageUrl = new StringBuilder("http://").append(ipAddr).append(":8083").append(fileNameUrl);
                            int cameraId = 0;//监控点id
                            for (Object o1 : ctList) {
                                VlprCameraTollgate vct = (VlprCameraTollgate) o1;
                                if (vct.getTollgateId() != null && vrVo.getTollgateCode() != null && vct.getTollgateId().equals(vrVo.getTollgateCode())) {
                                    cameraId = vct.getCameraId();
                                    break;
                                }
                            }

                            //如果没有找到对应的监控点，则不进行下载
                            if (cameraId == 0) {
                                continue;
                            }

                            //获取本地存放地址
                            StringBuilder destFileName = new StringBuilder(PathUtils.getVlprDataSrcPath());
                            destFileName.append(File.separator + cameraId).append(File.separator + DATA_SOURCE_ID).append(File.separator + "todo");

                            //查看本地存放图片目录是否存在，不存在则创建
                            File folder = new File(destFileName.toString());
                            if (!(folder.exists() && folder.isDirectory())) {
                                folder.mkdirs();
                            }

                            String suffixName = fileNameUrl.substring(fileNameUrl.lastIndexOf(".")); //图片后缀名
                            if(suffixName.lastIndexOf("?") != -1 && suffixName.lastIndexOf("?")>suffixName.lastIndexOf(".")){
                                suffixName = suffixName.substring(suffixName.lastIndexOf("."), suffixName.lastIndexOf("?"));
                            }
                            destFileName.append(File.separator + vrVo.getRecordId() + "_" + DateUtils.formateTime3(vrVo.getPassTime()) + suffixName);

                            HttpUtils imageDownloader = new HttpUtils();
                            imageDownloader.initApacheHttpClient();
                            try {
                                log.info("开始下载图片......");
                                imageDownloader.fetchContent(imageUrl.toString(), destFileName.toString());
                                imageDownloader.destroyApacheHttpClient();
                                downloadImgCount++;
                            } catch (ClientProtocolException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        log.info("本次下载图片完毕，共下载 " + downloadImgCount + " 张！");

                        int count = collectPictures.getDownloadCount()+downloadImgCount;
                        collectPictures.setDownloadCount(count);

                        //获取过车信息的最大id和通车时间（用于控制当前任务下次下载以及记录采集进程）
                        TblVehicleRecordVo maxTrv =  ((TblVehicleRecordVo)list.get(list.size()-1));
                        long maxRecordId = maxTrv.getRecordId().longValue();
                        collectPictures.setMaxRecordId(maxRecordId);
                        Timestamp passTime = new Timestamp(maxTrv.getPassTime().getTime());
                        collectPictures.setPassTime(passTime);
                    }

                    if(collectPictures.getStartTime().getTime()<new Date().getTime() ){
                        if(collectPictures.getStatus() == 0){
                            collectPictures.setStatus((short) 1); //任务状态标记为：下载中
                        }
                    }

                    //如果当前任务下载的结束时间小于当前时间，且没有取到图片，则标记下载完毕
                    if(downloadImgCount==0 && collectPictures.getEndTime()!=null && collectPictures.getEndTime().getTime()<new Date().getTime() ){
                        collectPictures.setStatus((short) 2); //下载完成
                    }
                    collectPicturesService.updateRealCollectPictrue(collectPictures);  //更新
                }
            }
        }
    }
}
