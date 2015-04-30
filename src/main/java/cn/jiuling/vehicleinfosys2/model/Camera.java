package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Camera entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "camera")
public class Camera implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String dsc;
	private Short type;
	private Double longitude;
	private Double latitude;
	private Short status;
	private String model;
	private Integer port1;
	private Integer port2;
	private String account;
	private String password;
	private Integer channel;
	private Integer brandId;
	private String brandName;
	private String ip;
	private String url;
	private String location;
	private String direction;
	
	private Integer extCameraId;
	private String followarea;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "dsc", length = 250)
	public String getDsc() {
		return this.dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	@Column(name = "type")
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "longitude", precision = 22, scale = 0)
	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Column(name = "latitude", precision = 22, scale = 0)
	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	@Column(name = "status")
	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Column(name = "model", length = 50)
	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name = "port1")
	public Integer getPort1() {
		return this.port1;
	}

	public void setPort1(Integer port1) {
		this.port1 = port1;
	}

	@Column(name = "port2")
	public Integer getPort2() {
		return this.port2;
	}

	public void setPort2(Integer port2) {
		this.port2 = port2;
	}

	@Column(name = "account", length = 50)
	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "channel")
	public Integer getChannel() {
		return this.channel;
	}

	public void setChannel(Integer channel) {
		this.channel = channel;
	}

	@Column(name = "brandId")
	public Integer getBrandId() {
		return this.brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	@Column(name = "brandName", length = 50)
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Column(name = "ip", length = 50)
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "url", length = 100)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "location")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "direction", length = 100)
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Column(name = "extCameraId")
	public Integer getExtCameraId() {
		return extCameraId;
	}

	public void setExtCameraId(Integer extCameraId) {
		this.extCameraId = extCameraId;
	}
	
	@Column(name = "followarea")
	public String getFollowarea() {
    	return followarea;
    }

	public void setFollowarea(String followarea) {
    	this.followarea = followarea;
    }

}