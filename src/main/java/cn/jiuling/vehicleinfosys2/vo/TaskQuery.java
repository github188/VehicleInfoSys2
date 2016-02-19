package cn.jiuling.vehicleinfosys2.vo;

import cn.jiuling.vehicleinfosys2.model.Task;

public class TaskQuery extends Task {
	/**
	 * 监控点id
	 */
	private String ids;
	private String offlineVideoName;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

    public String getOfflineVideoName() {
        return offlineVideoName;
    }

    public void setOfflineVideoName(String offlineVideoName) {
        this.offlineVideoName = offlineVideoName;
    }
}
