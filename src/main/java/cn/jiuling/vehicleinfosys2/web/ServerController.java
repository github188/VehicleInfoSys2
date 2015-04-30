package cn.jiuling.vehicleinfosys2.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jiuling.vehicleinfosys2.model.Serverinfo;
import cn.jiuling.vehicleinfosys2.service.ServerService;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@Controller
@RequestMapping("server")
public class ServerController extends BaseController {

	@Resource
	private ServerService serverService;

	@RequestMapping("list.action")
	@ResponseBody
	public Pager list(
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows,
			@RequestParam(defaultValue = "ip") String sort,
			@RequestParam(defaultValue = "asc") String order

	) {
		return serverService.listAndPublish(page, rows, sort, order);
	}
	
	@RequestMapping(value = "remove.action")
	@ResponseBody
	public String remove(Serverinfo s) {
		serverService.remove(s);
		return SUCCESS;
	}
}
