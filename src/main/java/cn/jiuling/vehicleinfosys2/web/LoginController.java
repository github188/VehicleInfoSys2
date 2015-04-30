package cn.jiuling.vehicleinfosys2.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.jiuling.vehicleinfosys2.listener.CustomSessionListener;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.service.UserService;
import cn.jiuling.vehicleinfosys2.util.MD5Utils;
import cn.jiuling.vehicleinfosys2.util.RandomString;

@Controller
public class LoginController extends BaseController {

	private static final String EXIST = "exist";

	private static final String NOT_EXIST = "notExist";

	private final MD5Utils md5 = new MD5Utils();

	@Resource
	private UserService userService;

	@RequestMapping(value = "login.action", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView m, HttpSession session) {

		// 生成六位随机的字符串
		RandomString randomStr = new RandomString(6);
		String rndStr = randomStr.nextString();
		session.setAttribute("rndStr", rndStr);

		return m;
	}

	@RequestMapping(value = "login.action", method = RequestMethod.POST)
	@ResponseBody
	public String valid(@RequestParam(defaultValue = "") String loginName, String pwd, HttpSession session, String rndStr) throws Exception {
		log.info("login.action,sid:" + session.getId());

		String str = "login";

		User user = userService.findUser(loginName);

		// MD5加密
		String uPwd = md5.encrypt(user.getPwd() + rndStr);

		Boolean valid = null != user && pwd.equals(uPwd) ? true : false;

		if (valid) {
			if (CustomSessionListener.isLogining(loginName)) {
				str = EXIST;
			} else {
				str = NOT_EXIST;
			}
		}
		if (str.equals(NOT_EXIST)) {
			session.setAttribute("user", user);
			CustomSessionListener.createUserSession(session, loginName);
		}
		return str;
	}

	@RequestMapping(value = "validPwd.action", method = RequestMethod.POST)
	@ResponseBody
	public Boolean valid(@RequestParam(required = false) Integer userId, String pwd, HttpSession session) {
		Boolean valid = false;
		try {
			User user = null;
			if (userId != null && userId > 0) {
				user = userService.findById(userId);
			} else {
				user = (User) session.getAttribute("user");
			}
			valid = user.getPwd().equals(pwd);
		} catch (Exception e) {
		}
		return valid;
	}

	@RequestMapping(value = "changePwd.action", method = RequestMethod.POST)
	@ResponseBody
	public Boolean changePwd(@RequestParam(required = false) Integer userId, String pwd, String newPwd, HttpSession session) {
		Boolean valid = false;

		try {
			User user = null;
			if (userId != null && userId > 0) {
				user = userService.findById(userId);
			} else {
				user = (User) session.getAttribute("user");
			}
			valid = userService.updatePwd(user, pwd, newPwd);
			if (valid) {
				user.setPwd(newPwd);
			}
		} catch (Exception e) {
		}
		return valid;
	}

	@RequestMapping(value = "logout.action")
	public String logout(HttpSession session) throws Exception {
		session.invalidate();
		return "redirect:login.action";
	}

	@RequestMapping(value = "forceLogin.action", method = RequestMethod.POST)
	@ResponseBody
	public Object forceLogin(@RequestParam(defaultValue = "") String loginName,
			String param,
			HttpSession session) {
		boolean flag = false;
		try {
			User user = userService.findUser(loginName);
			if (user != null) {
				flag = true;
				session.setAttribute("user", user);
			}
			if (flag) {
				if ("true".equals(param)) {
					flag = true;
					CustomSessionListener.replaceUserSession(session, loginName);
				} else {
					flag = false;
				}
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}