package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Serverinfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "serverinfo")
public class Serverinfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String ip;
	private Double cpu;
	private Double ram;
	private Double totalSpace;
	private Double usedSpace;
	private Double freeSpace;
	private Timestamp updateTime;

	// Constructors

	/** default constructor */
	public Serverinfo() {
	}

	/** minimal constructor */
	public Serverinfo(String ip) {
		this.ip = ip;
	}

	/** full constructor */
	public Serverinfo(String ip, Double cpu, Double ram, Double totalSpace, Double usedSpace, Double freeSpace) {
		this.ip = ip;
		this.cpu = cpu;
		this.ram = ram;
		this.totalSpace = totalSpace;
		this.usedSpace = usedSpace;
		this.freeSpace = freeSpace;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ip", nullable = false, length = 50)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "cpu", precision = 22, scale = 0)
	public Double getCpu() {
		return this.cpu;
	}

	public void setCpu(Double cpu) {
		this.cpu = cpu;
	}

	@Column(name = "ram", precision = 22, scale = 0)
	public Double getRam() {
		return this.ram;
	}

	public void setRam(Double ram) {
		this.ram = ram;
	}

	@Column(name = "totalSpace", precision = 22, scale = 0)
	public Double getTotalSpace() {
		return this.totalSpace;
	}

	public void setTotalSpace(Double totalSpace) {
		this.totalSpace = totalSpace;
	}

	@Column(name = "usedSpaceRatio", precision = 22, scale = 0)
	public Double getUsedSpace() {
		return this.usedSpace;
	}

	public void setUsedSpace(Double usedSpace) {
		this.usedSpace = usedSpace;
	}

	@Column(name = "freeSpace", precision = 22, scale = 0)
	public Double getFreeSpace() {
		return this.freeSpace;
	}

	public void setFreeSpace(Double freeSpace) {
		this.freeSpace = freeSpace;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}