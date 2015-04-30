package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Datasource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "datasource")
public class Datasource implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String url;
	private Short type;
	private Integer cameraId;
	private String thumbnail;
	private String filePath;
	private Timestamp absoluteTime;
	private Timestamp createTime;
	private String bigUrl;
	private String followarea;
	private Integer parentId;

	// Constructors

	/** default constructor */
	public Datasource() {
	}

	/** minimal constructor */
	public Datasource(Short type, String filePath, Timestamp createTime) {
		this.type = type;
		this.filePath = filePath;
		this.createTime = createTime;
	}

	/** full constructor */
	public Datasource(String name, String url, Short type, Integer cameraId, String thumbnail, String filePath, Timestamp absoluteTime, Timestamp createTime,
			String bigUrl, String followarea, Integer parentId) {
		this.name = name;
		this.url = url;
		this.type = type;
		this.cameraId = cameraId;
		this.thumbnail = thumbnail;
		this.filePath = filePath;
		this.absoluteTime = absoluteTime;
		this.createTime = createTime;
		this.bigUrl = bigUrl;
		this.followarea = followarea;
		this.parentId = parentId;
	}

	// Property accessors
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

	@Column(name = "url", length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "type", nullable = false)
	public Short getType() {
		return this.type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	@Column(name = "cameraId")
	public Integer getCameraId() {
		return this.cameraId;
	}

	public void setCameraId(Integer cameraId) {
		this.cameraId = cameraId;
	}

	@Column(name = "thumbnail", length = 100)
	public String getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Column(name = "filePath", nullable = false)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "absoluteTime", length = 19)
	public Timestamp getAbsoluteTime() {
		return this.absoluteTime;
	}

	public void setAbsoluteTime(Timestamp absoluteTime) {
		this.absoluteTime = absoluteTime;
	}

	@Column(name = "createTime", nullable = false, length = 19)
	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "bigUrl", length = 100)
	public String getBigUrl() {
		return this.bigUrl;
	}

	public void setBigUrl(String bigUrl) {
		this.bigUrl = bigUrl;
	}

	@Column(name = "followarea")
	public String getFollowarea() {
		return this.followarea;
	}

	public void setFollowarea(String followarea) {
		this.followarea = followarea;
	}

	@Column(name = "parentId")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}