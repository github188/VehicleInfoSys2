package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 审核记录表
 * 
 * @author daixiaowei
 *
 */
@Entity
@Table(name = "surveillance_application_record")
public class SurveillanceApplicationRecord {

	private Integer id;
	private Integer surveillanceTaskId;
	private Integer recordType;
	private String name;
	private String result;
	private String peopleName;
	private String peopleTel;
	private String unitName;
	private String cause;
	private Timestamp recordTime;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Basic
	@Column(name = "surveillanceTaskId")
	public Integer getSurveillanceTaskId() {
		return surveillanceTaskId;
	}

	public void setSurveillanceTaskId(Integer surveillanceTaskId) {
		this.surveillanceTaskId = surveillanceTaskId;
	}

	@Basic
	@Column(name = "recordType")
	public Integer getRecordType() {
		return recordType;
	}

	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "result")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Basic
	@Column(name = "peopleName")
	public String getPeopleName() {
		return peopleName;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}

	@Basic
	@Column(name = "peopleTel")
	public String getPeopleTel() {
		return peopleTel;
	}

	public void setPeopleTel(String peopleTel) {
		this.peopleTel = peopleTel;
	}

	@Basic
	@Column(name = "unitName")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Basic
	@Column(name = "cause")
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	@Basic
	@Column(name = "recordTime")
	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

}
