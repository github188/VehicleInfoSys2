package cn.jiuling.vehicleinfosys2.vo;

import org.springframework.util.StringUtils;

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
	private static final String[] directions = { "未知",  "向左", "向右","向上", "向下"};
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
	 * 地点
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
	 * 车系
	 */
	private String vehicleSeries;
	/**
	 * 车款
	 */
	private String vehicleStyle;
	
	//车牌框坐标
	private Short locationLeft;
	private Short locationTop;
	private Short locationRight;
	private Short locationBottom;

	/**
	 *年检标
	 */
	private Short tag;
	/**
	 *纸巾盒
	 */
	private Short paper;
	/**
	 *遮阳板
	 */
	private Short drop;
	/**
	 *挂饰
	 */
	private Short sun;
	
	
	/**
	 *年检标位置及可信度
	 */
	private String tagRectAndScore;
	/**
	 *纸巾盒位置及可信度
	 */
	private String paperRectAndScore;
	/**
	 *遮阳板位置及可信度
	 */
	private String dropRectAndScore;
	/**
	 *挂饰位置及可信度
	 */
	private String sunRectAndScore;
	
	/**
	 *前挡风玻璃位置及可信度
	 */
	private String windowRectAndScore;

	/**
	 * 监控点ID
	 */
	private Integer cameraID;

	/**
	 * 监控点IP地址
	 */
	private String cameraIP;

	/**
	 * 车速
	 */
	private Double carspeed;

	/**
	 * 车标
	 */
	private String carLogo;
	/**
	 * 识别结果图片绝对路径
	 */
	private String imagePath;
	
	//车身左上角x坐标
	private Short vehicleLeft;
	
	//车身左上角y坐标
	private Short vehicleTop;
	
	//车身右下角x坐标
	private Short vehicleRight;
	
	//车身右下角y坐标
	private Short vehicleBootom;
	
	
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

	public String getTagRectAndScore() {
		return tagRectAndScore;
	}

	public void setTagRectAndScore(String tagRectAndScore) {
		this.tagRectAndScore = tagRectAndScore;
	}

	public String getPaperRectAndScore() {
		return paperRectAndScore;
	}

	public void setPaperRectAndScore(String paperRectAndScore) {
		this.paperRectAndScore = paperRectAndScore;
	}

	public String getDropRectAndScore() {
		return dropRectAndScore;
	}

	public void setDropRectAndScore(String dropRectAndScore) {
		this.dropRectAndScore = dropRectAndScore;
	}

	public String getSunRectAndScore() {
		return sunRectAndScore;
	}

	public void setSunRectAndScore(String sunRectAndScore) {
		this.sunRectAndScore = sunRectAndScore;
	}

	public String getWindowRectAndScore() {
		return windowRectAndScore;
	}

	public void setWindowRectAndScore(String windowRectAndScore) {
		this.windowRectAndScore = windowRectAndScore;
	}

	public ResultVo(String license, Short plateType, String plateColor, Short confidence, String licenseAttribution, String imageUrl,
			String carColor, Date resultTime, String location, Short direction, Double longitude, Double latitude, String path,
			Long frame_index, Short taskType, Short resourceType,String vehicleKind,String vehicleBrand,String vehicleStyle,
			Short locationLeft,Short locationTop,Short locationRight,Short locationBottom,Short tag,Short paper, Short drop,Short sun,String vehicleSeries,Integer cameraID,
			String cameraIP,Double carspeed,String carLogo,String imagePath,Short vehicleLeft,Short vehicleTop,Short vehicleRight,Short vehicleBootom) {
		super();
		this.license = license;

		this.plateColor = plateColor;
		this.confidence = confidence;
		this.licenseAttribution = licenseAttribution;
		this.imageUrl = imageUrl;
		this.carColor = carColor;
		this.resultTime = new Timestamp(resultTime.getTime());
		this.location = location;

		if(!StringUtils.isEmpty(plateType) && plateType>=0 && plateType<plateTypes.length){
			this.setPlateType(plateTypes[(plateType)]);
		}
		if(!StringUtils.isEmpty(direction) && direction>=0 && direction<directions.length){
			this.setDirection(directions[direction]);
		}
		this.longitude = longitude;
		this.latitude = latitude;
		this.path = path;
		this.frame_index = frame_index;
		this.taskType = taskType;
		this.resourceType = resourceType;
		this.vehicleKind = vehicleKind;
		this.vehicleBrand = vehicleBrand;
		this.vehicleStyle = vehicleStyle;
		this.locationLeft = locationLeft;
		this.locationTop = locationTop;
		this.locationRight = locationRight;
		this.locationBottom = locationBottom;
		this.tag = tag;
		this.paper = paper;
		this.drop = drop;
		this.sun = sun;
		this.vehicleSeries = vehicleSeries;
		this.cameraID = cameraID;
		this.cameraIP = cameraIP;
		this.carspeed = carspeed;
		this.carLogo = carLogo;
		this.imagePath = imagePath;
		this.vehicleLeft = vehicleLeft;
		this.vehicleTop = vehicleTop;
		this.vehicleRight = vehicleRight;
		this.vehicleBootom = vehicleBootom;
	}

	// TODO 以后再优化，这种方式可维护性太差，以后要改
	public ResultVo(Object[] o) {
		this.setLicense((String) o[0]);
		this.setPlateColor((String) o[2]);
		this.setConfidence((Short) o[3]);
		this.setLicenseAttribution((String) o[4]);
		this.setImageUrl((String) o[5]);
		this.setCarColor((String) o[6]);
		this.setResultTime((Timestamp) o[7]);
		this.setLocation((String) o[8]);

		Short plateType = (Short) o[1];
		if(!StringUtils.isEmpty(plateType) && plateType>=0 && plateType<plateTypes.length){
			this.setPlateType(plateTypes[(plateType)]);
		}
		Short direction = (Short) o[9];
		if(!StringUtils.isEmpty(direction) && direction>=0 && direction<directions.length){
			this.setDirection(directions[direction]);
		}

		this.setLongitude((Double) o[10]);
		this.setLatitude((Double) o[11]);
		this.setPath((String) o[12]);

		if (o[13] != null) {
			this.setFrame_index(((BigInteger) o[13]).longValue());
		}
		this.setTaskType(o[14] == null ? null : ((Byte) o[14]).shortValue());
		this.setResourceType(o[15] == null ? null : ((Byte) o[15]).shortValue());
		this.setSerialNumber(((BigInteger) o[16]).longValue());
		//vehicleKinds直接是中文车车型了,不用map转换
		this.setVehicleKind((String) o[17]);
		this.setVehicleBrand((String) o[18]);
		this.setVehicleStyle((String) o[19]);
		this.setLocationLeft((Short) o[20]);
		this.setLocationTop((Short) o[21]);
		this.setLocationRight((Short) o[22]);
		this.setLocationBottom((Short) o[23]);
		this.setTag(o[24] == null ? null : ((Byte) o[24]).shortValue());
		this.setPaper(o[25] == null ? null : ((Byte) o[25]).shortValue());
		this.setDrop(o[26] == null ? null : ((Byte) o[26]).shortValue());
		this.setSun(o[27] == null ? null : ((Byte) o[27]).shortValue());
		this.setVehicleSeries((String) o[28]);
		this.setCameraID((Integer) o[29]);
		this.setCameraIP((String) o[30]);
		this.setCarspeed((Double) o[31]);
		this.setCarLogo((String) o[32]);
		this.setImagePath((String) o[33]);
		this.setVehicleLeft((Short) o[34]);
		this.setVehicleTop((Short) o[35]);
		this.setVehicleRight((Short) o[36]);
		this.setVehicleBootom((Short) o[37]);
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

	public Short getLocationLeft() {
    	return locationLeft;
    }

	public void setLocationLeft(Short locationLeft) {
    	this.locationLeft = locationLeft;
    }

	public Short getLocationTop() {
    	return locationTop;
    }

	public void setLocationTop(Short locationTop) {
    	this.locationTop = locationTop;
    }

	public Short getLocationRight() {
    	return locationRight;
    }

	public void setLocationRight(Short locationRight) {
    	this.locationRight = locationRight;
    }

	public Short getLocationBottom() {
    	return locationBottom;
    }

	public void setLocationBottom(Short locationBottom) {
    	this.locationBottom = locationBottom;
    }

	public static String[] getPlatetypes() {
    	return plateTypes;
    }

	public static String[] getDirections() {
    	return directions;
    }

	public static Map<String, String> getVehiclekinds() {
    	return vehicleKinds;
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

	public Integer getCameraID() {
		return cameraID;
	}

	public void setCameraID(Integer cameraID) {
		this.cameraID = cameraID;
	}

	public String getCameraIP() {
		return cameraIP;
	}

	public void setCameraIP(String cameraIP) {
		this.cameraIP = cameraIP;
	}

	public Double getCarspeed() {
		return carspeed;
	}

	public void setCarspeed(Double carspeed) {
		this.carspeed = carspeed;
	}

	public String getCarLogo() {
		return carLogo;
	}

	public void setCarLogo(String carLogo) {
		this.carLogo = carLogo;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Short getVehicleLeft() {
		return vehicleLeft;
	}

	public void setVehicleLeft(Short vehicleLeft) {
		this.vehicleLeft = vehicleLeft;
	}

	public Short getVehicleTop() {
		return vehicleTop;
	}

	public void setVehicleTop(Short vehicleTop) {
		this.vehicleTop = vehicleTop;
	}

	public Short getVehicleRight() {
		return vehicleRight;
	}

	public void setVehicleRight(Short vehicleRight) {
		this.vehicleRight = vehicleRight;
	}

	public Short getVehicleBootom() {
		return vehicleBootom;
	}

	public void setVehicleBootom(Short vehicleBootom) {
		this.vehicleBootom = vehicleBootom;
	}
	
}
