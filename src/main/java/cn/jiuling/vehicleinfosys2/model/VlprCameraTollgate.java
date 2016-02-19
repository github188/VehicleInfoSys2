package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 监控点与卡口编码关系信息(吉林白山)
 * Created by wangrb on 2015/10/19.
 */
@Entity
@Table(name = "vlpr_camera_tollgate")
public class VlprCameraTollgate implements java.io.Serializable {

	private Integer id;
	private Integer cameraId;
	private String tollgateId;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "camera_id", nullable = false)
	public Integer getCameraId() {
		return cameraId;
	}

	public void setCameraId(Integer cameraId) {
		this.cameraId = cameraId;
	}

	@Column(name = "tollgate_id", nullable = false)
	public String getTollgateId() {
		return tollgateId;
	}

	public void setTollgateId(String tollgateId) {
		this.tollgateId = tollgateId;
	}
}
