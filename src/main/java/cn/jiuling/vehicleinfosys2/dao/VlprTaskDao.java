package cn.jiuling.vehicleinfosys2.dao;

import java.util.List;

import cn.jiuling.vehicleinfosys2.model.VlprTask;

public interface VlprTaskDao extends BaseDao<VlprTask> {
	/**
	 * 取得最近的在进行中的实时任务
	 * 
	 * @param cameraId
	 * @return
	 */

	public VlprTask findStartedRealTimeTask(Integer cameraId);

	/**
	 * 停止监控点下的任务,把flag置为无效
	 * 
	 * @param cameraId
	 */
	public void stopAllTask(Integer cameraId);

	/**
	 * 取得所有在进行中的实时任务
	 * 
	 * @param cameraId
	 * @return
	 */
	public List findStartedRealTimeTasks(Integer cameraId);

    /**
     * 取得最近的在进行中的实时图片任务
     *
     * @param cameraId
     * @return
     */
    VlprTask findStartedRealImageTask(Integer cameraId);
    /**
     * 取得所有在进行中的实时图片任务
     *
     * @param cameraId
     * @return
     */
    List findStartedRealImageTasks(Integer cameraId);
}
