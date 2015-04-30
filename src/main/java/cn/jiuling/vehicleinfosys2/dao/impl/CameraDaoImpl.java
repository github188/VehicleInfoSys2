package cn.jiuling.vehicleinfosys2.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jiuling.vehicleinfosys2.dao.CameraDao;
import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.vo.CameraQuery;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@Repository("cameraDao")
public class CameraDaoImpl extends BaseDaoImpl<Camera> implements CameraDao {
	@Override
	public Pager queryCamera(CameraQuery c, Integer page, Integer rows) {
		StringBuilder sb = new StringBuilder("from Camera c where c.isShowInGis=1 ");
		sb.append("and c.latitude between ");
		sb.append(c.getMinLat()).append(" and ").append(c.getMaxLat());
		sb.append(" and c.longitude between ");
		sb.append(c.getMinLng()).append(" and ").append(c.getMaxLng());
		Long total = count("select count(*) " + sb.toString(), null);
		List list = super.find(sb.toString(), null, page, rows);
		Pager p = new Pager(total, list);
		return p;
	}
}
