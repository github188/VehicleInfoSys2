package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprRecognum;

/**
 * 识别数Dao
 *
 * @author wangrb
 * @date 2015-12-1
 */
public interface RecognumDao extends BaseDao<VlprRecognum> {
	/**
	 * 检查是否超过设定的数量
	 * @return  是否通过（true:没有超过限量  false:超过限量）
	 */
	public boolean checkRecognum();
}
