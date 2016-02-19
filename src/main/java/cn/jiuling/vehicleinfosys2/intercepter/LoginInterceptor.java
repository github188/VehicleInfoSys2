package cn.jiuling.vehicleinfosys2.intercepter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import cn.jiuling.vehicleinfosys2.exception.ServiceException;
import cn.jiuling.vehicleinfosys2.model.Actionprivilege;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.util.PrivilegeUtils;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private final Logger log = Logger.getLogger(LoginInterceptor.class);
	private static final String LOGIN_PATH = "/login.action";

	/*
	 *以下路径不需要刷新lastAccessedTime 
	 */
	private static final String FAILLOG_PATH = "/faillog/task.action";
	private static final String SERVER_PATH = "/server/list.action";
	private static final String SURVEILLANCE_PATH = "/surveillance/checkResult.action";
	private static final String TASK_LIST ="/task/list.action";
	private static final String TASK_QUERY ="/task/query.action";
	private static final String SURVEILLANCE_QUERY= "/surveillance/query.action";

    @Resource
    private PrivilegeUtils privilegeUtils;

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

			if (!uri.startsWith(SURVEILLANCE_QUERY)  && !uri.startsWith(TASK_QUERY) && !uri.startsWith(TASK_LIST) && !uri.startsWith(FAILLOG_PATH) && !uri.startsWith(SERVER_PATH) && !uri.startsWith(SURVEILLANCE_PATH)) {
				session.setAttribute("lastAccessedTime", session.getLastAccessedTime());
			}

			User u = (User) user;
            if (!privilegeUtils.isValid(u)) {
                response.sendRedirect(LOGIN_PATH);
                return false;
            }

            String loginName = u.getLoginName();
            if (!privilegeUtils.hasPrivilege(u, uri)) {
                Actionprivilege a = privilegeUtils.getActionprivilege(uri);
                String message = "用户[" + loginName + "]没有[" + a.getResourceName() + "-->" + a.getActionName() + "]权限!";
                throw new ServiceException(message);
            }
			return true;
		}

	}

}
