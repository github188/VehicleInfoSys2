package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.Bayonet_VehiclePassDao;
import cn.jiuling.vehicleinfosys2.service.Bayonet_VehiclePassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 */

@SuppressWarnings( { "unused", "unchecked" })
@Service("bayonet_VehiclePassService")
public class Bayonet_VehiclePassServiceImpl implements Bayonet_VehiclePassService {
    @Resource
    private Bayonet_VehiclePassDao bayonet_vehiclePassDao;

    @Override
    public List list() {
        return bayonet_vehiclePassDao.findByNow();
    }

    @Override
    public List findServerVehiclePassVo() {
        return bayonet_vehiclePassDao.findServerVehicleVO();
    }
}
