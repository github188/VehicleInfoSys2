package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "vlpr_vfm_task")
public class VlprVfmTask implements java.io.Serializable {

	private Long taskID;
	private Long vlprTaskID;
	private Long serialNumber;
	private String cameraId;
	private Short progress;
	private Short status;
	private Short retryCount;
	private Short flag;
	private String recognitionSlaveIP;
	private String license;
	private Short plateType;
	private Short plateLeft;
	private Short plateTop;
	private Short plateRight;
	private Short plateBottom;
	private Short vehicleLeft;
	private Short vehicleTop;
	private Short vehicleRight;
	private Short vehicleBottom;
	private Timestamp passStartTime;
	private Timestamp passEndTime;
	private String vehicleKind;
	private String vehicleBrand;
	private String vehicleSeries;
	private String vehicleStyle;
	private String vehicleColor;
	private String desImagePath;
	private String featureImagePath;
	private Timestamp insertTime;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "taskID", unique = true, nullable = false)
	public Long getTaskID() {
		return this.taskID;
	}

	public void setTaskID(Long taskID) {
		this.taskID = taskID;
	}
	
	@Column(name = "vlprTaskID")
	public Long getVlprTaskID() {
		return vlprTaskID;
	}

	public void setVlprTaskID(Long vlprTaskID) {
		this.vlprTaskID = vlprTaskID;
	}
	
	@Column(name = "serialNumber")
	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name = "cameraId", length = 1024)
	public String getCameraId() {
		return cameraId;
	}

	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}

	@Column(name = "progress")
	public Short getProgress() {
		return this.progress;
	}

	public void setProgress(Short progress) {
		this.progress = progress;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "retryCount")
	public Short getRetryCount() {
		return this.retryCount;
	}

	public void setRetryCount(Short retryCount) {
		this.retryCount = retryCount;
	}

	@Column(name = "flag")
	public Short getFlag() {
		return this.flag;
	}

	public void setFlag(Short flag) {
		this.flag = flag;
	}

	@Column(name = "recognitionSlaveIP", length = 100)
	public String getRecognitionSlaveIP() {
		return this.recognitionSlaveIP;
	}

	public void setRecognitionSlaveIP(String recognitionSlaveIP) {
		this.recognitionSlaveIP = recognitionSlaveIP;
	}

	@Column(name = "license", length = 50)
	public String getLicense() {
		return this.license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	
	@Column(name = "plateType")
	public Short getPlateType() {
		return plateType;
	}

	public void setPlateType(Short plateType) {
		this.plateType = plateType;
	}

	@Column(name = "plateLeft")
	public Short getPlateLeft() {
		return this.plateLeft;
	}

	public void setPlateLeft(Short plateLeft) {
		this.plateLeft = plateLeft;
	}

	@Column(name = "plateTop")
	public Short getPlateTop() {
		return this.plateTop;
	}

	public void setPlateTop(Short plateTop) {
		this.plateTop = plateTop;
	}

	@Column(name = "plateRight")
	public Short getPlateRight() {
		return this.plateRight;
	}

	public void setPlateRight(Short plateRight) {
		this.plateRight = plateRight;
	}

	@Column(name = "plateBottom")
	public Short getPlateBottom() {
		return this.plateBottom;
	}

	public void setPlateBottom(Short plateBottom) {
		this.plateBottom = plateBottom;
	}

	@Column(name = "vehicleLeft")
	public Short getVehicleLeft() {
		return this.vehicleLeft;
	}

	public void setVehicleLeft(Short vehicleLeft) {
		this.vehicleLeft = vehicleLeft;
	}

	@Column(name = "vehicleTop")
	public Short getVehicleTop() {
		return this.vehicleTop;
	}

	public void setVehicleTop(Short vehicleTop) {
		this.vehicleTop = vehicleTop;
	}

	@Column(name = "vehicleRight")
	public Short getVehicleRight() {
		return this.vehicleRight;
	}

	public void setVehicleRight(Short vehicleRight) {
		this.vehicleRight = vehicleRight;
	}

	@Column(name = "vehicleBottom")
	public Short getVehicleBottom() {
		return this.vehicleBottom;
	}

	public void setVehicleBottom(Short vehicleBottom) {
		this.vehicleBottom = vehicleBottom;
	}

	@Column(name = "passStartTime")
	public Timestamp getPassStartTime() {
		return this.passStartTime;
	}

	public void setPassStartTime(Timestamp passStartTime) {
		this.passStartTime = passStartTime;
	}

	@Column(name = "passEndTime")
	public Timestamp getPassEndTime() {
		return this.passEndTime;
	}

	public void setPassEndTime(Timestamp passEndTime) {
		this.passEndTime = passEndTime;
	}

	@Column(name = "vehicleKind", length = 50)
	public String getVehicleKind() {
		return vehicleKind;
	}

	public void setVehicleKind(String vehicleKind) {
		this.vehicleKind = vehicleKind;
	}

	@Column(name = "vehicleBrand", length = 50)
	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public void setVehicleBrand(String vehicleBrand) {
		this.vehicleBrand = vehicleBrand;
	}

	@Column(name = "vehicleSeries", length = 50)
	public String getVehicleSeries() {
		return vehicleSeries;
	}

	public void setVehicleSeries(String vehicleSeries) {
		this.vehicleSeries = vehicleSeries;
	}

	@Column(name = "vehicleStyle", length = 50)
	public String getVehicleStyle() {
		return vehicleStyle;
	}

	public void setVehicleStyle(String vehicleStyle) {
		this.vehicleStyle = vehicleStyle;
	}

	@Column(name = "vehicleColor", length = 32)
	public String getVehicleColor() {
		return vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	@Column(name = "desImagePath", length = 32)
	public String getDesImagePath() {
		return desImagePath;
	}

	public void setDesImagePath(String desImagePath) {
		this.desImagePath = desImagePath;
	}

	@Column(name = "featureImagePath", length = 32)
	public String getFeatureImagePath() {
		return featureImagePath;
	}

	public void setFeatureImagePath(String featureImagePath) {
		this.featureImagePath = featureImagePath;
	}

	@Column(name = "insertTime")
	public Timestamp getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Timestamp insertTime) {
		this.insertTime = insertTime;
	}

}