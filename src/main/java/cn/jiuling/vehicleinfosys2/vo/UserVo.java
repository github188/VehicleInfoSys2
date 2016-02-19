package cn.jiuling.vehicleinfosys2.vo;

public class UserVo {

	private Integer id;
	private String loginName;
    private String name;
	private String tel;
	private String dsc;
    private Integer departmentId;
    private String depName;
    private Integer isValid;
    private Integer departmentAdministratorId;

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

	public String getDsc() {
		return dsc;
	}

	public void setDsc(String dsc) {
		this.dsc = dsc;
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

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getDepartmentAdministratorId() {
        return departmentAdministratorId;
    }

    public void setDepartmentAdministratorId(Integer departmentAdministratorId) {
        this.departmentAdministratorId = departmentAdministratorId;
    }

    public UserVo(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public UserVo(Integer id, String loginName, String name, String tel, String dsc, Integer departmentId, String depName, Integer isValid, Integer departmentAdministratorId) {
        super();
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.tel = tel;
        this.dsc = dsc;
        this.departmentId = departmentId;
        this.depName = depName;
        this.isValid = isValid;
        this.departmentAdministratorId = departmentAdministratorId;
    }

    public UserVo(Object[] o) {
        this.setId((Integer) o[0]);
        this.setLoginName((String) o[1]);
        this.setName((String) o[2]);
        this.setTel((String) o[3]);
        this.setDsc((String) o[4]);
        this.setDepartmentId((Integer) o[5]);
        this.setDepName((String) o[6]);
        this.setIsValid((Integer) o[7]);
        this.setDepartmentAdministratorId((Integer) o[8]);
    }

}
