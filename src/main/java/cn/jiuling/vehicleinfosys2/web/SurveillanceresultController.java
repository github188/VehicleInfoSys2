package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.service.SurveillanceresultService;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 布控结果Controller
 * 
 * @author bl
 * 
 * @date 2015-07-25
 */
@Controller
@RequestMapping("surveillanceresult")
public class SurveillanceresultController {
	@Resource
	private SurveillanceresultService surveillanceresultService;

	@RequestMapping("list.action")
	@ResponseBody
	public Pager list(Integer surveillanceTaskId,
			          @RequestParam(required = false, defaultValue = "1") Integer page,
					  @RequestParam(required = false, defaultValue = "10") Integer rows) {
		Pager p = surveillanceresultService.querylist(surveillanceTaskId, page, rows);
		return p;
	}

/*	@RequestMapping("delete.action")
	@ResponseBody
	public boolean delete(Integer surveillanceTaskId) {
		boolean p = surveillanceresultService.delete(surveillanceTaskId);
		return p;
	}*/
	
}
