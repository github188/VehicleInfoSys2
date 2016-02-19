package cn.jiuling.vehicleinfosys2.vo;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;

public class SearchCarByImageVo {

	private static final String[] plateTypes = {
			"未知车牌",
			"蓝牌",
			"黑牌",
			"单排黄牌",
			"双排黄牌(大车尾牌，农用车)",
			"警车车牌",
			"武警车牌",
			"个性化车牌",
			"单排军车",
			"双排军车",
			"使馆牌",
			"香港牌",
			"拖拉机",
			"澳门牌",
			"厂内牌"
	};

	private String license;
	private String plateColor;
	private String plateType;
	private Short confidence;
	private String licenseAttribution;
	private String carColor;
	private String vehicleKind;
	private String vehicleBrand;
	private String vehicleSeries;
	private String vehicleStyle;
	private Short tag;
	private Short paper;
	private Short sun;
	private Short drop;
	private String location;
	private Timestamp resultTime;
	private Float vfmScore;
	private String imagePath;
	private String imageURL;
	private Short progress;

	public SearchCarByImageVo(Object[] o) {
		this.setLicense((String) o[2]);
		this.setPlateColor((String) o[4]);

		Short plateType = (Short) o[5];
		if(!StringUtils.isEmpty(plateType) && plateType>=0 && plateType<plateTypes.length){
			this.setPlateType(plateTypes[(plateType)]);
		}

		this.setConfidence((Short) o[6]);
		this.setLicenseAttribution((String) o[3]);
		this.setCarColor((String) o[15]);
		this.setVehicleKind((String) o[24]);
		this.setVehicleBrand((String) o[25]);
		this.setVehicleSeries((String) o[26]);
		this.setVehicleStyle((String) o[27]);
		this.setTag(o[28] == null ? null : ((Byte) o[28]).shortValue());
		this.setPaper(o[29] == null ? null : ((Byte) o[29]).shortValue());
		this.setSun(o[30] == null ? null : ((Byte) o[30]).shortValue());
		this.setDrop(o[31] == null ? null : ((Byte) o[31]).shortValue());
		this.setLocation((String) o[45]);
		this.setResultTime((Timestamp) o[19]);
		this.setVfmScore((Float) o[44]);
		this.setImagePath((String) o[17]);
		this.setImageURL((String) o[18]);
		this.setProgress((Short) o[46]);
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getPlateColor() {
		return plateColor;
	}

	public void setPlateColor(String plateColor) {
		this.plateColor = plateColor;
	}

	public String getPlateType() {
		return plateType;
	}

	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}

	public Short getConfidence() {
		return confidence;
	}

	public void setConfidence(Short confidence) {
		this.confidence = confidence;
	}

	public String getLicenseAttribution() {
		return licenseAttribution;
	}

	public void setLicenseAttribution(String licenseAttribution) {
		this.licenseAttribution = licenseAttribution;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getVehicleKind() {
		return vehicleKind;
	}

	public void setVehicleKind(String vehicleKind) {
		this.vehicleKind = vehicleKind;
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

	public Short getTag() {
		return tag;
	}

	public void setTag(Short tag) {
		this.tag = tag;
	}

	public Short getPaper() {
		return paper;
	}

	public void setPaper(Short paper) {
		this.paper = paper;
	}

	public Short getDrop() {
		return drop;
	}

	public void setDrop(Short drop) {
		this.drop = drop;
	}

	public Short getSun() {
		return sun;
	}

	public void setSun(Short sun) {
		this.sun = sun;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Timestamp getResultTime() {
		return resultTime;
	}

	public void setResultTime(Timestamp resultTime) {
		this.resultTime = resultTime;
	}

	public Float getVfmScore() {
		return vfmScore;
	}

	public void setVfmScore(Float vfmScore) {
		this.vfmScore = vfmScore;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public Short getProgress() {
		return progress;
	}

	public void setProgress(Short progress) {
		this.progress = progress;
	}
}
