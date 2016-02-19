package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.ServerInfoDao;
import cn.jiuling.vehicleinfosys2.service.ServerInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 */
@SuppressWarnings( { "unused", "unchecked" })
@Service("serverInfoService")
public class ServerInfoServiceImpl implements ServerInfoService {

    @Resource
    private ServerInfoDao serverInfoDao;

    @Override
    public List findByIds(String ids) {
        List list = serverInfoDao.findByIds(ids);
        return list;
    }
}
