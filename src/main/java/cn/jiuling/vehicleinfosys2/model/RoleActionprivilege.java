package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * RoleActionprivilege entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "role_actionprivilege")
public class RoleActionprivilege implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer roleId;
	private Integer privilegeId;

	// Constructors

	/** default constructor */
	public RoleActionprivilege() {
	}

	/** full constructor */
	public RoleActionprivilege(Integer roleId, Integer privilegeId) {
		this.roleId = roleId;
		this.privilegeId = privilegeId;
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

	@Column(name = "roleId", nullable = false)
	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "privilegeId", nullable = false)
	public Integer getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

}