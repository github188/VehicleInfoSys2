package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.vo.ResultQuery;

/**
 * 
 * 下载service
 * @author daixiaowei
 *
 */
public interface DownloaderService {
	
	/**
	 * 计算下载的条目数
	 * @param rq
	 * @return Integer
	 */
	public Integer downloadItemCount(ResultQuery rq);
	
	/**
	 * 计算下载的条目数
	 * @param taskId 任务id
	 * @return Integer
	 */
	public Integer downloadItemCount(Integer taskId);
	
	/**
	 * 下载综合查询结果（全部）
	 * @return String 文件路径
	 */
	public String downloadTotalComprehensive(ResultQuery rq);
	
	/**
	 * 下载综合查询结果
	 * 
	 * @return String 文件路径
	 */
	public String downloadTotalComprehensive(Integer taskId);
	
	/**
	 * 下载综合查询结果(选择)
	 * 
	 * @return String 文件路径
	 */
	public String downloadTotalChoose(String ids);
	
	/**
	 * 计算下载的条目数(以图搜车)
	 * @param taskId 任务id
	 * @return Integer
	 */
	public Integer downloadScarItemCount(Integer taskVfmId);
	
	/**
	 * 下载搜车结果（以图搜车）
	 * 
	 * @return String 文件路径
	 */
	public String downloadScarTotal(Integer taskVfmId);
	
	
}
