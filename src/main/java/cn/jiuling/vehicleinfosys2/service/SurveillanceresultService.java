package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface SurveillanceresultService {
	/**
	 * 查找所有布控结果与查找指定布控任务的布控结果
	 */
	public Pager querylist(Integer surveillanceTaskId, Integer page, Integer rows);

}
