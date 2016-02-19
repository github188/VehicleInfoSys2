package cn.jiuling.vehicleinfosys2.dao;


import cn.jiuling.vehicleinfosys2.model.Area;
import cn.jiuling.vehicleinfosys2.vo.Pager;

import java.util.List;

public interface AreaDao extends BaseDao<Area> {
    List getAreaTreeEnable(Integer id);

    /**
	 * 更新孩子的parentName
	 * 
	 * @param id
	 * @param parentName
	 */
	public void updateChildren(Integer id, String parentName);

	public Pager findAreaChildren(Integer id, Integer page, Integer rows);

	public void disableChildren(Integer id);
	
	public List getAreaTree(Integer id);

    List getAncestor(String ancestorId);
}
