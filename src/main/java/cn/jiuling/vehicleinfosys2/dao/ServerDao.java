package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.Serverinfo;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface ServerDao extends BaseDao<Serverinfo> {
	/**
	 * 分页排序查询
	 * 
	 * @param page
	 * @param rows
	 * @param sortName
	 * @param order
	 * @return
	 */
	public Pager list(Integer page, Integer rows, String sortName, String order);
}
