package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprTaskFailLog;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface VlprTaskFailLogDao extends BaseDao<VlprTaskFailLog> {
	/**
	 * 分页查找异常日志
	 * 
	 * @param id
	 * @param page
	 * @param size
	 * @return Pager
	 */

	public Pager getFailLogPager(Integer id, int page, int size);

}
