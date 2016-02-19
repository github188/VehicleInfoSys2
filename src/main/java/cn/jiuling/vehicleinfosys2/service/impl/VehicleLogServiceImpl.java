package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.VehicleLoggerDao;
import cn.jiuling.vehicleinfosys2.model.VehicleLogger;
import cn.jiuling.vehicleinfosys2.service.VehicleLoggerService;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.VehicleLoggerQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2015/12/2.
 */
@Service("vehicleLoggerService")
public class VehicleLogServiceImpl implements VehicleLoggerService {

    @Resource
    private VehicleLoggerDao vehicleLoggerDao;

    @Override
    public void log(VehicleLogger log) {
        vehicleLoggerDao.save(log);
    }

    @Override
    public String loginUserName() {
        return null;
    }

    @Override
    public Pager query(VehicleLoggerQuery vehicleLoggerQuery, Integer page, Integer rows) {
        return vehicleLoggerDao.query(vehicleLoggerQuery, page, rows);
    }

    @Override
    public Pager list(VehicleLogger vehicleLogger, Integer page, Integer rows) {
        return vehicleLoggerDao.list(vehicleLogger,page,rows);
    }

    @Override
    public void add(VehicleLogger vehicleLogger) {
        vehicleLoggerDao.save(vehicleLogger);
    }
}
