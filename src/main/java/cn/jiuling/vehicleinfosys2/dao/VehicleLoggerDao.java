package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VehicleLogger;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.VehicleLoggerQuery;

/**
 * Created by Administrator on 2015/12/3.
 */
public interface VehicleLoggerDao extends BaseDao<VehicleLogger> {

    Pager query(VehicleLoggerQuery vehicleLoggerQuery, Integer page, Integer rows);


}
