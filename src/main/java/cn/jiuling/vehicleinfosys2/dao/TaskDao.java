package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.Task;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.TaskQuery;

public interface TaskDao extends BaseDao<Task> {
	/**
	 * 把监控点下的正在进行的识别中的所有任务给停了
	 * 
	 * @param cameraId
	 *            监控点id
	 */
	public void stopAllTask(Integer cameraId);

	/**
	 * @Title: 根据任务ID删除任务
	 * @Description: 根据taskid删除相应的任务
	 * @param taskId
	 * @ReturnType: void
	 */
	public void stopSingleTask(Long taskId);

	/**
	 * 任务高级查询
	 * 
	 * @param taskQuery
	 *            查询条件封装
	 * @param page
	 *            页数
	 * @param rows
	 *            每页数据数
	 * @return 分页对象
	 */
	public Pager query(TaskQuery taskQuery, Integer page, Integer rows);

}
