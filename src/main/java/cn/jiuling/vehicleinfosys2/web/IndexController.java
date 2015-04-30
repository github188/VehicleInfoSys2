package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.MapSettings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName: IndexController
 * @Description: 主页跳转控制
 * @author Gost_JR
 * @date: 2014-12-5 上午10:46:55
 */
@Controller
public class IndexController extends BaseController {

	/**
	 * @Title: index
	 * @Description: 主页跳转
	 * @ReturnType: void
	 */
	@RequestMapping("home.action")
	public void index(Model m) {
		String serverIpKey = "serverIp";
		String serverIp = PropertiesUtils.get(serverIpKey);
		m.addAttribute(serverIpKey, serverIp);
		m.addAttribute("mapSettings", MapSettings.getInstance());
	}

	@RequestMapping("info.action")
	public void info(Model m) {
		String version = PropertiesUtils.get("version");
		m.addAttribute("version", version);
	}
}
