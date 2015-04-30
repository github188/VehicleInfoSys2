package cn.jiuling.vehicleinfosys2.vo;

import java.sql.Timestamp;
import java.util.Date;

import cn.jiuling.vehicleinfosys2.model.VlprTaskFailLog;

/**
 * 用于记录异常的vo
 * 
 * @author phq
 * 
 * @date 2015-4-3
 */
public class FailLogVo extends VlprTaskFailLog {

	private static final long serialVersionUID = 1L;
	private String taskName;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public FailLogVo() {
		super();
	}

	// l.id,l.vlprTaskId,l.failTime,l.description,t.taskName
	public FailLogVo(Integer id, Long vlprTaskId, Date failTime, String description, String taskName) {
		super.setId(id);
		super.setVlprTaskId(vlprTaskId);
		super.setFailTime((Timestamp) failTime);
		super.setDescription(description);
		this.taskName = taskName;
	}

}
