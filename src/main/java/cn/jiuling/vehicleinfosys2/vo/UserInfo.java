package cn.jiuling.vehicleinfosys2.vo;

public class UserInfo {
	private Integer id;
	private String loginName;
    private String name;
    private String tel;
    private Integer departmentId;
    private String depName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	
	public UserInfo(){};
    
	public UserInfo(Object[] o) {
        this.setId((Integer) o[0]);
        this.setLoginName((String) o[1]);
        this.setName((String) o[2]);
        this.setTel((String) o[3]);
        this.setDepartmentId((Integer) o[4]);
        this.setDepName((String) o[5]);
    }
}
