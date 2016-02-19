package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Actionprivilege entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "actionprivilege")
public class Actionprivilege implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer resourceId;
	private String resourceName;
	private Integer actionId;
	private String actionName;
	private String code;

	// Constructors

	/** default constructor */
	public Actionprivilege() {
	}

	/** minimal constructor */
	public Actionprivilege(Integer resourceId) {
		this.resourceId = resourceId;
	}

	/** full constructor */
	public Actionprivilege(Integer resourceId, String resourceName, Integer actionId, String actionName, String code) {
		this.resourceId = resourceId;
		this.resourceName = resourceName;
		this.actionId = actionId;
		this.actionName = actionName;
		this.code = code;
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

	@Column(name = "resourceId", nullable = false)
	public Integer getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Column(name = "resourceName", length = 15)
	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "actionId")
	public Integer getActionId() {
		return this.actionId;
	}

	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}

	@Column(name = "actionName", length = 50)
	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	@Column(name = "code", length = 50)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}