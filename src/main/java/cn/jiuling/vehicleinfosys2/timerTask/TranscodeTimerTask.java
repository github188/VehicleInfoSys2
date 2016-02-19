package cn.jiuling.vehicleinfosys2.timerTask;

import cn.jiuling.vehicleinfosys2.model.UserUploadVideo;
import cn.jiuling.vehicleinfosys2.service.ResourceService;
import cn.jiuling.vehicleinfosys2.service.UserUploadVideoService;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.util.FileUtils;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * 视频转码定时器--监控转码是否已完成
 *
 * @author wsy
 */
@Component("transcodeTimerTask")
public class TranscodeTimerTask {

    protected Logger log = Logger.getLogger(TranscodeTimerTask.class);

    @Resource
    private UserUploadVideoService userUploadVideoService;
    @Resource
    private ResourceService resourceService;
    long count = 0;

    @Scheduled(cron = "*/1 * * * * ? ")
    public void transIsFinished() {
        String timerSwitch = PropertiesUtils.get("transcodeTimerTask");
        if (timerSwitch.equalsIgnoreCase(Constant.TIMER_POWER_ON)) {
            System.out.println("监控转码进度定时器" + (count = count + 1));
            List list = userUploadVideoService.queryUploadVideo();
            if (list != null && list.size() != 0) {
                for (Object o : list) {
                    UserUploadVideo u = (UserUploadVideo) o;

                    File file = FileUtils.transFileNameToGBK(u.getDestURL());

                    Integer status = u.getStatus();
                    Integer isAddedToDataSource = u.getIsAddedToDataSource();
                    Integer cameraId = u.getCameraId();
                    short dataType = cn.jiuling.vehicleinfosys2.vo.Constant.DATASOURCE_TYPE_VIDEO;

                    if (status == Constant.TRANSTASKMGR_STATUS_COMPLETED && isAddedToDataSource == Constant.ISADDEDTODATASOURCE_NO) {
                        try {
                            resourceService.upload(file, cameraId, dataType);

                            u.setIsAddedToDataSource(Constant.ISADDEDTODATASOURCE_YES);
                            userUploadVideoService.update(u);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
