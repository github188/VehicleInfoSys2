package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.service.AerialMammalVehicleService;
import cn.jiuling.vehicleinfosys2.vo.AerialMammalVehicleQuery;
import cn.jiuling.vehicleinfosys2.vo.AerialMammalVehicleVo;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/8.
 */
@SuppressWarnings({"unused", "unchecked"})
@Service("aerialMammalVehicleService")
public class AerialMammalVehicleServiceImpl implements AerialMammalVehicleService {

    @Resource
    private VlprResultDao vlprResultDao;

    @Override
    public List query(AerialMammalVehicleQuery aq) {
        //夜间出现次数
        Integer nightAppearNum = aq.getAppearNum();

        ResultQuery rq = new ResultQuery();
        rq.setStartTime(aq.getStartTime());
        rq.setEndTime(aq.getEndTime());
        List list = vlprResultDao.queryAerialMammalVehicle(rq,aq.getAppearNum(),aq.getNightStartTime(),aq.getNightEndTime());
        int size = list.size();
        List resultList = new ArrayList();
        for (int i=0; i < size; i++) {
            Object[] item = (Object[]) list.get(i);
            String license = (String) item[0];
            Integer nightAppearCount = Integer.valueOf(item[1].toString());
            Integer dayAppearNum = Integer.valueOf(item[2]==null?"0":item[2].toString());
            AerialMammalVehicleVo aerialMammalVehicleVo = new AerialMammalVehicleVo();
            aerialMammalVehicleVo.setLicense(license);
            aerialMammalVehicleVo.setNightAppearNum(nightAppearCount);
            aerialMammalVehicleVo.setDayAppearNum(dayAppearNum);
            resultList.add(aerialMammalVehicleVo);
        }

        return resultList;
    }
}
