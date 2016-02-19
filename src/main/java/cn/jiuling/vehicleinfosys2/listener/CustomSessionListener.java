package cn.jiuling.vehicleinfosys2.listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class CustomSessionListener implements HttpSessionListener {
	private static HashMap sessionMap = new HashMap();
	private static Logger logger = Logger.getLogger(CustomSessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		se.getSession().setAttribute("sessionMap", sessionMap);
		logger.info("*****************the  http session " + se.getSession().getId() + " is created...***************");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		sessionMap.remove(se.getSession());
		try {
			se.getSession().setAttribute("sessionMap", sessionMap);
		} catch (IllegalStateException e) {
			logger.error("session 状态异常");
		}
		logger.info("***************the  http session " + se.getSession().getId() + " is destroyed...***************");
	}

	/*
	 * 判断用户是否已经登录
	 * @param loginName
	 * @return
	 * @throws Exception
	 */
	public static boolean isLogining(String loginName) throws Exception {
		return sessionMap.containsValue(loginName);
	}

	/* 
	 * isOnline-用于判断用户是否在线 
	 * @param session HttpSession-登录的用户名称 
	 * @return boolean-该用户是否在线的标志 
	 */
	public static boolean isOnline(HttpSession session) throws Exception {
		return sessionMap.containsKey(session);
	}

	/* 
	* createUserSession-用于建立用户session 
	* @param sessionUserName String-登录的用户名称 
	*/
	public static void createUserSession(HttpSession session, String loginName) throws Exception {
		sessionMap.put(session, loginName);
		session.setAttribute("sessionMap", sessionMap);
	}

	/* 
	* createUserSession-根据用户名剔除session 
	* @param loginName String-登录的用户名称 
	*/
	public static void removeUserSession(String loginName) throws Exception {
		Iterator iter = sessionMap.entrySet().iterator();
		HttpSession session = null;
		String value = null;
		Set s = sessionMap.keySet();
		for (Object key : s) {
			session = (HttpSession) key;
			value = (String) sessionMap.get(key);
			if (value.equals(loginName)) {
				logger.info("session: " + session.getId() + " has been removed!!login name is :" + loginName);
				sessionMap.remove(session);
				session.invalidate();
				break;
			}
		}
	}

	/* 
	* replaceUserSession-用户已经登录则进行session剔除,否则建立新的session 
	* @param loginName String-登录的用户名称 
	*/
	public static void replaceUserSession(HttpSession session, String loginName) throws Exception {
		if (sessionMap.containsValue(loginName)) {// 如果该用户已经登录过，则使上次登录的用户掉线(依据使用户名是否在sessionMap中)
			// 删除原用户名对应的session(即删除原来的session和loginName)
			removeUserSession(loginName);
			createUserSession(session, loginName);// 添加现在的session和loginName
		} else {// 如果该用户没登录过，直接添加现在的session和loginName
			createUserSession(session, loginName);
		}
	}

}
