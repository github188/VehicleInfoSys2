package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.Role;

import java.util.List;

public interface RoleDao extends BaseDao<Role> {

	public List<Role> getUserRoleList(Integer userId);

    public List<Role> getRole();

    public void delete(Integer roleid);
}
