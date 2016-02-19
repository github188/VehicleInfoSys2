package cn.jiuling.vehicleinfosys2.timerTask;

import cn.jiuling.vehicleinfosys2.service.ResultService;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.util.HttpUtils;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;

/**
 * 
 * 数据上传定时任务
 * @author daixiaowei
 *
 */
@Component("dataUploadTimerTask")
public class DataUploadTimerTask {

	protected Logger log = Logger.getLogger(DataUploadTimerTask.class);
	@Resource
	private ResultService resultService;
	long count = 0;

    @Scheduled(cron = "*/2 * * * * ? ")
	public void uploadFile(){
        String timerSwitch = PropertiesUtils.get("dataUploadTimerTask");
        if (timerSwitch.equalsIgnoreCase(Constant.TIMER_POWER_ON)) {
            log.info("开始数据上传定时任务......" + (count = count + 1));

            ResultQuery resultQuery = new ResultQuery();
            resultQuery.setStartTime(new Timestamp(System.currentTimeMillis()));

            String url = PropertiesUtils.get("changhaiUrl");
            String dataXML = resultService.queryMunityResult1(resultQuery);
            HttpUtils imageDownloader = new HttpUtils();
            imageDownloader.initApacheHttpClient();

            try {
                log.info("开始发送数据......" + count);
                String ret = imageDownloader.doPost(url,dataXML);
                if ("ret".equals("error")) {
                    log.info(ret);
                }
            } finally {
                imageDownloader.destroyApacheHttpClient();
            }
        }
	}
}
