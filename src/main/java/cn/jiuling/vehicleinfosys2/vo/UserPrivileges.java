package cn.jiuling.vehicleinfosys2.vo;

import java.util.Collection;
import java.util.List;

public class UserPrivileges {
	private Collection allPrivileges;
	private List userPrivileges;

	private List roles;
	private List ownRoles;

	public Collection getAllPrivileges() {
		return allPrivileges;
	}

	public void setAllPrivileges(Collection allPrivileges) {
		this.allPrivileges = allPrivileges;
	}

	public List getUserPrivileges() {
		return userPrivileges;
	}

	public void setUserPrivileges(List userPrivileges) {
		this.userPrivileges = userPrivileges;
	}

	public List getRoles() {
		return roles;
	}

	public void setRoles(List roles) {
		this.roles = roles;
	}

	public List getOwnRoles() {
		return ownRoles;
	}

	public void setOwnRoles(List ownRoles) {
		this.ownRoles = ownRoles;
	}

}
