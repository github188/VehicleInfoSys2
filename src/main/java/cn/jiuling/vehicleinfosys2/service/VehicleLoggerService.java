package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.VehicleLogger;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.VehicleLoggerQuery;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2015/12/2.
 */
public interface VehicleLoggerService {
    /**
     * 日志记录
     * @param log
     */
    @Transactional
    public void log(VehicleLogger log);

    /**
     * 获取登录管理员账户
     */
    public String loginUserName();

    Pager query(VehicleLoggerQuery vehicleLoggerQuery, Integer page, Integer rows);

    Pager list(VehicleLogger vehicleLogger, Integer page, Integer rows);
    void add(VehicleLogger vehicleLogger);
}
