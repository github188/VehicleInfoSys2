package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.model.Role;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.service.PrivilegeService;
import cn.jiuling.vehicleinfosys2.service.RoleService;
import cn.jiuling.vehicleinfosys2.util.PrivilegeUtils;
import cn.jiuling.vehicleinfosys2.vo.RolePrivileges;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Controller
@RequestMapping(value = "role")
public class RoleController extends BaseController {
	private Logger log = Logger.getLogger(RoleController.class);
	@Resource
	private RoleService roleService;
	@Resource
	private PrivilegeService privilegeService;
	@Resource
	private PrivilegeUtils privilegeUtils;

	@RequestMapping("role.action")
	public void index() {
	}

	@RequestMapping("list.action")
	@ResponseBody
	public Object list(Role r,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows) {
		return roleService.list(r, page, rows);
	}

	@RequestMapping(value = "add.action")
	@ResponseBody
	public Object list(Role r, HttpSession session) {
		Integer userId = 1;
		String createName = "admin";
		User u = (User) session.getAttribute("user");
		if (null != u) {
			userId = u.getId();
			createName = u.getLoginName();
		}
		r.setCreateId(userId);
		r.setCreateName(createName);
		r.setCreateTime(new Timestamp(System.currentTimeMillis()));
		roleService.add(r);
		return SUCCESS;
	}

	@RequestMapping("del.action")
	@ResponseBody
	public Object del(Integer id) {
		roleService.del(id);
        roleService.delete(id);
		return SUCCESS;
	}

	@RequestMapping("update.action")
	@ResponseBody
	public Object update(Role r) {
        if(r.getId()!=null){
            roleService.delete(r.getId());
        }
        roleService.update(r);
		return SUCCESS;
	}

	@RequestMapping("detail.action")
	public void detail(Role r) {
	}

	@RequestMapping("findPrivileges.action")
	@ResponseBody
	public RolePrivileges findPrivileges(Integer roleId) {
		return privilegeService.findRolePrivilege(roleId);
	}

	@RequestMapping("updatePrivileges.action")
	@ResponseBody
	public String updatePrivileges(
			@RequestParam(required = true, defaultValue = "") Integer [] prvList,
			@RequestParam(required = true) Integer roleId) {
		privilegeService.updateRolePrivilege(roleId, prvList);
		privilegeUtils.updatePrivilegeFlag();
		return SUCCESS;
	}

	@RequestMapping("findRoleActionprivilege.action")
	@ResponseBody
	public Object findRoleActionprivilege(Integer []roleIds) {
		return privilegeService.getRoleActionprivilegeList(roleIds);
	}
}
