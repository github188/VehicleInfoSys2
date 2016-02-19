package cn.jiuling.vehicleinfosys2.timerTask;

import cn.jiuling.vehicleinfosys2.service.ResultService;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.util.FTPUtils;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 数据上传定时任务
 *
 * @author daixiaowei
 */
@Component("ftpUploadTimerTask")
public class FTPUploadTimerTask {

    //获取当前时间
    public static Date now = new Date();

    protected Logger log = Logger.getLogger(FTPUploadTimerTask.class);
    @Resource
    private ResultService resultService;
    long count = 0;

    @Scheduled(cron = "*/2 * * * * ? ")
    public void ftpUploadFile() {

        String timerSwitch = PropertiesUtils.get("fTPUploadTimerTask");
        if (timerSwitch.equalsIgnoreCase(Constant.TIMER_POWER_ON)) {
            System.out.println("上传图片到FTP服务器定时任务......" + (count = count + 1));
            ResultQuery resultQuery = new ResultQuery();
            resultQuery.setStartTime(new Timestamp(now.getTime()));

            //查询识别结果
            Pager pager = resultService.queryMunityResult(resultQuery);
            List list = pager.getRows();

            if (list.size() == 0) {
                log.info("================当前还没有新图片！==================");
            } else {
                //更新最后查询的时间
                ResultVo r = (ResultVo)list.get(list.size() - 1);
                now = r.getResultTime();

                for (Object o : list) {
                    ResultVo resultVo = (ResultVo) o;
                    File file = new File(resultVo.getImagePath());
                    //从配置文件中读取ftp用户名、密码、IP地址和端口
                    String username = PropertiesUtils.get("ftpUserName");
                    String password = PropertiesUtils.get("ftpPassWord");
                    String ip = PropertiesUtils.get("ftpIP");
                    Integer port = Integer.parseInt(PropertiesUtils.get("ftpPort"));

                    //ftp存储路径
                    String uploadDir = PropertiesUtils.get("uploadDir");

                    //连接ftp，生成客户端并开始上传
                    FTPUtils ftpUtils = new FTPUtils(username, password, ip, port);
                    ftpUtils.getFTPClient();
                    log.info("开始上传......");
                    boolean isOK = ftpUtils.uploadFile(file, uploadDir);
                    if (isOK) {
                        log.info("上传成功！");
                    } else {
                        log.info("上传失败！");
                    }
                }
            }
        }
    }
}
