package cn.jiuling.vehicleinfosys2.vo;

import java.sql.Timestamp;

/**
 * Created by admin on 2015/10/26.
 */
public class SurveillanceResultVo {

    private Long id;
    private Long surveillanceTaskId;
    private Long serialNumber;
    private String license;
    private String licenseAttribution;
    private String plateColor;
    private String plateType;
    private Short confidence;
    private Short direction;
    private Short locationLeft;
    private Short locationTop;
    private Short locationRight;
    private Short locationBottom;
    private String carColor;
    private String imageUrl;
    private Timestamp resultTime;
    private Long frame_index;
    private String vehicleKind;
    private String vehicleBrand;
    private String vehicleSeries;
    private String vehicleStyle;
    private Short tag;
    private Short paper;
    private Short drop;
    private Short sun;
    private String location;

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
     *特征物名称
    *//*
    private String featureName;
    *//**
     *特征物位置信息
     *//*
    private String position;
    *//**
     *可信度
     *//*
    private String reliability;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSurveillanceTaskId() {
        return surveillanceTaskId;
    }

    public void setSurveillanceTaskId(Long surveillanceTaskId) {
        this.surveillanceTaskId = surveillanceTaskId;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getLicenseAttribution() {
        return licenseAttribution;
    }

    public void setLicenseAttribution(String licenseAttribution) {
        this.licenseAttribution = licenseAttribution;
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

    public Short getDirection() {
        return direction;
    }

    public void setDirection(Short direction) {
        this.direction = direction;
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

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getResultTime() {
        return resultTime;
    }

    public void setResultTime(Timestamp resultTime) {
        this.resultTime = resultTime;
    }

    public Long getFrame_index() {
        return frame_index;
    }

    public void setFrame_index(Long frame_index) {
        this.frame_index = frame_index;
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
}
