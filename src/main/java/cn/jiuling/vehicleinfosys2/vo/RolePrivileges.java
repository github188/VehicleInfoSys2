package cn.jiuling.vehicleinfosys2.vo;

import java.util.Collection;
import java.util.List;

public class RolePrivileges {
	private Collection allPrivileges;
	private List rolePrivileges;
	
	public Collection getAllPrivileges() {
		return allPrivileges;
	}
	public void setAllPrivileges(Collection allPrivileges) {
		this.allPrivileges = allPrivileges;
	}
	public List getRolePrivileges() {
		return rolePrivileges;
	}
	public void setRolePrivileges(List rolePrivileges) {
		this.rolePrivileges = rolePrivileges;
	}

}
