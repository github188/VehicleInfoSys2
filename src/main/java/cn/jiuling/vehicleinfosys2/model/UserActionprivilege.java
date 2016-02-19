package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * UserActionprivilege entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user_actionprivilege")
public class UserActionprivilege implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer userId;
	private Integer privilegeId;

	// Constructors

	/** default constructor */
	public UserActionprivilege() {
	}

	/** full constructor */
	public UserActionprivilege(Integer userId, Integer privilegeId) {
		this.userId = userId;
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

	@Column(name = "userId", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "privilegeId", nullable = false)
	public Integer getPrivilegeId() {
		return this.privilegeId;
	}

	public void setPrivilegeId(Integer privilegeId) {
		this.privilegeId = privilegeId;
	}

}