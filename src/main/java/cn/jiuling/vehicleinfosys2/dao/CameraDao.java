package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.vo.CameraQuery;
import cn.jiuling.vehicleinfosys2.vo.Pager;

import java.util.List;

public interface CameraDao extends BaseDao<Camera> {

	public Pager queryCamera(CameraQuery c, Integer page, Integer rows);

	/**
	 * 通过监控点名称获取监控点信息
	 * @param cameraName 监控点名称
	 * @return
	 */
	public Camera findByName(String cameraName);

    List findByIds(String cameraIds);

    Pager queryByAreaIds(Camera c, StringBuilder areaIds, Integer page, Integer rows);
}
