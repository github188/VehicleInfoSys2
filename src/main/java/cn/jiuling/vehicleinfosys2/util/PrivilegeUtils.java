package cn.jiuling.vehicleinfosys2.util;

import cn.jiuling.vehicleinfosys2.model.Actionprivilege;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.service.PrivilegeService;
import cn.jiuling.vehicleinfosys2.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@SuppressWarnings("unchecked")
@Component
public class PrivilegeUtils {

	private static final Logger log = Logger.getLogger(PrivilegeUtils.class);

	@Resource
	private UserService userService;
	@Resource
	private PrivilegeService privilegeService;
	/**
	 * 记录权限的最后修改时间
	 */
	private long time = System.currentTimeMillis();
	/**
	 * 是否启用权限
	 */
	private boolean enablePrivilege = true;

	private Map actionPrivilegeMap;

	/**
	 * 取得系统所有的操作权限集合
	 * 
	 * @return 系统所有的操作权限集合
	 */
	public Map getAllActionPrivilegeMap() {
		if (null == actionPrivilegeMap) {
			actionPrivilegeMap = privilegeService.getAll();
		}
		return actionPrivilegeMap;
	}

	/**
	 * 根据key判断是否有权限.
	 * 
	 * @param key
	 *            操作权限的代码,一个字符串表示,系统中用struts中配置的(action名+方法名)表示,如
	 *            修改案件的代码是CaseAction_updateCase
	 * @return true,用户对此操作有权限;false,用户对此操作没权限
	 */
	public boolean hasPrivilege(User user, String key) {
		if (!isEnablePrivilege()) {
			return true;
		}
		if (null == user) {
			return false;
		}
		Map allActionPrivilege = getAllActionPrivilegeMap();
		// 如果系统中没有这个操作权限,就放行吧
		if (allActionPrivilege.get(key) == null) {
			log.warn("用户" + user.getLoginName() + ",访问" + key + ",系统没有设置权限,直接放行");
			return true;
		}
		Map userPrivilege = getActinPrivilegeList(user);
		// 如果用户所拥有的权限中存在这个key,则放行
		if (null != userPrivilege.get(key)) {
			log.info("用户" + user.getLoginName() + ",访问" + key + ",通过授权");
			return true;
		}
		// 默认不放行
		log.warn("用户" + user.getLoginName() + ",禁止访问" + key);
		return false;
	}

	/**
	 * 对外提供取权限列表的方法
	 * 
	 * @return
	 */
	public Map getActinPrivilegeList(User user) {
		checkPrivilege(user);
		return user.getActionPrivilegeMap();
	}

	private void checkPrivilege(User user) {
		if (isPrivilegeExpired(user)) {
			Integer userId = user.getId();
			Map actionPrivilegeMap = privilegeService.getUserPrivilegeMap(userId);
			User newUser = userService.findUser(userId);
			user.setActionPrivilegeMap(actionPrivilegeMap);
			user.setTime(time);
			// 获取用户是否过期
			user.setIsValid(newUser.getIsValid());
		}
	}

	/**
	 * 判断用户是否有效
	 * 
	 * @param user
	 * @return
	 */
	public boolean isValid(User user) {
		checkPrivilege(user);
		return user.getIsValid().intValue() == 1;
	}

	/**
	 * 更新全局的time.
	 * 
	 * 此方法在权限被修改时调用
	 * 
	 */
	public void updatePrivilegeFlag() {
		log.info("权限有更新！！");
		time = System.currentTimeMillis();
	}

	/**
	 * 判断权限是否有更新 .
	 * 
	 * 静态变量time记录了权限的修改时间,每个用户session都会有一个time的备份,
	 * 取权限时用这个time来判断是权限从session中取还是db中取
	 * 
	 * @return
	 */

	private boolean isPrivilegeExpired(User user) {
		if (user == null) {
			return true;
		}
		Long userTime = user.getTime();
		return null == userTime || userTime != time;
	}

	public boolean isEnablePrivilege() {
		return enablePrivilege;
	}

	public void setEnablePrivilege(boolean enablePrivilege) {
		this.enablePrivilege = enablePrivilege;
	}

	public Actionprivilege getActionprivilege(String code) {
		Map<String, Actionprivilege> map = getAllActionPrivilegeMap();
		return map.get(code);
	}

}
