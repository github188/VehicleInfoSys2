package cn.jiuling.vehicleinfosys2.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileProgressController {

	@RequestMapping(value = "progress.action")
	@ResponseBody
	public Object progress(HttpSession s) {
		Object o = s.getAttribute("progress");
		Long progress = 0L;
		if (null != o) {
			progress = (Long) o;
		}
		return progress;
	}

}
