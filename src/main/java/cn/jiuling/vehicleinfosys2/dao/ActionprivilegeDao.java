package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.Actionprivilege;

import java.util.List;

public interface ActionprivilegeDao extends BaseDao<Actionprivilege> {
	/**
	 * 取得用户的个人配置的权限集合
	 * 
	 * @param userId
	 * @return
	 */
	public List<Actionprivilege> getUserActionprivilegeList(Integer userId);

	/**
	 * 取得用户的角色的权限list
	 * 
	 * @param userId
	 * @return
	 */
	public List<Actionprivilege> getUserRoleActionprivilegeList(Integer userId);

	/**
	 * 取得角色配置的权限集合
	 * 
	 * @param roleId
	 * @return
	 */
	public List<Actionprivilege> getRoleActionprivilegeList(Integer roleId);

	/**
	 * 取得角色配置的权限集合
	 *
	 * @param roleIds
	 * @return
	 */
	public List<Actionprivilege> getRoleActionprivilegeList(Integer []roleIds);
}
