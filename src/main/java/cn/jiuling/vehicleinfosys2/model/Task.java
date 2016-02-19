package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Task entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "task")
public class Task implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer dataSourceId;
	private Integer cameraId;
	private String name;
	private Short type;
	private Timestamp startTime;
	private Timestamp endTime;
	private Short status;
	private Timestamp createTime;
	private Long vlprTaskId;

	// Constructors

	/** default constructor */
	public Task() {
	}

	/** minimal constructor */
	public Task(Short type, Timestamp startTime, Timestamp endTime, Timestamp createTime, Long vlprTaskId) {
		this.type = type;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.vlprTaskId = vlprTaskId;
	}

	/** full constructor */
	public Task(Integer dataSourceId, Integer cameraId, String name, Short type, Timestamp startTime, Timestamp endTime, Short status, Timestamp createTime,
			Long vlprTaskId) {
		this.dataSourceId = dataSourceId;
		this.cameraId = cameraId;
		this.name = name;
		this.type = type;
		this.startTime = startTime;
		this.endTime = endTime;
		this.status = status;
		this.createTime = createTime;
		this.vlprTaskId = vlprTaskId;
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

	@Column(name = "dataSourceId")
	public Integer getDataSourceId() {
		return this.dataSourceId;
	}

	public void setDataSourceId(Integer dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	@Column(name = "cameraId")
	public Integer getCameraId() {
		return this.cameraId;
	}

	public void setCameraId(Integer cameraId) {
		this.cameraId = cameraId;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type", nullable = false)
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "startTime", nullable = false, length = 19)
	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	@Column(name = "endTime", length = 19)
	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "createTime", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "vlprTaskId", nullable = false)
	public Long getVlprTaskId() {
		return this.vlprTaskId;
	}

	public void setVlprTaskId(Long vlprTaskId) {
		this.vlprTaskId = vlprTaskId;
	}

}