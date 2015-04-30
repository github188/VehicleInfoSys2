package cn.jiuling.vehicleinfosys2.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.jiuling.vehicleinfosys2.service.MessageService;

/**
 * 用户相关Controller
 * 
 * @author phq
 * 
 * @date 2015-01-21
 */
@Controller
@RequestMapping("msg")
public class SendMsgController extends BaseController {
	@Resource
	private MessageService messageService;

	@RequestMapping("index.action")
	public void index(Integer id) {
	}

	@RequestMapping("send.action")
	@ResponseBody
	public Object send(final String msg) {
		messageService.sendMsg(msg);
		return "发送成功：" + msg;
	}
}
