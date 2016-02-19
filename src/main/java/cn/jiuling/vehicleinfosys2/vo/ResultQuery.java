package cn.jiuling.vehicleinfosys2.vo;

import java.sql.Timestamp;

public class ResultQuery {

	private String cameraIds;
	private Timestamp startTime;
	private Timestamp endTime;
	private String plate;
	private String plateType;
	private String location;
	private String plateColor;
	private String carColor;
	private String direction;
	private String vehicleKind;  
	private String vehicleBrand;
	private String vehicleSeries;
	private String vehicleStyle;
	private String characteristic;
	private Short confidence;
	private Short tag;
	private Short paper;
	private Short sun;
	private Short drop;
	private Long serialNumber;
	private Long taskId;
	private String license;
	private boolean accurate = false;


	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public String getPlateType() {
		return plateType;
	}

	public void setPlateType(String plateType) {
		this.plateType = plateType;
	}

	public String getCameraIds() {
		return cameraIds;
	}

	public void setCameraIds(String cameraIds) {
		this.cameraIds = cameraIds;
	}

	public String getLocation() {
    	return location;
    }

	public void setLocation(String location) {
    	this.location = location;
    }

	public String getPlateColor() {
    	return plateColor;
    }

	public void setPlateColor(String plateColor) {
    	this.plateColor = plateColor;
    }

	public String getCarColor() {
    	return carColor;
    }

	public void setCarColor(String carColor) {
    	this.carColor = carColor;
    }

	public String getDirection() {
    	return direction;
    }

	public void setDirection(String direction) {
    	this.direction = direction;
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

	public String getCharacteristic() {
    	return characteristic;
    }

	public void setCharacteristic(String characteristic) {
    	this.characteristic = characteristic;
    }

	public Short getConfidence() {
		return confidence;
	}

	public void setConfidence(Short confidence) {
		this.confidence = confidence;
	}

	public Short getDrop() {
		return drop;
	}

	public void setDrop(Short drop) {
		this.drop = drop;
	}

	public Short getPaper() {
		return paper;
	}

	public void setPaper(Short paper) {
		this.paper = paper;
	}

	public Short getSun() {
		return sun;
	}

	public void setSun(Short sun) {
		this.sun = sun;
	}

	public Short getTag() {
		return tag;
	}

	public void setTag(Short tag) {
		this.tag = tag;
	}

	public Long getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public boolean isAccurate() {
		return accurate;
	}

	public void setAccurate(boolean accurate) {
		this.accurate = accurate;
	}
				
}
