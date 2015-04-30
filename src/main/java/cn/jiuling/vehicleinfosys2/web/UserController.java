package cn.jiuling.vehicleinfosys2.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.service.UserService;

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

	@RequestMapping("add.action")
	@ResponseBody
	public Object add(User user) {
		userService.add(user);
		return SUCCESS;
	}

	@RequestMapping("del.action")
	@ResponseBody
	public Object del(String ids, HttpSession s) {
		User user = (User) s.getAttribute("user");
		userService.del(ids, user);
		return SUCCESS;
	}

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
	
}
