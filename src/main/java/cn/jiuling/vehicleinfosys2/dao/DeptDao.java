package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.Dept;
import cn.jiuling.vehicleinfosys2.vo.Pager;

import java.util.List;

public interface DeptDao extends BaseDao<Dept> {
	/**
	 * 通过父id取子部门
	 * 
	 * @param id
	 * @return
	 */
	public List getDeptTree(Integer id);

	public List getDeptRootTree();

	/**
	 * 更新部门下面的所有子部门的parentName属性
	 * 
	 * @param parentId
	 *            父部门Id
	 * @param parentName
	 *            更新后的父部门名称
	 */
	public void updateChildDept(Integer parentId, String parentName);

	/**
	 * 通过父id取子部门列表,返回分页对象,包含父部门
	 * 
	 * @param deptId
	 * @param rows
	 *            当大于0时有效
	 * @param page
	 *            当大于0时有效
	 * @return 分页对象
	 */
	public Pager findDeptChildren(Integer deptId, Integer page, Integer rows);

	/**
	 * 停用给定的id单位下级所有单位
	 * 
	 * @param id
	 */
	public void disableChildren(Integer id);
}
