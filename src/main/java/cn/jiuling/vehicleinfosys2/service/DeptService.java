package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.Dept;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.Tree;

import java.util.List;

public interface DeptService {
	public Pager findDeptChildren(Dept c, Integer page, Integer rows);

	public Dept findDeptById(Integer id);

	public void updateDept(Dept dept);

	public void save(Dept dept);

	public List<Dept> getAll();

	public Dept findByProperty(String name, Object value);

	public List<Tree> getDeptTree(Integer deptId);

	public Object deptTree(Integer id);

	public void enableOrDisable(Integer id, Short enableOrDisable);
}
