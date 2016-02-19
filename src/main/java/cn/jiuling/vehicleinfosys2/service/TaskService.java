package cn.jiuling.vehicleinfosys2.service;

import java.sql.Timestamp;

import cn.jiuling.vehicleinfosys2.vo.TaskObj;
import org.springframework.web.multipart.MultipartFile;

import cn.jiuling.vehicleinfosys2.model.Task;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.TaskQuery;

public interface TaskService {
	public void delete(Integer[] taskIds);

	public Pager list(Task task, Integer page, Integer rows);

	/**
	 * 查询最后一次实时任务
	 * 
	 * @param cameraId
	 * @return 任务对象
	 */
	public Task findLastRealTimeTask(Integer cameraId);
	/**
	 * 查询最后一次实时图片任务
	 *
	 * @param cameraId
	 * @return 任务对象
	 */
	public Task findLastRealImageTask(Integer cameraId);

	/**
	 * 开启实时任务 一个监控点同时只能开启一个实时流任务
	 * 
	 *
	 * @param task@return taskId 将开启的任务id返回
	 */
	public Integer startRealTimeTask(TaskObj task);

	/**
	 * 开启实时图片任务 一个监控点同时只能开启一个实时图片任务
	 *
	 *
	 * @param task@return taskId 将开启的任务id返回
	 */
	public void startRealImageTask(Integer[] ids, TaskObj task);

	/**
	 * 停止实时任务
	 * 
	 * @param taskId
	 *            任务id
	 * @return 若任务早已停止，则返回该任务的停止时间，否则成功，返回空
	 */
	public Timestamp stopRealTimeTask(Integer taskId);

	/**
	 * 开启离线任务
	 *
	 * @param followArea
	 *            感兴趣区域
	 * @param resourceId
 *            离线数据源id
	 * @param taskObj
	 */
	public void startOffLineTask(Integer[] resourceId, TaskObj taskObj);

	/**
	 * 任务高级查询
	 * 
	 * @param taskQuery
	 *            查询条件封装
	 * @param page
	 *            页数
	 * @param rows
	 *            每页数据数
	 * @return
	 */
	public Pager query(TaskQuery taskQuery, Integer page, Integer rows);

	/**
	 * 添加批量任务.
	 * 
	 * 上传一堆文件,为这些文件建一个目录,然后提交一个任务
	 * 
	 * @param files
	 * @param cId
	 * @param dataType
	 * @param type
	 * @param parentId
	 * @return
	 */
	public Object addBatTask(MultipartFile[] files, Integer cId, Short dataType, String type, Integer parentId);
}
