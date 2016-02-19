package cn.jiuling.vehicleinfosys2.timerTask;

import cn.jiuling.vehicleinfosys2.framework.spring.support.CustomerContextHolder;
import cn.jiuling.vehicleinfosys2.service.Bayonet_VehiclePassService;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.util.HttpUtils;
import cn.jiuling.vehicleinfosys2.util.PathUtils;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.Server_VehiclePassVo;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 定时任务，进行一次扫描，如果有新的图片就下载下来
 * Created by Administrator on 2015/7/30.
 */

@Component("downloadRemoteImages")
public class DownloadRemoteImages {

    public static final String DATA_SOURCE_ID = "0";

    protected Logger log = Logger.getLogger(this.getClass());

    @Resource
    private Bayonet_VehiclePassService bayonetVehiclePassService;
    long count = 0;

    /**
     * 定时任务，每2s进行一次扫描，如果有新的图片就下载下来 (望城)
     */
    @Scheduled(cron = "*/2 * * * * ? ")
    public void downloadRealImage() {

        String timerSwitch = PropertiesUtils.get("downloadRemoteImages");

        if (timerSwitch.equalsIgnoreCase(Constant.TIMER_POWER_ON)) {
            log.info("开始实时图片下载任务......" + (count=count+1));
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCEY_ORACLE);
            //查找大于当前时间的所有过车信息
            List list = bayonetVehiclePassService.findServerVehiclePassVo();
            CustomerContextHolder.clearCustomerType();
            //保存文件地址
            StringBuilder destFileName = null;
            for (Object o : list) {
                destFileName = new StringBuilder(PathUtils.getVlprDataSrcPath());
                Server_VehiclePassVo sv = (Server_VehiclePassVo) o;
                String ipAddr = sv.getIp_addr();
                Integer hppPort = sv.getHpp_port();
                String fileName = sv.getPicFullView();
                String picFthPath = sv.getPicFTPPath();

                //组装下载地址
                StringBuilder imageUrl = new StringBuilder("http://").append(ipAddr)
                        .append(":").append(hppPort).append(picFthPath);
                destFileName.append(File.separator + sv.getCrossLsh()).append(File.separator + DATA_SOURCE_ID)
                        .append(File.separator + "todo" + File.separator + fileName);
                HttpUtils imageDownloader = new HttpUtils();
                imageDownloader.initApacheHttpClient();
                try {
                    log.info("开始下载图片......");
                    imageDownloader.fetchContent(imageUrl.toString(), destFileName.toString());
                    imageDownloader.destroyApacheHttpClient();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
