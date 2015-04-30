package cn.jiuling.vehicleinfosys2.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import cn.jiuling.vehicleinfosys2.vo.MapSettings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jiuling.vehicleinfosys2.model.Defencerange;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.service.DefencerangeService;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@Controller
@RequestMapping("map")
public class MapController extends BaseController {
	@Resource
	private DefencerangeService defencerangeService;

	@RequestMapping("listDefenceRange.action")
	@ResponseBody
	public Pager list(HttpSession session,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		User user = (User) session.getAttribute("user");
		Pager p = defencerangeService.list(user, page, rows);
		return p;
	}

	@RequestMapping("saveDefenceRange.action")
	@ResponseBody
	public Defencerange saveDefenceRange(Defencerange defenceRange, HttpSession session) {
		User user = (User) session.getAttribute("user");
		defencerangeService.save(user, defenceRange);
		return defenceRange;
	}

	@RequestMapping("delDefenceRange.action")
	@ResponseBody
	public String delDefenceRange(Integer id) {
		defencerangeService.del(id);
		return SUCCESS;
	}


	@RequestMapping("setCenter.action")
	 @ResponseBody
	 public String delDefenceRange(String latitude,String longitude) {
		MapSettings.getInstance().setCenterLat(latitude);
		MapSettings.getInstance().setCenterLng(longitude);
		return SUCCESS;
	}

}
