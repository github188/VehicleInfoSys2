package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "vlpr_specially_result")
public class VlprSpeciallyResult {
    private Long serialNumber;
    @JsonIgnore
    private VlprTask vlprTask;
    private String license;
    private String licenseAttribution;
    private String plateColor;
    private Short plateType;
    private Short confidence;
    private Short bright;
    private Short direction;
    private Short locationLeft;
    private Short locationTop;
    private Short locationRight;
    private Short locationBottom;
    private Short costTime;
    private String carBright;
    private String carColor;
    private String carLogo;
    private String imagePath;
    private String imageUrl;
    private Timestamp resultTime;
    private Timestamp createTime;
    private Long frame_index;
    private Double carspeed;
    private String labelInfoData;
    private String vehicleKind;
    private String vehicleBrand;
    private String vehicleSeries;
    private String vehicleStyle;
    private Short tag;
    private Short paper;
    private Short drop;
    private Short sun;
    private Short mainBelt;
    private Short secondBelt;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "SerialNumber", unique = true, nullable = false)
    public Long getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TaskID", nullable = false)
    public VlprTask getVlprTask() {
        return vlprTask;
    }

    public void setVlprTask(VlprTask vlprTask) {
        this.vlprTask = vlprTask;
    }

    @Column(name = "License", nullable = false, length = 32)
    public String getLicense() {
        return this.license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    @Column(name = "LicenseAttribution", nullable = false, length = 32)
    public String getLicenseAttribution() {
        return this.licenseAttribution;
    }

    public void setLicenseAttribution(String licenseAttribution) {
        this.licenseAttribution = licenseAttribution;
    }

    @Column(name = "PlateColor", nullable = false, length = 32)
    public String getPlateColor() {
        return this.plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    @Column(name = "PlateType", nullable = false)
    public Short getPlateType() {
        return this.plateType;
    }

    public void setPlateType(Short plateType) {
        this.plateType = plateType;
    }

    @Column(name = "Confidence", nullable = false)
    public Short getConfidence() {
        return this.confidence;
    }

    public void setConfidence(Short confidence) {
        this.confidence = confidence;
    }

    @Column(name = "Bright", nullable = false)
    public Short getBright() {
        return this.bright;
    }

    public void setBright(Short bright) {
        this.bright = bright;
    }

    @Column(name = "Direction", nullable = false)
    public Short getDirection() {
        return this.direction;
    }

    public void setDirection(Short direction) {
        this.direction = direction;
    }

    @Column(name = "LocationLeft", nullable = false)
    public Short getLocationLeft() {
        return this.locationLeft;
    }

    public void setLocationLeft(Short locationLeft) {
        this.locationLeft = locationLeft;
    }

    @Column(name = "LocationTop", nullable = false)
    public Short getLocationTop() {
        return this.locationTop;
    }

    public void setLocationTop(Short locationTop) {
        this.locationTop = locationTop;
    }

    @Column(name = "LocationRight", nullable = false)
    public Short getLocationRight() {
        return this.locationRight;
    }

    public void setLocationRight(Short locationRight) {
        this.locationRight = locationRight;
    }

    @Column(name = "LocationBottom", nullable = false)
    public Short getLocationBottom() {
        return this.locationBottom;
    }

    public void setLocationBottom(Short locationBottom) {
        this.locationBottom = locationBottom;
    }

    @Column(name = "CostTime", nullable = false)
    public Short getCostTime() {
        return this.costTime;
    }

    public void setCostTime(Short costTime) {
        this.costTime = costTime;
    }

    @Column(name = "CarBright", nullable = false, length = 32)
    public String getCarBright() {
        return this.carBright;
    }

    public void setCarBright(String carBright) {
        this.carBright = carBright;
    }

    @Column(name = "CarColor", nullable = false, length = 32)
    public String getCarColor() {
        return this.carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    @Column(name = "CarLogo", nullable = false, length = 32)
    public String getCarLogo() {
        return this.carLogo;
    }

    public void setCarLogo(String carLogo) {
        this.carLogo = carLogo;
    }

    @Column(name = "ImagePath", nullable = false, length = 1024)
    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Column(name = "ImageURL", nullable = false, length = 1024)
    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Column(name = "ResultTime", nullable = false, length = 19)
    public Timestamp getResultTime() {
        return this.resultTime;
    }

    public void setResultTime(Timestamp resultTime) {
        this.resultTime = resultTime;
    }

    @Column(name = "CreateTime", nullable = false, length = 19)
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "frame_index", nullable = true, length = 20)
    public Long getFrame_index() {
        return frame_index;
    }

    public void setFrame_index(Long frameIndex) {
        frame_index = frameIndex;
    }

    @Column(name = "carspeed", nullable = true)
    public Double getCarspeed() {
        return carspeed;
    }

    public void setCarspeed(Double carspeed) {
        this.carspeed = carspeed;
    }

    @Column(name = "LabelInfoData", nullable = true, length = 1024)
    public String getLabelInfoData() {
        return labelInfoData;
    }

    public void setLabelInfoData(String labelInfoData) {
        this.labelInfoData = labelInfoData;
    }

    @Column(name = "vehicleKind", nullable = true, length = 50)
    public String getVehicleKind() {
        return vehicleKind;
    }

    public void setVehicleKind(String vehicleKind) {
        this.vehicleKind = vehicleKind;
    }

    @Column(name = "vehicleBrand", nullable = true, length = 50)
    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    @Column(name = "vehicleSeries", nullable = true, length = 50)
    public String getVehicleSeries() {
        return vehicleSeries;
    }

    public void setVehicleSeries(String vehicleSeries) {
        this.vehicleSeries = vehicleSeries;
    }

    @Column(name = "vehicleStyle", nullable = true, length = 50)
    public String getVehicleStyle() {
        return vehicleStyle;
    }

    public void setVehicleStyle(String vehicleStyle) {
        this.vehicleStyle = vehicleStyle;
    }

    @javax.persistence.Column(name = "tag")
    public Short getTag() {
        return tag;
    }

    public void setTag(Short tag) {
        this.tag = tag;
    }

    @javax.persistence.Column(name = "paper")
    public Short getPaper() {
        return paper;
    }

    public void setPaper(Short paper) {
        this.paper = paper;
    }

    @javax.persistence.Column(name = "sun")
    public Short getSun() {
        return sun;
    }

    public void setSun(Short sun) {
        this.sun = sun;
    }

    @javax.persistence.Column(name = "drop")
    public Short getDrop() {
        return drop;
    }

    public void setDrop(Short drop) {
        this.drop = drop;
    }
    
  @javax.persistence.Column(name = "mainBelt")
	public Short getMainBelt() {
		return mainBelt;
	}

	public void setMainBelt(Short mainBelt) {
		this.mainBelt = mainBelt;
	}
	
	@javax.persistence.Column(name = "secondBelt")
	public Short getSecondBelt() {
		return secondBelt;
	}

	public void setSecondBelt(Short secondBelt) {
		this.secondBelt = secondBelt;
	}
}
