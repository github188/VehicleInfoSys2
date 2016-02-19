package cn.jiuling.vehicleinfosys2.timerTask;

import cn.jiuling.vehicleinfosys2.dao.VlprFirstIntoPosInfoDao;
import cn.jiuling.vehicleinfosys2.model.VlprFirstIntoPosInfo;
import cn.jiuling.vehicleinfosys2.service.VlprFirstIntoCityResultService;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * 首次入城分析定时任务
 * @author wsy
 *
 */
@Component("firstTimeIntoCityTimerTask")
public class FirstTimeIntoCityTimerTask {

    private Logger log = Logger.getLogger(FirstTimeIntoCityTimerTask.class);

    @Resource
    private VlprFirstIntoCityResultService vlprFirstIntoCityResultService;
    @Resource
    private VlprFirstIntoPosInfoDao vlprFirstIntoPosInfoDao;

    public static String localPlatePre = PropertiesUtils.get("localPlatePre");

    long count = 0;

    @Scheduled(cron = "*/2 * * * * ? ")
	public void firstTimeIntoCityAnalyse(){

        String timerSwitch = PropertiesUtils.get("firstTimeIntoCityTimerTask");

        if (timerSwitch.equalsIgnoreCase(Constant.TIMER_POWER_ON)) {
            System.out.println("首次入城后台定时任务进行中。。。"+(count=count+1));

            if(count < 2){
                //预处理数据
                List positionInfoPage = vlprFirstIntoPosInfoDao.getAll();
                int size = positionInfoPage.size();
                if(size < 1){
                    VlprFirstIntoPosInfo vlprFirstIntoPosInfo = new VlprFirstIntoPosInfo();
                    vlprFirstIntoPosInfo.setThreadPosition(0L);
                    vlprFirstIntoPosInfoDao.save(vlprFirstIntoPosInfo);
                }
            }
            List list = vlprFirstIntoCityResultService.queryNonLocalPlateByCurrentTime(localPlatePre);
            int size = list.size();
            if (list != null && size > 0) {
                log.info("发现" + size + "辆异地车。");
                vlprFirstIntoCityResultService.save(list);
            }
        }
	}
}
