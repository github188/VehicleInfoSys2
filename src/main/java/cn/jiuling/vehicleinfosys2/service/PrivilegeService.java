package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.Actionprivilege;
import cn.jiuling.vehicleinfosys2.vo.RolePrivileges;
import cn.jiuling.vehicleinfosys2.vo.UserPrivileges;

import java.util.List;
import java.util.Map;

public interface PrivilegeService {
	/**
	 * 取得系统所有的操作权限
	 * 
	 * @return map key是url,value是权限对象
	 */
	public Map getAll();

	/**
	 * 取得用户操作权限
	 * 
	 * @return map key是url,value是权限对象
	 */
	public Map getUserPrivilegeMap(Integer integer);
	
	public UserPrivileges findUserPrivilege(Integer userId);
	
	public RolePrivileges findRolePrivilege(Integer roleId);

	/**
	 * 更新用户权限
	 * 
	 * @param userId
	 * @param ids
	 */
	public void updateUserPrivilege(Integer userId, Integer[] privilegeIds);
	
	/**
	 * 更新角色权限
	 * 
	 * @param userId
	 * @param ids
	 */
	public void updateRolePrivilege(Integer roleId, Integer[] privilegeIds);

	public void updateUserRoles(Integer userId, Integer[] ids);

	/**
	 * 通过角色id获取对应的权限actionName
	 * @param roleIds
	 * @return
     */
	public List<Actionprivilege> getRoleActionprivilegeList(Integer []roleIds);
}
