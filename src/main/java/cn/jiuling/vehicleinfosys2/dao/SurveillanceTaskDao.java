package cn.jiuling.vehicleinfosys2.dao;

import java.sql.Timestamp;
import java.util.List;

import cn.jiuling.vehicleinfosys2.model.SurveillanceTask;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface SurveillanceTaskDao extends BaseDao<SurveillanceTask> {

	void delete(SurveillanceTask s, Boolean isAllDelete);

	void stopSurveillanceTask(Integer taskId);

	List findByPlate(String plates, Timestamp timestamp);

	List findSurveillancingPlate();

	/**
	 * 查找布控中的任务
	 * @return list
	 */
	List findSurveillancingTask();

	List findByTask(List list,Timestamp timestamp);
	
	/**
	 * 更新任务状态
	 * @param taskId
	 * @param status
	 */
	public void updateSurveillanceTaskStatus(Integer taskId,Integer status);
	
	/**
	  * （布控报警页面）布控任务查询查询
	  * @param surveillanceTask
	  * @param page
	  * @param rows
	  * @return
	  */
	 public Pager queryPracticeTaskList(SurveillanceTask surveillanceTask, Integer page, Integer rows);

}
