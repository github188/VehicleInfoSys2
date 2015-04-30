package cn.jiuling.vehicleinfosys2.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private final Logger log = Logger.getLogger(LoginInterceptor.class);
	private static final String LOGIN_PATH = "/login.action";

	/*
	 *以下路径不需要刷新lastAccessedTime 
	 */
	private static final String FAILLOG_PATH = "/faillog/task.action";
	private static final String SERVER_PATH = "/server/list.action";

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		Object user = WebUtils.getSessionAttribute(request, "user");

		String uri = request.getRequestURI();
		if (null == user) {
			if (uri.startsWith(LOGIN_PATH)) {
				return true;
			}
			response.sendRedirect(LOGIN_PATH);
			return false;
		} else {
			HttpSession session = request.getSession();

			if (uri.startsWith(LOGIN_PATH)) {
				response.sendRedirect("/");
				return false;
			}

			if (!uri.startsWith(FAILLOG_PATH) && !uri.startsWith(SERVER_PATH)) {
				session.setAttribute("lastAccessedTime", session.getLastAccessedTime());
			}
			return true;
		}
	}

}
