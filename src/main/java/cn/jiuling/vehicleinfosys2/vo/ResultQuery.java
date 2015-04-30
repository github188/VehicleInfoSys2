package cn.jiuling.vehicleinfosys2.vo;

import java.sql.Timestamp;

public class ResultQuery {

	private String cameraIds;
	private Timestamp startTime;
	private Timestamp endTime;
	private String plate;
	private String location;
	private String plateColor;
	private String carColor;
	private String direction;
	private String vehicleKind;  
	private String vehicleBrand; 
	private String vehicleStyle;

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

	public String getVehicleStyle() {
    	return vehicleStyle;
    }

	public void setVehicleStyle(String vehicleStyle) {
    	this.vehicleStyle = vehicleStyle;
    }

}
