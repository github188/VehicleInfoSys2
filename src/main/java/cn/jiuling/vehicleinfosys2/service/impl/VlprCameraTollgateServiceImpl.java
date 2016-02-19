package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.VlprCameraTollgateDao;
import cn.jiuling.vehicleinfosys2.model.VlprCameraTollgate;
import cn.jiuling.vehicleinfosys2.service.VlprCameraTollgateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("vlprCameraTollgateService")
public class VlprCameraTollgateServiceImpl implements VlprCameraTollgateService {

    @Resource
    private VlprCameraTollgateDao vctDao;

    @Override
    public List getAll() {
        return vctDao.getAll();
    }

    @Override
    public VlprCameraTollgate findByCameraId(Integer cameraId) {
        return vctDao.findByCameraId(cameraId);
    }

    @Override
    public List queryTollgateCodesByCameraIds(Integer[] cameraIds) {
        return vctDao.queryTollgateCodesByCameraIds(cameraIds);
    }
}
