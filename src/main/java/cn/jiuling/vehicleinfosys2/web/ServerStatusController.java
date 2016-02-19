package cn.jiuling.vehicleinfosys2.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.service.UserService;
import cn.jiuling.vehicleinfosys2.util.SessionUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * 服务器状态
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("serverstatus")
public class ServerStatusController extends BaseController {

	private Logger log = Logger.getLogger(ServerStatusController.class);

	@Resource
	private UserService userService;

	@RequestMapping("index.action")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("serverstatus/index");
		return view;
	}

	@RequestMapping("list.action")
	@ResponseBody
	public Pager list(HttpSession session) {

		HashMap sessionMap = (HashMap) session.getAttribute("sessionMap");
		List list = new ArrayList();
		Iterator iter = sessionMap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			log.info("用户名:" + (String) val);
			if (SessionUtils.isInvalid(session)) {
				sessionMap.remove(key);
				continue;
			}
			User user = userService.findUser((String) (val));
			list.add(user);
		}
		Pager pager = new Pager();
		long count = list.size();
		pager.setRows(list);
		pager.setTotal(count);

		return pager;
	}

}
