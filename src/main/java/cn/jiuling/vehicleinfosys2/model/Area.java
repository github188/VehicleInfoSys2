package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Area entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "area")
public class Area implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String dsc;
	private String areaCode;
	private Integer parentId;
	private String parentName;
	private String ancestorId;
	private Short isEnable;

	// Constructors

	/** default constructor */
	public Area() {
	}

	/** minimal constructor */
	public Area(String name, Short isEnable) {
		this.name = name;
		this.isEnable = isEnable;
	}

	/** full constructor */
	public Area(String name, String dsc, String areaCode, Integer parentId, String parentName, String ancestorId, Short isEnable) {
		this.name = name;
		this.dsc = dsc;
		this.areaCode = areaCode;
		this.parentId = parentId;
		this.parentName = parentName;
		this.ancestorId = ancestorId;
		this.isEnable = isEnable;
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

	@Column(name = "areaCode", length = 50)
	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	@Column(name = "parentId")
	public Integer getParentId() {
		return this.parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "parentName", length = 100)
	public String getParentName() {
		return this.parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

}