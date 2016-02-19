package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 假套牌车DO
 * 
 * @author daixiaowei
 *
 */
@Entity
@Table(name = "vlpr_fakeLicensed")
public class FakeLicenseCarInfo implements java.io.Serializable {
	
	private Long id;
	private Long serialNumber;

	/**
	 * 报警时间
	 */
	private Timestamp warnTime;

	/**
	 * 过车时间
	 */
	private Timestamp vehicelTime;

	/**
	 * 监控点名称
	 */
	private String camerName;

	/**
	 * 报警类型
	 */
	private String warnType;

	/**
	 * 人工审核
	 */
	private String manulAudit;

	/**
	 * 车牌
	 */
	private String license;

	/**
	 * 车牌种类
	 */
	private Integer plateType;

	/**
	 * 车型
	 */
	private String viehicleKind;

	/**
	 * 车身颜色
	 */
	private String carColor;

	/**
	 * 车品牌
	 */
	private String vehicleBrand;

	/**
	 * 车系
	 */
	private String vehicleSeries;

	/**
	 * 车款
	 */
	private String vehicleStyle;

	/**
	 * 识别可信度
	 */
	private Integer confidence;

	/**
	 * 处理状态
	 */
	private String optionState;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "SerialNumber")
	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Timestamp getWarnTime() {
		return warnTime;
	}

	public void setWarnTime(Timestamp warnTime) {
		this.warnTime = warnTime;
	}

	@Column(name = "vehicelTime")
	public Timestamp getVehicelTime() {
		return vehicelTime;
	}

	public void setVehicelTime(Timestamp vehicelTime) {
		this.vehicelTime = vehicelTime;
	}

	@Column(name = "camerName")
	public String getCamerName() {
		return camerName;
	}

	public void setCamerName(String camerName) {
		this.camerName = camerName;
	}

	@Column(name = "warnType")
	public String getWarnType() {
		return warnType;
	}

	public void setWarnType(String warnType) {
		this.warnType = warnType;
	}

	@Column(name = "manulAudit")
	public String getManulAudit() {
		return manulAudit;
	}

	public void setManulAudit(String manulAudit) {
		this.manulAudit = manulAudit;
	}

	@Column(name = "license")
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	@Column(name = "plateType")
	public Integer getPlateType() {
		return plateType;
	}

	public void setPlateType(Integer plateType) {
		this.plateType = plateType;
	}

	@Column(name = "viehicleKind")
	public String getViehicleKind() {
		return viehicleKind;
	}

	public void setViehicleKind(String viehicleKind) {
		this.viehicleKind = viehicleKind;
	}

	@Column(name = "carColor")
	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	@Column(name = "vehicleBrand")
	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	@Column(name = "vehicleSeries")
	public String getVehicleSeries() {
		return vehicleSeries;
	}

	public void setVehicleSeries(String vehicleSeries) {
		this.vehicleSeries = vehicleSeries;
	}

	@Column(name = "vehicleStyle")
	public String getVehicleStyle() {
		return vehicleStyle;
	}

	public void setVehicleStyle(String vehicleStyle) {
		this.vehicleStyle = vehicleStyle;
	}

	@Column(name = "confidence")
	public Integer getConfidence() {
		return confidence;
	}

	public void setConfidence(Integer confidence) {
		this.confidence = confidence;
	}

	@Column(name = "optionState")
	public String getOptionState() {
		return optionState;
	}

	public void setOptionState(String optionState) {
		this.optionState = optionState;
	}

}
