package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.UserActionprivilege;

public interface UserActionprivilegeDao extends BaseDao<UserActionprivilege> {

	public void delActionprivilegeByUserId(Integer userId);
}
