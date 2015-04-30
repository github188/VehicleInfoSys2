package cn.jiuling.vehicleinfosys2.util;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class SessionUtils {
	private static final Logger log = Logger.getLogger(SessionUtils.class);

	/**
	 * 判断session是否是失效的
	 * 
	 * @param session
	 * @return
	 */
	public static Boolean isInvalid(HttpSession session) {
		try {
			Long now = new Date().getTime();
			Long lastAccessedTime = session.getLastAccessedTime();
			Long maxLiveTime = new Long(session.getMaxInactiveInterval() * 1000);
			log.info("当前时间(毫秒)：" + now);
			log.info("上次访问时间(毫秒):" + lastAccessedTime);
			log.info("最大超时时间(毫秒):" + maxLiveTime);
			log.info("超时时间(毫秒)：" + (now - lastAccessedTime));
			if (now - lastAccessedTime >= maxLiveTime) {
				return true;
			}
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	/**
	 * 判断session是否是有效的
	 * 
	 * @param session
	 * @return
	 */
	public static Boolean isValid(HttpSession session) {
		return !isInvalid(session);
	}
}
