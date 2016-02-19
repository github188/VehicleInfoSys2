package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.service.RecognumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("recognum")
public class RecognumController extends BaseController {

	@Resource
	private RecognumService recognumService;

	@RequestMapping("checkRecognum.action")
	@ResponseBody
	public Object checkRecognum() {
		boolean flag = recognumService.checkRecognum(); //如果识别数量大于每天最大识别数,则返回false
		if(flag){
			return SUCCESS;
		}
		return "failed";
	}

}
