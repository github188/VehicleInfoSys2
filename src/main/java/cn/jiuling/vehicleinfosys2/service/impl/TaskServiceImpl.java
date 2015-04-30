package cn.jiuling.vehicleinfosys2.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.jiuling.vehicleinfosys2.dao.CameraDao;
import cn.jiuling.vehicleinfosys2.dao.ResourceDao;
import cn.jiuling.vehicleinfosys2.dao.TaskDao;
import cn.jiuling.vehicleinfosys2.dao.VlprTaskDao;
import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.model.Datasource;
import cn.jiuling.vehicleinfosys2.model.Task;
import cn.jiuling.vehicleinfosys2.model.VlprTask;
import cn.jiuling.vehicleinfosys2.service.CameraService;
import cn.jiuling.vehicleinfosys2.service.ResourceService;
import cn.jiuling.vehicleinfosys2.service.TaskService;
import cn.jiuling.vehicleinfosys2.util.JsonUtils;
import cn.jiuling.vehicleinfosys2.util.PathUtils;
import cn.jiuling.vehicleinfosys2.vo.Constant;
import cn.jiuling.vehicleinfosys2.vo.FollowArea;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.TaskQuery;

/**
 * @ClassName: TaskServiceImpl
 * @Description: 任务服务类
 * @author Gost_JR
 * @date: 2014-12-11 下午02:47:25
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {

	private static final Logger log = Logger.getLogger(TaskServiceImpl.class);

	@Resource
	private TaskDao taskDao;
	@Resource
	private VlprTaskDao vlprTaskDao;
	@Resource
	private CameraDao cameraDao;
	@Resource
	private ResourceDao resourceDao;
	@Resource
	private CameraService cameraService;
	@Resource
	private ResourceService resourceService;

	@Override
	public void delete(Integer[] taskIds) {
		for (Integer id : taskIds) {
			Task t = taskDao.find(id);
			taskDao.delete(t);
			Long vId = t.getVlprTaskId();
			VlprTask vlprTask = vlprTaskDao.find(vId);
			vlprTaskDao.delete(vlprTask);
		}
	}

	@Override
	public Pager list(Task task, Integer page, Integer rows) {
		return taskDao.list(task, page, rows);
	}

	@Override
	public Task findLastRealTimeTask(Integer cameraId) {
		VlprTask vlprTask = vlprTaskDao.findStartedRealTimeTask(cameraId);
		if (null == vlprTask) {
			return null;
		}
		return taskDao.findByProperty("vlprTaskId", vlprTask.getTaskId());

	}

	@Override
	public void startOffLineTask(Integer[] ids, FollowArea followArea) {
		for (Integer resourceId : ids) {
			Datasource resource = resourceDao.find(resourceId);
			String filePath = "file://" + resource.getFilePath();
			startTask(resource.getCameraId(), Constant.TASK_TYPE_OFFLINE, filePath, resourceId, followArea);
		}
	}

	@Override
	public Integer startRealTimeTask(Integer cameraId, FollowArea followArea) {
		Task task = findLastRealTimeTask(cameraId);
		if (null != task) {
			return null;
		}
		Integer taskId = startTask(cameraId, Constant.TASK_TYPE_ONLINE, null, null, followArea);
		return taskId;
	}

	/**
	 * 开始任务的封装
	 * 
	 * @param cameraId
	 *            监控点id
	 * @param type
	 *            1实时任务2离线任务
	 * @param filePath
	 *            数据源地址,可以是实时的也可以是离线的
	 * @param followArea
	 * 
	 */
	private Integer startTask(Integer cameraId, Short type, String filePath, Integer resourceId, FollowArea followArea) {
		String newFollowarea = null;
		if (null != followArea) {
			newFollowarea = JsonUtils.toJson(followArea).replaceAll("\"", "");
		}

		long currentTimeMillis = System.currentTimeMillis();
		Timestamp createTime = new Timestamp(currentTimeMillis);
		Camera c = cameraDao.find(cameraId);

		// 插入识别程序的任务表
		VlprTask vt = new VlprTask();
		vt.setStatus(Constant.VLPRTASK_STATUS_WAIT);
		vt.setVideoId(currentTimeMillis);
		vt.setCreateTime(createTime);
		vt.setFlag(Constant.VLPRTASK_FLAG_VALID);
		if (type.equals(Constant.TASK_TYPE_ONLINE)) {
			filePath = cameraService.getFilepath(c);
			c.setFollowarea(newFollowarea);
			cameraService.update(c);
		}

		if (type.equals(Constant.TASK_TYPE_OFFLINE)) {
			Datasource d = resourceDao.find(resourceId);
			d.setFollowarea(newFollowarea);
			resourceDao.update(d);
		}

		// filePath = "file://d:\\video.avi";
		vt.setFilePath(StringUtils.isEmpty(filePath) ? "" : filePath);
		vt.setProgress((short) 0);
		vt.setRecognitionSlaveIp("");
		vt.setRetryCount((short) 0);
		if (null != followArea) {
			vt.setRoiX0(followArea.getX0());
			vt.setRoiY0(followArea.getY0());
			vt.setRoiX1(followArea.getX1());
			vt.setRoiY1(followArea.getY1());
			vt.setRoiCx(followArea.getWidth());
			vt.setRoiCy(followArea.getHeight());
		}
		vlprTaskDao.save(vt);

		// 插入自己的任务表
		Task t = new Task();
		t.setName(c.getName() + "_" + createTime.toString().replaceAll("\\..*", ""));
		t.setCameraId(cameraId);
		t.setCreateTime(createTime);
		t.setType(type);
		t.setStatus(Constant.TASK_STATUS_UNFINISHED);
		t.setStartTime(createTime);
		t.setVlprTaskId(vt.getTaskId());
		t.setDataSourceId(resourceId);
		taskDao.save(t);
		return t.getId();
	}

	/**
	 * 停止实时任务
	 * 
	 * @param cameraId
	 *            监控点id
	 */
	@Override
	public Timestamp stopRealTimeTask(Integer taskId) {
		Task task = taskDao.find(taskId);
		if (null != task) {
			if (task.getStatus() == 1) {
				taskDao.stopSingleTask(task.getVlprTaskId());
			} else {
				return task.getEndTime();
			}
		}
		return null;
	}

	@Override
	public Pager query(TaskQuery taskQuery, Integer page, Integer rows) {
		return taskDao.query(taskQuery, page, rows);
	}

	@Override
	public Object addBatTask(MultipartFile[] files, Integer cId, Short dataType, String type, Integer parentId) {
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			log.info("正在上传的文件：" + file.getOriginalFilename());
			if (file == null || file.isEmpty()) {
				return "fail";
			}
			if (dataType.equals(Constant.DATASOURCE_TYPE_PICTURE) && (type.contains("video") || type.contains("stream"))) {
				return "typeError";
			}
			if (dataType.equals(Constant.DATASOURCE_TYPE_VIDEO) && type.contains("image")) {
				return "typeError";
			}
		}
		log.info("保存上传文件");

		Datasource data;
		try {
			Datasource menu = resourceDao.find(parentId);
			// 转存文件
			MultipartFile file = files[0];
			String path = PathUtils.getTempPath() + "/" + menu.getName();
			data = resourceService.saveFile(file, type, path);
			data.setCameraId(cId);
			data.setType(dataType);
			// 将文件入库
			data.setParentId(parentId);
			resourceDao.save(data);
		} catch (Exception e) {
			log.error("文件保存错误!!", e);
			return "saveError";
		}
		// String filePath = "file://" + PathUtils.getRealPath("/") +
		// File.separator + path;
		// startTask(cId, Constant.TASK_TYPE_BAT, filePath, null, null);
		return "success";
	}

}
