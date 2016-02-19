package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "vlrp_firstIntoPosInfo")
public class VlprFirstIntoPosInfo implements java.io.Serializable {

	private Long id;
	private Long threadPosition;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "threadPosition", length = 20)
	public Long getThreadPosition() {
		return threadPosition;
	}

	public void setThreadPosition(Long threadPosition) {
		this.threadPosition = threadPosition;
	}

}
