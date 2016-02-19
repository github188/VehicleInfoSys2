package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.UserRole;

public interface UserRoleDao extends BaseDao<UserRole> {

	public void delRoleByUserId(Integer userId);
}
