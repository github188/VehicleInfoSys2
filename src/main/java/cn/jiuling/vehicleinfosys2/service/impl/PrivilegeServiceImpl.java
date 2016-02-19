package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.*;
import cn.jiuling.vehicleinfosys2.model.*;
import cn.jiuling.vehicleinfosys2.service.PrivilegeService;
import cn.jiuling.vehicleinfosys2.util.PrivilegeUtils;
import cn.jiuling.vehicleinfosys2.vo.RolePrivileges;
import cn.jiuling.vehicleinfosys2.vo.UserPrivileges;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service("privilegeService")
@SuppressWarnings("unchecked")
public class PrivilegeServiceImpl implements PrivilegeService {
	@Resource
	private ActionprivilegeDao actionprivilegeDao;
	@Resource
	private UserActionprivilegeDao userActionprivilegeDao;
	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private RoleDao roleDao;
	@Resource
	private RoleActionprivilegeDao roleActionprivilegeDao;
	@Resource
	private PrivilegeUtils privilegeUtils;

	private Logger log = Logger.getLogger(PrivilegeService.class);
	
	@Override
	public Map getAll() {
		Map m = new HashMap();
		List<Actionprivilege> list = actionprivilegeDao.getAll();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Actionprivilege actionprivilege = (Actionprivilege) iterator.next();
			m.put(actionprivilege.getCode(), actionprivilege);
		}
		return m;
	}

	@Override
	public Map getUserPrivilegeMap(Integer userId) {
		Map m = new HashMap();
		// 用户个人权限
		List<Actionprivilege> list = actionprivilegeDao.getUserActionprivilegeList(userId);
		// 用户角色权限
		List<Actionprivilege> list2 = actionprivilegeDao.getUserRoleActionprivilegeList(userId);
		list.addAll(list2);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Actionprivilege actionprivilege = (Actionprivilege) iterator.next();
			m.put(actionprivilege.getCode(), actionprivilege);
			log.info(actionprivilege.getResourceName()+":"+actionprivilege.getCode()+":"+actionprivilege.getActionName());
		}
		return m;
	}

	@Override
	public UserPrivileges findUserPrivilege(Integer userId) {
		List<Actionprivilege> list = actionprivilegeDao.getUserActionprivilegeList(userId);
		Map map = privilegeUtils.getAllActionPrivilegeMap();
		List<Role> roles = roleDao.getRole();
		List<Role> ownRoles = roleDao.getUserRoleList(userId);
		UserPrivileges u = new UserPrivileges();
		u.setUserPrivileges(list);
		u.setAllPrivileges(map.values());
		u.setRoles(roles);
		u.setOwnRoles(ownRoles);
		return u;
	}

	@Override
	public RolePrivileges findRolePrivilege(Integer roleId) {
		Map map = privilegeUtils.getAllActionPrivilegeMap();
		List<Actionprivilege> list = actionprivilegeDao.getRoleActionprivilegeList(roleId);
		RolePrivileges r = new RolePrivileges();
		r.setAllPrivileges(map.values());
		r.setRolePrivileges(list);
		return r;
	}
	
	@Override
	public void updateUserPrivilege(Integer userId, Integer[] privilegeIds) {
		userActionprivilegeDao.delActionprivilegeByUserId(userId);
		if (privilegeIds != null && privilegeIds.length != 0) {
			for (int i = 0; i < privilegeIds.length; i++) {
				Integer privilegeId = Integer.valueOf(privilegeIds[i]);
				UserActionprivilege u = new UserActionprivilege();
				u.setUserId(userId);
				u.setPrivilegeId(privilegeId);
				userActionprivilegeDao.save(u);
			}
		}
		privilegeUtils.updatePrivilegeFlag();
	}
	
	@Override
	public void updateRolePrivilege(Integer roleId, Integer [] privilegeIds) {
		roleActionprivilegeDao.deleteByRoleId(roleId);
		if(privilegeIds != null && privilegeIds.length != 0) {
			Integer [] ids = privilegeIds;
			for(int i = 0; i < ids.length; i++) {
				Integer privilegeId = ids[i];
				RoleActionprivilege r = new RoleActionprivilege();
				r.setRoleId(roleId);
				r.setPrivilegeId(privilegeId);
				roleActionprivilegeDao.save(r);
			}
			
		}
		privilegeUtils.updatePrivilegeFlag();
	}

	@Override
	public void updateUserRoles(Integer userId, Integer [] roleIds) {
		userRoleDao.delRoleByUserId(userId);
		if(roleIds != null && roleIds.length != 0) {
			for (int i = 0; i < roleIds.length; i++) {
				Integer roleId = Integer.valueOf(roleIds[i]);
				UserRole u = new UserRole(userId, roleId);
				userRoleDao.save(u);
			}
		}
		privilegeUtils.updatePrivilegeFlag();
	}

	@Override
	public List<Actionprivilege> getRoleActionprivilegeList(Integer []roleIds){
		return actionprivilegeDao.getRoleActionprivilegeList(roleIds);
	}
}
