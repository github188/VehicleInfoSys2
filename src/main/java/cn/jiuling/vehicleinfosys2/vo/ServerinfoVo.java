package cn.jiuling.vehicleinfosys2.vo;

import java.sql.Timestamp;

import cn.jiuling.vehicleinfosys2.model.Serverinfo;

public class ServerinfoVo extends Serverinfo {

	private Timestamp queryTime;

	public ServerinfoVo(Serverinfo s) {
		super(s.getIp(), s.getCpu(), s.getRam(), 
			  s.getTotalSpace(), s.getUsedSpace(), 
			  s.getFreeSpace());

		this.queryTime = new Timestamp(System.currentTimeMillis());
		this.setUpdateTime(s.getUpdateTime());
		this.setId(s.getId());
	}

	public Timestamp getQueryTime() {
		return queryTime;
	}

	public void setQueryTime(Timestamp queryTime) {
		this.queryTime = queryTime;
	}
}
