package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.model.CameraGroup;
import cn.jiuling.vehicleinfosys2.vo.CameraVO;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * Created by Administrator on 2015/9/25.
 */
public interface CameraGroupService {
    void save(CameraGroup cameraGroup);

    Pager list(CameraGroup cameraGroup, Integer page, Integer rows);

    CameraGroup findByGroupId(Integer cameraGroupId);

    /**
     * 根据camera返回cameraVo
     * @param c
     * @return
     */
    CameraVO responseCamVo(Camera c);
}
