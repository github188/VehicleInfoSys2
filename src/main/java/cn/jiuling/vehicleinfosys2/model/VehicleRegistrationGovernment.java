package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 车管所车辆信息表
 * 
 * @author daixiaowei
 *
 */
@Entity
@Table(name = "vehicle_registration_government")
public class VehicleRegistrationGovernment implements java.io.Serializable {

	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 车牌号
	 */
	private String license;

	/**
	 * 车牌归属地
	 */
	private String licenseAttribution;

	/**
	 * 车牌类型
	 */
	private Integer plateType;

	/**
	 * 车身颜色
	 */
	private String carColor;

	/**
	 * 车型种类
	 */
	private String vehicleKind;

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
	 * 车主身份证号
	 */
	private String identityCardId;

	/**
	 * 车子驾驶证号
	 */
	private String drivingLenceId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "license")
	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	
	@Column(name = "licenseAttribution")
	public String getLicenseAttribution() {
		return licenseAttribution;
	}

	public void setLicenseAttribution(String licenseAttribution) {
		this.licenseAttribution = licenseAttribution;
	}
	
	@Column(name = "plateType")
	public Integer getPlateType() {
		return plateType;
	}

	public void setPlateType(Integer plateType) {
		this.plateType = plateType;
	}
	
	@Column(name = "carColor")
	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}
	
	@Column(name = "vehicleKind")
	public String getVehicleKind() {
		return vehicleKind;
	}

	public void setVehicleKind(String vehicleKind) {
		this.vehicleKind = vehicleKind;
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
	
	@Column(name = "identityCardId")
	public String getIdentityCardId() {
		return identityCardId;
	}

	public void setIdentityCardId(String identityCardId) {
		this.identityCardId = identityCardId;
	}
	
	@Column(name = "drivingLenceId")
	public String getDrivingLenceId() {
		return drivingLenceId;
	}

	public void setDrivingLenceId(String drivingLenceId) {
		this.drivingLenceId = drivingLenceId;
	}

}
