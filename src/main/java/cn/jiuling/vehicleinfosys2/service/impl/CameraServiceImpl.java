package cn.jiuling.vehicleinfosys2.service.impl;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.jiuling.vehicleinfosys2.dao.CameraDao;
import cn.jiuling.vehicleinfosys2.exception.ServiceException;
import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.service.CameraService;
import cn.jiuling.vehicleinfosys2.vo.CameraQuery;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@SuppressWarnings( { "unused", "unchecked" })
@Service("cameraService")
public class CameraServiceImpl implements CameraService {
	private Logger log = Logger.getLogger(CameraServiceImpl.class);
	@Resource
	private CameraDao cameraDao;

	private static final Short STATUS_VALID = 1;
	private static final Short STATUS_INVALID = 0;

	private static final Short TYPE_OTHER = 0;
	private static final Short TYPE_BALL = 1;
	private static final Short TYPE_GUN = 2;

	@Override
	public Pager query(CameraQuery c, Integer page, Integer rows) {
		return cameraDao.queryCamera(c, page, rows);
	}

	@Override
	public Pager list(Camera c, Integer page, Integer rows) {
		return cameraDao.list(c, page, rows);
	}

	@Override
	public void add(Camera c) {
		c.setStatus(STATUS_VALID);
		c.setType(TYPE_OTHER);
		cameraDao.save(c);
	}

	@Override
	public void remove(Camera c) {
		Camera _c = cameraDao.find(c.getId());
		cameraDao.delete(_c);
	}

	@Override
	public void update(Camera c) {
		cameraDao.update(c);
	}

	@Override
	public Boolean valideName(String name) {
		Object o = cameraDao.findByProperty("name", name);
		return o == null;
	}

	@Override
	public Boolean valideName(Integer id, String name) {
		List list = cameraDao.findByPropertyList("name", name);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Camera c = (Camera) iterator.next();
			// 当存在不同id和同名对象时,则校验不通过
			if (c.getId().intValue() != id.intValue() && c.getName().equals(name)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void saveCameras(MultipartFile cameraInfo) {
		List<String> cameras;
		String camera = "";
		String arr[];
		String name;
		Double longitude;
		Double latitude;
		Camera c;
		try {
			InputStream in = cameraInfo.getInputStream();
			cameras = IOUtils.readLines(in);
			for (Iterator iterator = cameras.iterator(); iterator.hasNext();) {
				camera = (String) iterator.next();
				arr = camera.split(",");
				name = arr[0];
				longitude = Double.valueOf(arr[1]);
				latitude = Double.valueOf(arr[2]);

				if (longitude < -180 || longitude > 180 || latitude < -90 || latitude > 90) {
					throw new ServiceException();
				}

				c = new Camera();
				c.setName(name);
				c.setLongitude(longitude);
				c.setLatitude(latitude);
				c.setStatus(STATUS_VALID);
				cameraDao.save(c);
			}
		} catch (Exception e) {
			throw new ServiceException("监控点数据有误:" + camera, e);
		}
	}

	@Override
	public Camera fingById(Integer cameraId) {
		return cameraDao.find(cameraId);
	}

	@Override
	public String getFilepath(Camera c) {
		String path = c.getUrl();
		return path;
	}
}
