package cn.jiuling.vehicleinfosys2.vo;

public class UserVo {

	private Integer id;
	private String name;
	private String loginName;
	private String tel;
	private String dsc;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserVo(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getDsc() {
		return dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
	}

	public UserVo(Integer id, String name, String loginName, String tel, String dsc) {
		super();
		this.id = id;
		this.name = name;
		this.loginName = loginName;
		this.tel = tel;
		this.dsc = dsc;
	}

}
