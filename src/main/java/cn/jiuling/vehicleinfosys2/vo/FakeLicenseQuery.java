package cn.jiuling.vehicleinfosys2.vo;

import java.sql.Timestamp;

/**
 * 
 * 假套牌车查询请求参数类
 * 
 * @author daixiaowei
 *
 */
public class FakeLicenseQuery {

	private Long id;
	private Long serialNumber;

	/**
	 * 页面查询的报警时间
	 */
	private String queryWarnTime;

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
	private String plateType;

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

	private String startTime;
	private String endTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Timestamp getVehicelTime() {
		return vehicelTime;
	}

	public void setVehicelTime(Timestamp vehicelTime) {
		this.vehicelTime = vehicelTime;
	}

	public String getQueryWarnTime() {
		return queryWarnTime;
	}

	public void setQueryWarnTime(String queryWarnTime) {
		this.queryWarnTime = queryWarnTime;
	}

	public String getCamerName() {
		return camerName;
	}

	public void setCamerName(String camerName) {
		this.camerName = camerName;
	}

	public String getWarnType() {
		return warnType;
	}

	public void setWarnType(String warnType) {
		this.warnType = warnType;
	}

	public String getManulAudit() {
		return manulAudit;
	}

	public void setManulAudit(String manulAudit) {
		this.manulAudit = manulAudit;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getPlateType() {
		return plateType;
	}

	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}

	public String getViehicleKind() {
		return viehicleKind;
	}

	public void setViehicleKind(String viehicleKind) {
		this.viehicleKind = viehicleKind;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	public String getVehicleSeries() {
		return vehicleSeries;
	}

	public void setVehicleSeries(String vehicleSeries) {
		this.vehicleSeries = vehicleSeries;
	}

	public String getVehicleStyle() {
		return vehicleStyle;
	}

	public void setVehicleStyle(String vehicleStyle) {
		this.vehicleStyle = vehicleStyle;
	}

	public Integer getConfidence() {
		return confidence;
	}

	public void setConfidence(Integer confidence) {
		this.confidence = confidence;
	}

	public String getOptionState() {
		return optionState;
	}

	public void setOptionState(String optionState) {
		this.optionState = optionState;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
