package cn.jiuling.vehicleinfosys2.vo;

import java.sql.Timestamp;
import java.util.Date;

import cn.jiuling.vehicleinfosys2.model.Task;

public class TaskVo extends Task {

	private String cameraName;
	private Short progress;
	private Long count;

	public String getCameraName() {
		return cameraName;
	}

	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}

	public TaskVo(Integer taskId, String name, Short type, Short progress, Date startTime, Date endTime, Short status, String cameraName) {
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
		this.progress = progress;
		this.cameraName = cameraName;
	}

	public Short getProgress() {
		return progress;
	}

	public void setProgress(Short progress) {
		this.progress = progress;
	}

	public Long getCount() {
    	return count;
    }

	public void setCount(Long count) {
    	this.count = count;
    }
}
