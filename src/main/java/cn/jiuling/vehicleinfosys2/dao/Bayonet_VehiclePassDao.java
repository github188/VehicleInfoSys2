package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.Bayonet_VehiclePass;

import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 */
public interface Bayonet_VehiclePassDao extends BaseDao<Bayonet_VehiclePass> {
    public List<Bayonet_VehiclePass> findByNow();

    List findServerVehicleVO();
}
