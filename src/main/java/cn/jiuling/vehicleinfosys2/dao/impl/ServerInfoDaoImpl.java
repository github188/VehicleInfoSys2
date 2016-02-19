package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.ServerInfoDao;
import cn.jiuling.vehicleinfosys2.framework.spring.support.CustomerContextHolder;
import cn.jiuling.vehicleinfosys2.model.Server_Info;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 */
@Repository("serverInfoDao")
public class ServerInfoDaoImpl extends BaseDaoImpl<Server_Info> implements ServerInfoDao {

    @Override
    public List findByIds(String ids) {

        StringBuilder sb = new StringBuilder("from Server_Info s where s.server_id in("+83+")");
        List list = super.getHibernateTemplate().find(sb.toString());
        //查找完成清除oracle数据源
        CustomerContextHolder.clearCustomerType();
        return list;
    }
}
