package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.SurveillanceResult;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface SurveillanceresultDao extends BaseDao<SurveillanceResult> {
	/**
	 * 查找所有布控结果与查找指定布控任务的布控结果
	 */
	public Pager querylist(Integer surveillanceTaskId, Integer page, Integer rows);

	/**
	 * 删除指定布控任务的布控结果
	 */
	public void delete(Integer[] surveillanceTaskId);

}
