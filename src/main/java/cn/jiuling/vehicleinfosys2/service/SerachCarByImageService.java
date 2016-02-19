package cn.jiuling.vehicleinfosys2.service;

import org.springframework.web.multipart.MultipartFile;

import cn.jiuling.vehicleinfosys2.model.Datasource;
import cn.jiuling.vehicleinfosys2.model.VlprTask;
import cn.jiuling.vehicleinfosys2.model.VlprVfmTask;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.TaskObj;


/**
 * 以图搜车service
 * @author daixiaowei
 *
 */
public interface SerachCarByImageService {
	
	/**
	 * 保存上传的文件
	 * @param files 文件对象list
	 * @param cId  监控点id
	 * @param dataType 资源类型（数据库）
	 * @param type  资源类型
	 * @return Datasource
	 * @throws Exception
	 */
	public Datasource uploadImage(MultipartFile[] files, Integer cId, Short dataType, String type) throws Exception;
	
	/**
	 * 添加图片识别任务
	 * @param ids
	 * @param taskObj
	 * @return
	 */
	public Long startOffLineTask(Integer[] ids, TaskObj taskObj);
	
	/**
     * 获取识别结果信息
     * @return list
     */
    public Pager getRecognitionResult(Long taskId);
    
    /**
     * 保存以图搜车任务
     * @param vlprVfmTask
     */
    public Long saveVlprTask(Long vlprTaskId,VlprVfmTask vlprVfmTask,TaskObj taskObj);
    
    /**
     * 根据任务id，查询识别任务
     */
    public VlprTask getVlprTask(long vlprTaskId);
    
	/**
	 * 查找所有以图搜车结果与查找指定以图搜车任务的布控结果
	 */
	public Pager querylist(Integer taskID, Integer page, Integer rows);

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
	
	/**
	 * 查询以图搜车任务
	 * @param vlprVfmTask
	 * @param page
	 * @param rows
	 * @return Pager
	 */
	public Pager getVlprVfmTask(VlprVfmTask vlprVfmTask,Integer page, Integer rows);
	
	/**
     * 保存以图搜车任务(手动)
     * @param vlprVfmTask
     */
    public Long saveManualVlprVfmTask(Integer dsId,String plateFollowarea,String featurFollowarea,Integer width,Integer height,VlprVfmTask vlprVfmTask);
    
    /**
	 * 停止以图搜车任务
	 * @param taskId
	 * @return int
	 */
	public int stopVlprVfmTask(Long taskId);
}
