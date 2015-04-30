package cn.jiuling.vehicleinfosys2.vo;

import java.sql.Timestamp;
import java.util.Date;

import cn.jiuling.vehicleinfosys2.model.Task;

public class TaskVo extends Task {

	private String cameraName;

	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	public TaskVo(Integer taskId, String name, Short type, Date startTime, Date endTime, Short status, String cameraName) {
		if (null != startTime) {
			setStartTime((Timestamp) startTime);
		}
		if (null != endTime) {
			setEndTime((Timestamp) endTime);
		}
		setId(taskId);
		setName(name);
		setType(type);
		setStatus(status);
		this.cameraName = cameraName;
	}
}
