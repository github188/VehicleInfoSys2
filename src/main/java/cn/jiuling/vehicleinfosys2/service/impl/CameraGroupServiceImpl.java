package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.CameraGroupDao;
import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.model.CameraGroup;
import cn.jiuling.vehicleinfosys2.service.CameraGroupService;
import cn.jiuling.vehicleinfosys2.vo.CameraVO;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2015/9/25.
 */
@Service("cameraGroupService")
public class CameraGroupServiceImpl implements CameraGroupService {

    @Resource
    private CameraGroupDao cameraGroupDao;

    @Override
    public void save(CameraGroup cameraGroup) {
        cameraGroupDao.save(cameraGroup);
    }

    @Override
    public Pager list(CameraGroup cameraGroup, Integer page, Integer rows) {
        return cameraGroupDao.list(cameraGroup, page, rows);
    }

    @Override
    public CameraGroup findByGroupId(Integer cameraGroupId) {
        return cameraGroupDao.find(cameraGroupId);
    }

    @Override
    public CameraVO responseCamVo(Camera c) {
        CameraVO cameraVO = new CameraVO();
        try {
            BeanUtils.copyProperties(cameraVO, c);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        if(c.getCameraGroupId() != null) {

            CameraGroup cameraGroup = cameraGroupDao.find(c.getCameraGroupId());

            cameraVO.setCameraGroupName(cameraGroup.getName());
        }

        return cameraVO;
    }
}
