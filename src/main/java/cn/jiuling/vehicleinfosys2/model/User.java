package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
	private String name;
	private String tel;
	private String dsc;

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
	public User(String loginName, String pwd, String name, String tel, String dsc) {
		this.loginName = loginName;
		this.pwd = pwd;
		this.name = name;
		this.tel = tel;
		this.dsc = dsc;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

}