package cn.jiuling.vehicleinfosys2.tags;

import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.util.PrivilegeUtils;
import cn.jiuling.vehicleinfosys2.util.SpringBeanUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PrivilegeTag extends SimpleTagSupport {

	private String privilege;

	@Override
	public void doTag() throws javax.servlet.jsp.JspException, java.io.IOException {
		PrivilegeUtils p = (PrivilegeUtils) SpringBeanUtils.getBean("privilegeUtils");

		HttpSession s = ((PageContext) this.getJspContext()).getSession();
		Object o = s.getAttribute("user");
		User user = (User) o;
		if (p.hasPrivilege(user, getPrivilege())) {
			getJspBody().invoke(getJspContext().getOut());
		}
	};

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

}
