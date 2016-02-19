package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;
import java.util.Map;

/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable {

	// Fields

	private Integer id;
	private String loginName;
	private String pwd;
	private String realName;
	private String tel;
	private String dsc;
    private Integer departmentId;
    private Integer isValid;
    private Integer departmentAdministratorId;

    /**
     * 权限相关标志
     */
    private Long time;
    private Map actionPrivilegeMap;

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String loginName, String pwd) {
		this.loginName = loginName;
		this.pwd = pwd;
	}

	/** full constructor */
	public User(String loginName, String pwd, String realName, String tel, String dsc, Integer departmentId, Integer isValid, Integer departmentAdministratorId) {
		this.loginName = loginName;
		this.pwd = pwd;
		this.realName = realName;
		this.tel = tel;
		this.dsc = dsc;
        this.departmentId = departmentId;
        this.isValid = isValid;
        this.departmentAdministratorId = departmentAdministratorId;
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

	@Column(name = "loginName", nullable = false, length = 50)
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "pwd", nullable = false, length = 50)
	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "realName", length = 50)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "tel", length = 50)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "dsc", length = 250)
	public String getDsc() {
		return this.dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

    @Column(name = "departmentId")
    public Integer getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "isValid")
    public Integer getIsValid() {
        return this.isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    @Column(name = "departmentAdministratorId")
    public Integer getDepartmentAdministratorId() {
        return this.departmentAdministratorId;
    }

    public void setDepartmentAdministratorId(Integer departmentAdministratorId) {
        this.departmentAdministratorId = departmentAdministratorId;
    }

    @Transient
    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Transient
    public Map getActionPrivilegeMap() {
        return actionPrivilegeMap;
    }

    public void setActionPrivilegeMap(Map actionPrivilegeMap) {
        this.actionPrivilegeMap = actionPrivilegeMap;
    }

}