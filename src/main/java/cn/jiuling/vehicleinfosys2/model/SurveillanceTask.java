package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2015/5/26 0026.
 */
@Entity
@Table(name = "surveillance_task")
public class SurveillanceTask {
    private Integer id;
    private String name;
    private String plate;
    private Timestamp startTime;
    private Timestamp doTime;
    private Timestamp endTime;
    private Short status;
    private Short plateType;
    private String carcolor;
    private String camera;
    private String vehicleKind;
    private String vehicleBrand;
    private String vehicleSeries;
    private String vehicleStyle;
    private Short tag;
    private Short paper;
    private Short sun;
    private Short drop;
    private String peopleName;
    private String peopleTel;
    private String unitName;
    private String detail;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "plate")
    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    @Basic
    @Column(name = "startTime")
    public Timestamp getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    
    @Basic
    @Column(name = "doTime")
    public Timestamp getDoTime() {
		return doTime;
	}

	public void setDoTime(Timestamp doTime) {
		this.doTime = doTime;
	}

	@Basic
    @Column(name = "endTime")
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Basic
    @Column(name = "status")
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Basic
    @Column(name = "plateType")
    public Short getPlateType() {
        return plateType;
    }


    public void setPlateType(Short plateType) {
        this.plateType = plateType;
    }

    @Basic
    @Column(name = "carcolor")
    public String getCarcolor() {
        return carcolor;
    }

    public void setCarcolor(String carcolor) {
        this.carcolor = carcolor;
    }

    @Basic
    @Column(name = "camera")
    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    @Basic
    @Column(name = "vehicleKind")
    public String getVehicleKind() {
        return vehicleKind;
    }

    public void setVehicleKind(String vehicleKind) {
        this.vehicleKind = vehicleKind;
    }

    @Basic
    @Column(name = "vehicleBrand")
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

    @Basic
    @Column(name = "vehicleStyle")
    public String getVehicleStyle() {
        return vehicleStyle;
    }

    public void setVehicleStyle(String vehicleStyle) {
        this.vehicleStyle = vehicleStyle;
    }

    @Basic
    @Column(name = "tag")
    public Short getTag() {
        return tag;
    }

    public void setTag(Short tag) {
        this.tag = tag;
    }

    @Basic
    @Column(name = "paper")
    public Short getPaper() {
        return paper;
    }

    public void setPaper(Short paper) {
        this.paper = paper;
    }

    @Basic
    @Column(name = "sun")
    public Short getSun() {
        return sun;
    }

    public void setSun(Short sun) {
        this.sun = sun;
    }

    @Basic
    @Column(name = "`drop`")
    public Short getDrop() {
        return drop;
    }

    public void setDrop(Short drop) {
        this.drop = drop;
    }
    
    @Basic
    @Column(name = "peopleName")
	public String getPeopleName() {
		return peopleName;
	}

	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}
	
	@Basic
    @Column(name = "peopleTel")
	public String getPeopleTel() {
		return peopleTel;
	}

	public void setPeopleTel(String peopleTel) {
		this.peopleTel = peopleTel;
	}
	
	@Basic
    @Column(name = "unitName")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	@Basic
    @Column(name = "detail")
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
    
}
