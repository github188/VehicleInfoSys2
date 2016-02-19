package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprVfmTask;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * 以图搜车任务类dao
 * @author daixiaowei
 *
 */
public interface VlprVfmTaskDao extends BaseDao<VlprVfmTask>{
	
	/**
	 * 停止以图搜车任务
	 * @param taskId
	 * @return int
	 */
	public int stopVlprVfmTask(Long taskId);
	
	/**
	 * 查询以图搜车任务
	 * @param vlprVfmTask
	 * @param page
	 * @param rows
	 * @return
	 */
	public Pager getVlprVfmTask(VlprVfmTask vlprVfmTask,Integer page, Integer rows);
}
