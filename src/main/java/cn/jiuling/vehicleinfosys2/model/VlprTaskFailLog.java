package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * VlprTaskFailLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "vlpr_task_fail_log")
public class VlprTaskFailLog implements java.io.Serializable {

	// Fields

	private Integer id;
	private Long vlprTaskId;
	private Timestamp failTime;
	private String description;

	// Constructors

	/** default constructor */
	public VlprTaskFailLog() {
	}

	/** full constructor */
	public VlprTaskFailLog(Long vlprTaskId, Timestamp failTime, String description) {
		this.vlprTaskId = vlprTaskId;
		this.failTime = failTime;
		this.description = description;
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

	@Column(name = "vlprTaskId")
	public Long getVlprTaskId() {
		return this.vlprTaskId;
	}

	public void setVlprTaskId(Long vlprTaskId) {
		this.vlprTaskId = vlprTaskId;
	}

	@Column(name = "failTime", length = 19)
	public Timestamp getFailTime() {
		return this.failTime;
	}

	public void setFailTime(Timestamp failTime) {
		this.failTime = failTime;
	}

	@Column(name = "description", length = 250)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}