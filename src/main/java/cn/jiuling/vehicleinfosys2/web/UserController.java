package cn.jiuling.vehicleinfosys2.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import cn.jiuling.vehicleinfosys2.annotation.Auditable;
import cn.jiuling.vehicleinfosys2.util.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.service.UserService;
import cn.jiuling.vehicleinfosys2.service.PrivilegeService;
import cn.jiuling.vehicleinfosys2.util.PrivilegeUtils;
import cn.jiuling.vehicleinfosys2.vo.UserPrivileges;

/**
 * 用户相关Controller
 * 
 * @author phq
 * 
 * @date 2015-01-21
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {
	@Resource
	private UserService userService;

    @Resource
    private PrivilegeService privilegeService;

    @Resource
    private PrivilegeUtils privilegeUtils;

	@RequestMapping("index.action")
	public void index(Integer id) {
	}

	@RequestMapping("list.action")
	@ResponseBody
	public Object list(
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		return userService.list(page, rows);
	}

    @Auditable(remark = "添加用户",operType = Constant.USER_OPRE_TYPE_ADD)
	@RequestMapping("add.action")
	@ResponseBody
	public Object add(User user) {
		userService.add(user);
		return SUCCESS;
	}

    @Auditable(remark = "删除用户",operType = Constant.USER_OPRE_TYPE_DEL)
	@RequestMapping("del.action")
	@ResponseBody
	public Object del(String ids, HttpSession s) {
		User user = (User) s.getAttribute("user");
		userService.del(ids, user);
		return SUCCESS;
	}

    @Auditable(remark = "修改用户",operType = Constant.USER_OPRE_TYPE_UPDATE)
	@RequestMapping("edit.action")
	@ResponseBody
	public Object edit(User user) {
		userService.update(user);
		return SUCCESS;
	}
	
	@RequestMapping(value = "isExistUser.action", method = RequestMethod.POST)
	@ResponseBody
	public boolean isExistUser(String loginName) {
		boolean valid = false;
		User user = userService.findUser(loginName);
		if (user == null) {
	        valid = true;
        }
		return valid;
	}

    @RequestMapping("privileges.action")
    @ResponseBody
    public UserPrivileges privilege(User u) {
        UserPrivileges userPrivileges = privilegeService.findUserPrivilege(u.getId());
        return userPrivileges;
    }

    @RequestMapping("updatePrivilege.action")
    @ResponseBody
    public String updatePrivilege(
            @RequestParam(required = true, defaultValue = "") Integer[] prvList,
            @RequestParam(required = true, defaultValue = "") Integer[] roleList,
            @RequestParam(required = true) Integer userId) {
        privilegeService.updateUserPrivilege(userId, prvList);
        privilegeService.updateUserRoles(userId, roleList);
        privilegeUtils.updatePrivilegeFlag();
        return SUCCESS;
    }
	
}
