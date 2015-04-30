package cn.jiuling.vehicleinfosys2.vo;

/**
 * 系统状态Vo
 * 
 * @author phq
 * 
 * @date 2015-1-23
 */
public class SysStatusVo {

	private String ip;
	private Float cpu;
	private Float ram;
	private Float totalSpace;
	private Float usedSpace;
	private Float freeSpace;

	public Float getCpu() {
		return cpu;
	}

	public void setCpu(Float cpu) {
		this.cpu = cpu;
	}

	public Float getRam() {
		return ram;
	}

	public void setRam(Float ram) {
		this.ram = ram;
	}

	public Float getTotalSpace() {
		return totalSpace;
	}

	public void setTotalSpace(Float totalSpace) {
		this.totalSpace = totalSpace;
	}

	public Float getUsedSpace() {
		return usedSpace;
	}

	public void setUsedSpace(Float usedSpace) {
		this.usedSpace = usedSpace;
	}

	public Float getFreeSpace() {
		return freeSpace;
	}

	public void setFreeSpace(Float freeSpace) {
		this.freeSpace = freeSpace;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
