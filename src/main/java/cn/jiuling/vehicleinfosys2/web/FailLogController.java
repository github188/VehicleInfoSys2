package cn.jiuling.vehicleinfosys2.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jiuling.vehicleinfosys2.service.FaillogService;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * 对外的接口
 * 
 * @author phq
 * 
 * @date 2015-04-02
 */
@Controller
@RequestMapping(value = "faillog")
public class FailLogController extends BaseController {

	@Resource
	private FaillogService faillogService;

	@RequestMapping("task.action")
	@ResponseBody
	public Pager faillogTask(Integer id,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "5") Integer rows) {
		return faillogService.findFaillogTask(id, page, rows);
	}
}
