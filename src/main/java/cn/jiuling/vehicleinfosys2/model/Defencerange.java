package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Defencerange entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "defencerange")
public class Defencerange implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private String points;
	private String shape;
	private Integer userId;

	// Constructors

	/** default constructor */
	public Defencerange() {
	}

	/** full constructor */
	public Defencerange(String name, String points, String shape, Integer userId) {
		this.name = name;
		this.points = points;
		this.shape = shape;
		this.userId = userId;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "points", length = 5000)
	public String getPoints() {
		return this.points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	@Column(name = "shape", length = 50)
	public String getShape() {
		return this.shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	@Column(name = "userId")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}