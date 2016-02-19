package cn.jiuling.vehicleinfosys2.timerTask;

import javax.annotation.Resource;

import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.jiuling.vehicleinfosys2.dao.VlprAppearanceDao;
import cn.jiuling.vehicleinfosys2.model.VlprAppearance;
import cn.jiuling.vehicleinfosys2.service.FakeLicenseService;
import cn.jiuling.vehicleinfosys2.vo.Pager;
/**
 * 
 * 假套牌车分析定时任务
 * @author daixiaowei
 *
 */
@Component("fakeLicenseCarTimerTask")
public class FakeLicenseCarTimerTask {
	
	@Resource
	private FakeLicenseService fakeLicenseService;
	
	@Resource
	private VlprAppearanceDao vlprAppearanceDao;
	
	long count = 0;

    @Scheduled(cron = "*/2 * * * * ? ")
	public void analyzeFakeLicenserCar(){

        String timerSwitch = PropertiesUtils.get("fakeLicenseCarTimerTask");

        if (timerSwitch.equalsIgnoreCase(Constant.TIMER_POWER_ON)) {

            System.out.println("假套牌车辆分析后台定时任务进行中。。。"+(count=count+1));
            if(count < 2){
                //预处理数据
                Pager positionInfoPage = vlprAppearanceDao.querAppearance();
                if(positionInfoPage.getTotal()<1){
                    VlprAppearance vlprAppearance = new VlprAppearance();
                    vlprAppearance.setPagePosition(0L);
                    vlprAppearance.setThreadPosition(0L);
                    vlprAppearanceDao.save(vlprAppearance);
                }
            }

            //假套牌车辆分析
            fakeLicenseService.saveFakeLicenseAnalyze();
        }
	}
}
