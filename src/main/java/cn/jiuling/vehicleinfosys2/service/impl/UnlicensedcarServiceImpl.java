package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.UnlicensedcarDao;
import cn.jiuling.vehicleinfosys2.service.UnlicensedcarService;
import cn.jiuling.vehicleinfosys2.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("unlicensedcarService")
public class UnlicensedcarServiceImpl implements UnlicensedcarService {

    @Resource
    private UnlicensedcarDao unlicensedcarDao;

    @Override
    public Pager query(ResultQuery rq, Integer page, Integer rows) {
        return unlicensedcarDao.query(rq, page, rows);
    }
}
