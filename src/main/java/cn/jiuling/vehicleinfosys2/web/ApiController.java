package cn.jiuling.vehicleinfosys2.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.jiuling.vehicleinfosys2.util.UploadStatusCache;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import cn.jiuling.vehicleinfosys2.util.JsonUtils;
import cn.jiuling.vehicleinfosys2.vo.IpVo;

import java.util.Date;

/**
 * 对外的接口
 * 
 * @author phq
 * 
 * @date 2015-04-02
 */
@Controller
@RequestMapping(value = "api")
public class ApiController extends BaseController {
	private static Logger logger = Logger.getLogger(ApiController.class);

	@RequestMapping("ip.action")
	@ResponseBody
	public String ip(HttpServletRequest request) {
		String ip = request.getRemoteHost();

		IpVo ipVo = new IpVo(ip);
		return JsonUtils.toJson(ipVo);
	}

	/**
	 * 判断用户是否登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("isLogin.action")
	@ResponseBody
	public Object isLogin(HttpServletRequest request, Long timestamp) {

		HttpSession session = request.getSession();

		long maxInactive = (long) session.getMaxInactiveInterval();
		Object lastAccessedTimeVal = session.getAttribute("lastAccessedTime");
		if (null != lastAccessedTimeVal) {
			long lastAccessedTime = (Long) lastAccessedTimeVal;
			// +3保证在失效前销毁session
			if(timestamp == null){
				timestamp = new Date().getTime();
			}
			long idleTime = (timestamp - lastAccessedTime) / 1000 + 3;
			if (idleTime >= maxInactive) {
				session.invalidate();
			}
		}

		Object user = WebUtils.getSessionAttribute(request, "user");
		return user != null;
	}

	@RequestMapping(value = "stopUpload.action")
	@ResponseBody
	public void stopUpload(String uuid) throws Exception {
		UploadStatusCache.remove(uuid);
	}

}
