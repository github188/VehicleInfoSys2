package cn.jiuling.vehicleinfosys2.service;

import java.sql.Timestamp;
import java.util.List;

import cn.jiuling.vehicleinfosys2.model.SurveillanceApplicationRecord;
import cn.jiuling.vehicleinfosys2.model.SurveillanceTask;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;

public interface SurveillanceService {

	Pager query(ResultQuery rq, Integer page, Integer rows);

	Pager list(SurveillanceTask surveillanceTask, Integer page, Integer rows);

	void add(SurveillanceTask surveillanceTask);

	Pager querySurveillanceTask(SurveillanceTask surveillanceTask);
	
	/**
	 * @Title: delete
	 * @Description: 删除资源，并判断是否连同资源下的任务和结果一起删除
	 * @param ids
	 *            所要删除的资源id数组
	 * @param isAllDelete
	 *            是否要删除该资源的任务和结果 true则删除，false则不删除
	 * @ReturnType: void
	 */
	public void delete(Integer[] ids, Boolean isAllDelete);

	/**
	 * 停止布控任务
	 * @param taskId
	 * @return 若任务早已停止，则返回该任务的停止时间，否则成功，返回空
	 */
	Timestamp stopSurveillanceTask(Integer taskId);

	/**
	 * 检查布控中任务的结果
	 * @param plates
	 * @return
	 */
	List checkResult(String plates, Long interval);

	/**
	 * 检查布控中任务的结果
	 * @param list interval
	 * @return
	 */
	List checkResultByTask(List list, Long interval);

	/**
	 * 查找正在布控的车牌号
	 * @return
	 */
	String findPlates();

	/**
	 * 查找布控中的任务
	 */
	 List findSurveillancingTask();
	 
	 /**
	  * 插入审核记录信息
	  * @param surveillanceApplicationRecord
	  */
	 public void addSurveillanceApplicationRecord(SurveillanceApplicationRecord surveillanceApplicationRecord);
	 
	 /**
	  * 更新任务状态
	  * @param taskId
	  * @param status
	  */
	 public void updateSurveillanceTaskStatus(Integer taskId,Integer status);
	 
	 /**
	  * 更新布控任务
	  * @param surveillanceTask
	  */
	 public void update(SurveillanceTask surveillanceTask);
	 
	 /**
	  * 查询审批流程信息
	  * @param exampleEntity
	  * @param page
	  * @param rows
	  * @return
	  */
	 public Pager querySurveillanceApplicationRecord(SurveillanceApplicationRecord exampleEntity,Integer page, Integer rows);
	 
	 /**
	  * （布控报警页面）布控任务查询查询
	  * @param surveillanceTask
	  * @param page
	  * @param rows
	  * @return
	  */
	 public Pager queryPracticeTaskList(SurveillanceTask surveillanceTask, Integer page, Integer rows);
	 
	 /**
	  * 更新布控时间
	  */
	 public void updateDoTime(Integer taskId,Timestamp doTime);
	 
	/**
	 * 更新停止布控时间
	 */
	public void updateEndTime(Integer taskId,Timestamp doTime);
	
	/**
     * 根据任务id获取上次处理的位置
     */
    public long getPreviousPosition(Integer id);
    
    /**
     * 根据任务id更新或插入本次处理位置
     */
    public void saveOrUpdateProcessPosition(Integer id,Long serialNumber);
    
    /**
   	 * 校验任务名
   	 */
   	public boolean valideName(String taskName);
}
