package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Dept entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dept")
public class Dept implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String dsc;
	private Integer parentId;
	private String ancestorId;
	private Short isEnable;
	private String parentName;

	// Constructors

	/** default constructor */
	public Dept() {
	}

	/** minimal constructor */
	public Dept(String name, Short isEnable) {
		this.name = name;
		this.isEnable = isEnable;
	}

	/** full constructor */
	public Dept(String name, String dsc, Integer parentId, String ancestorId, Short isEnable, String parentName) {
		this.name = name;
		this.dsc = dsc;
		this.parentId = parentId;
		this.ancestorId = ancestorId;
		this.isEnable = isEnable;
		this.parentName = parentName;
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

	@Column(name = "name", nullable = false, length = 100)
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

	@Column(name = "parentId")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "ancestorId", length = 100)
	public String getAncestorId() {
		return this.ancestorId;
	}

	public void setAncestorId(String ancestorId) {
		this.ancestorId = ancestorId;
	}

	@Column(name = "isEnable", nullable = false)
	public Short getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Short isEnable) {
		this.isEnable = isEnable;
	}

	@Column(name = "parentName", length = 150)
	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}