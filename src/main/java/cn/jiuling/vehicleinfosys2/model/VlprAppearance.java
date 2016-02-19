package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vlrp_appearance")
public class VlprAppearance implements java.io.Serializable {

	private Long id;
	private Long threadPosition;
	private Long pagePosition;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "threadPosition", nullable = false, length = 20)
	public Long getThreadPosition() {
		return threadPosition;
	}

	public void setThreadPosition(Long threadPosition) {
		this.threadPosition = threadPosition;
	}
	
	@Column(name = "pagePosition", nullable = false, length = 20)
	public Long getPagePosition() {
		return pagePosition;
	}

	public void setPagePosition(Long pagePosition) {
		this.pagePosition = pagePosition;
	}

}
