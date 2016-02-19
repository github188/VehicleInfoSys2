package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprVfmResult;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface VlprVfmResultDao extends BaseDao<VlprVfmResult> {
	/**
	 * 查找所有以图搜车结果与查找指定以图搜车任务的布控结果
	 */
	public Pager querylist(Integer taskID, Integer page, Integer rows);
	
	/**
	 * 查找所有以图搜车结果与查找指定以图搜车任务的结果(同上，无分页)
	 */
	public Pager queryMunityList(final Integer taskID);
	
	/**
	 * 查找所有以图搜车结果与查找指定以图搜车任务的结果条目数
	 */
	public Integer queryMunityListCount(final Integer taskID);

	/**
	 * 查找所有以图搜车原始上传图片的识别结果  通过SerialNumber识别结果ID来查找
	 */
	public Pager originalquerylist(Integer taskID, Integer page, Integer rows);

	/**
	 * 查找所有以图搜车原始上传图片的识别结果  通过TaskID识别任务ID来查找
	 */
	public Pager originalquerylisttask(Integer taskID, Integer page, Integer rows);

	/**
	 * 查找所有以图搜车原始上传图片的识别结果  通过taskID以图搜车任务ID来查找
	 */
	public Pager originalquerylisttaskid(Integer taskID, Integer page, Integer rows);
}
