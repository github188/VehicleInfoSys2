package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprAppearance;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface VlprAppearanceDao extends BaseDao<VlprAppearance> {
	
	/**
	 * 查询位置信息
	 * 
	 * @return pager
	 */
	public Pager querAppearance();
	
	/**
	 * 更新线程读取数据库的位置信息
	 */
	public void updateThreadPositionAppearance(Long seriNumber);
	
	/**
	 * 更新页面读取数据库的信息
	 */
	public void updatePagePositionAppearance(Long id);
}
