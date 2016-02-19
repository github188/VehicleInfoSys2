package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.VehicleLoggerDao;
import cn.jiuling.vehicleinfosys2.model.VehicleLogger;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.VehicleLoggerQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2015/12/3.
 */
@Repository("vehicleLoggerDao")
public class VehicleLoggerDaoImpl extends BaseDaoImpl<VehicleLogger> implements VehicleLoggerDao {
    @Override
    public Pager query(VehicleLoggerQuery vehicleLoggerQuery, Integer page, Integer rows) {
        StringBuilder sb = new StringBuilder();

        String account = vehicleLoggerQuery.getAccount();
        if (!StringUtils.isEmpty(account)) {
            account = account.replaceAll("\\*", "%").replaceAll("\\?", "_");
            sb.append(" and account like '%" + account + "%'");
        }
        String operation = vehicleLoggerQuery.getOperation();
        if (!StringUtils.isEmpty(operation)) {
            operation = operation.replaceAll("\\*", "%").replaceAll("\\?", "_");
            sb.append(" and operation like '%" + operation + "%'");
        }
        Timestamp start = vehicleLoggerQuery.getStartTime();
        if (null != start) {
            sb.append(" and createdate>='" + start + "'");
        }
        Timestamp end = vehicleLoggerQuery.getEndTime();
        if (null != end) {
            sb.append(" and createdate<='" + end + "'");
        }

        sb.append(" order by createdate desc");

        Pager p = super.list(new VehicleLogger(),page,rows,sb.toString());

        return p;
    }
}
