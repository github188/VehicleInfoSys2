package cn.jiuling.vehicleinfosys2.vo;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 结果视图对象
 * 
 * @author phq
 * 
 * @date 2014-12-15
 */
public class ResultVo {
	/*	//车牌类型
	0   //未知车牌
	1   //蓝牌
	2   //黑牌
	3   //单排黄牌
	4   //双排黄牌（大车尾牌，农用车）
	5   //警车车牌
	6   //武警车牌
	7   //个性化车牌
	8   //单排军车
	9   //双排军车
	10  //使馆牌
	11  //香港牌
	12  //拖拉机
	13  //澳门牌
	14  //厂内牌
	*/

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
	private static final String[] directions = { "未知", "向上", "向下", "向左", "向右" };
	private static final Map<String, String> vehicleKinds = new HashMap<String, String>() {
	{    
	    put("car", "小轿车");    
	    put("freight", "货车");    
	    put("passenger", "客车");    
	    put("motorcycle", "摩托车");    
	    put("van", "面包车");    
	    put("unknown", "未识别");    
	}};

	/**
	 * 车牌
	 */
	private String license;
	/**
	 * 车牌类型
	 */
	private String plateType;
	/**
	 * 车牌颜色
	 */
	private String plateColor;
	/**
	 * 车牌可信度
	 */
	private Short confidence;
	/**
	 * 车牌归属地
	 */
	private String licenseAttribution;
	/**
	 * 图片地址
	 */
	private String imageUrl;
	/**
	 * 车身颜色
	 */
	private String carColor;
	/**
	 * 识别时间
	 */
	private Timestamp resultTime;
	/**
	 * 过车行政区
	 */
	private String location;
	/**
	 * 方向
	 */
	private String direction;
	/**
	 * 经度
	 */
	private Double longitude;
	/**
	 * 纬度
	 */
	private Double latitude;
	/**
	 * 源视频路径
	 */
	private String path;
	/**
	 * 结果出现的帧数
	 */
	private Long frame_index;
	/**
	 * 结果所属任务的类型
	 */
	private Short taskType;
	/**
	 * 资源id，当任务为离线任务时些id才会有值
	 */
	private Integer resourceId;
	/**
	 * 资源类型
	 */
	private Short resourceType;
	/**
	 * 结果ID
	 */
	private Long serialNumber;
	/**
	 * 车型
	 */
	private String vehicleKind;
	/**
	 * 车品牌
	 */
	private String vehicleBrand;
	/**
	 * 车款
	 */
	private String vehicleStyle;
	
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

	public Timestamp getResultTime() {
		return resultTime;
	}

	public void setResultTime(Timestamp resultTime) {
		this.resultTime = resultTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public ResultVo() {
		super();
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public ResultVo(String license, Short plateType, String plateColor, Short confidence, String licenseAttribution, String imageUrl,
			String carColor, Date resultTime, String location, Short direction, Double longitude, Double latitude, String path,
			Long frame_index, Short taskType, Short resourceType,String vehicleKind,String vehicleBrand,String vehicleStyle) {
		super();
		this.license = license;

		this.plateType = plateTypes[plateType];
		this.plateColor = plateColor;
		this.confidence = confidence;
		this.licenseAttribution = licenseAttribution;
		this.imageUrl = imageUrl;
		this.carColor = carColor;
		this.resultTime = new Timestamp(resultTime.getTime());
		this.location = location;

		this.direction = directions[direction];

		this.longitude = longitude;
		this.latitude = latitude;
		this.path = path;
		this.frame_index = frame_index;
		this.taskType = taskType;
		this.resourceType = resourceType;
		this.vehicleKind = vehicleKind;
		this.vehicleBrand = vehicleBrand;
		this.vehicleStyle = vehicleStyle;
	}

	// TODO 以后再优化，这种方式可维护性太差，以后要改
	public ResultVo(Object[] o) {
		this.setLicense((String) o[0]);
		this.setPlateType(plateTypes[(Short) o[1]]);
		this.setPlateColor((String) o[2]);
		this.setConfidence((Short) o[3]);
		this.setLicenseAttribution((String) o[4]);
		this.setImageUrl((String) o[5]);
		this.setCarColor((String) o[6]);
		this.setResultTime((Timestamp) o[7]);
		this.setLocation((String) o[8]);
		this.setDirection(directions[(Short) o[9]]);
		this.setLongitude((Double) o[10]);
		this.setLatitude((Double) o[11]);
		this.setPath((String) o[12]);

		if (o[13] != null) {
			this.setFrame_index(((BigInteger) o[13]).longValue());
		}
		this.setTaskType(((Byte) o[14]) == null ? null : ((Byte) o[14]).shortValue());
		this.setResourceType(((Byte) o[15]) == null ? null : ((Byte) o[15]).shortValue());
		this.setSerialNumber(((BigInteger) o[16]).longValue());
		this.setVehicleKind(vehicleKinds.get(o[17]));
		this.setVehicleBrand((String)o[18]);
		this.setVehicleStyle((String)o[19]);
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getPlateColor() {
		return plateColor;
	}

	public void setPlateColor(String plateColor) {
		this.plateColor = plateColor;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getFrame_index() {
		return frame_index;
	}

	public void setFrame_index(Long frameIndex) {
		frame_index = frameIndex;
	}

	public Short getTaskType() {
		return taskType;
	}

	public void setTaskType(Short taskType) {
		this.taskType = taskType;
	}

	public Short getResourceType() {
		return resourceType;
	}

	public void setResourceType(Short resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
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

	public String getVehicleStyle() {
		return vehicleStyle;
	}

	public void setVehicleStyle(String vehicleStyle) {
		this.vehicleStyle = vehicleStyle;
	}

}
