package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Sysresource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sysresource")
public class Sysresource implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String dsc;
	private Short type;

	// Constructors

	/** default constructor */
	public Sysresource() {
	}

	/** minimal constructor */
	public Sysresource(String name) {
		this.name = name;
	}

	/** full constructor */
	public Sysresource(String name, String dsc, Short type) {
		this.name = name;
		this.dsc = dsc;
		this.type = type;
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

	@Column(name = "name", nullable = false, length = 20)
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

}