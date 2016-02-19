package cn.jiuling.vehicleinfosys2.timerTask;

import cn.jiuling.vehicleinfosys2.dao.BaseDao;
import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.framework.spring.support.CustomerContextHolder;
import cn.jiuling.vehicleinfosys2.service.Bayonet_VehiclePassService;
import cn.jiuling.vehicleinfosys2.service.ResultService;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.util.HttpUtils;
import cn.jiuling.vehicleinfosys2.util.PathUtils;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;
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
 * 定时任务，每2s进行一次扫描，如果有新数据就上传
 * Created by Administrator on 2015/7/30.
 */

@Component("uploadDataTimerTask")
public class UploadDataTimerTask {

    protected Logger log = Logger.getLogger(this.getClass());

    @Resource
    private ResultService resultService;
    long count = 0;

    @Scheduled(cron = "*/2 * * * * ? ")
    public void uploadImage() {

        String timerSwitch = PropertiesUtils.get("uploadDataTimerTask");
        if (timerSwitch.equalsIgnoreCase(Constant.TIMER_POWER_ON)) {

            List list = resultService.queryByNow();
            log.info("开始图片上传任务......" + (count = count + 1));
            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCEY_ORACLE);
            for (int i = 0; i < list.size(); i++) {
                ResultVo r = (ResultVo) list.get(i);
                resultService.addToChangHai(r);
            }
            CustomerContextHolder.clearCustomerType();
        }
    }
}
