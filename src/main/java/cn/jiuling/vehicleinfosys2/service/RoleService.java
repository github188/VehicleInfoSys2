package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.Role;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.Tree;

import java.util.List;

public interface RoleService {
	public Pager list(Object c, Integer page, Integer rows);

	public Role add(Role r);

	public void del(Integer id);

	public void update(Role r);
	
	public List<Tree> findRolePrivilegesTree(Integer id);

    public void delete(Integer roleid);
	
}
