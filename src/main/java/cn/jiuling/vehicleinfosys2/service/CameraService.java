package cn.jiuling.vehicleinfosys2.service;

import org.springframework.web.multipart.MultipartFile;

import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.vo.CameraQuery;
import cn.jiuling.vehicleinfosys2.vo.Pager;

import java.util.List;

public interface CameraService {
	/**
	 * 取得监控点列表
	 */
	public Pager list(Camera c, Integer page, Integer rows);

	/**
	 * 添加监控点
	 * 
	 * @param c
	 */
	public void add(Camera c);

	/**
	 * 从地图上移除监控点
	 * 
	 * @param c
	 */
	public void remove(Camera c);

	/**
	 * 更新监控点
	 * 
	 * @param c
	 */
	public void update(Camera c);

    public void updateCamera(Camera c);

	public Boolean valideName(String name);

	public Boolean valideName(Integer id, String name);

	public void saveCameras(MultipartFile cameraInfo);

	Pager query(CameraQuery c, Integer page, Integer rows);

	public Camera fingById(Integer cameraId);

	/**
	 * 
	 * 取得实时视频流地址
	 * 
	 * @param c
	 * @return 实时视频流地址
	 */
	public String getFilepath(Camera c);

    List<Camera> getAll();
    /**
	 * 通过监控点名称获取监控点信息
	 * @param cameraName 监控点名称
	 * @return
	 */
	public Camera findByName(String cameraName);

    Object camLstTree();
}
