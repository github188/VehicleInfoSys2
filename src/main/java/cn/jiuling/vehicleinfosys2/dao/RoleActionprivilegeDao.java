package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.RoleActionprivilege;

public interface RoleActionprivilegeDao extends BaseDao<RoleActionprivilege> {
	public void deleteByRoleId(Integer roleId);
}
