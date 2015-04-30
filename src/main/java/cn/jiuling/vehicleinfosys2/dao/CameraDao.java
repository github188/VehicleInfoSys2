package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.vo.CameraQuery;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface CameraDao extends BaseDao<Camera> {

	public Pager queryCamera(CameraQuery c, Integer page, Integer rows);

}
