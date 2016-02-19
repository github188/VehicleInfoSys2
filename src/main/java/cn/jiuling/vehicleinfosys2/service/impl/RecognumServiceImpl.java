//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.RecognumDao;
import cn.jiuling.vehicleinfosys2.service.RecognumService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("recognumService")
public class RecognumServiceImpl implements RecognumService {

    private static final Logger log = Logger.getLogger(RecognumServiceImpl.class);
    @Resource
    private RecognumDao recognumDao;

    @Override
    public boolean checkRecognum() {
        return recognumDao.checkRecognum();
    }
}
