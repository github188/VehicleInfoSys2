package cn.jiuling.vehicleinfosys2.vo;

/**
 * 系统常量类
 * 
 * @author phq
 * 
 * @date 2014-12-13
 */
public class Constant {
	/**
	 * 实时任务
	 */
	public static final Short TASK_TYPE_ONLINE = 1;
	/**
	 * 离线任务
	 */
	public static final Short TASK_TYPE_OFFLINE = 2;
	/**
	 * 批量任务
	 */
	public static final Short TASK_TYPE_BAT = 3;
	/**
	 * 未完成
	 */
	public static final Short TASK_STATUS_UNFINISHED = 1;
	/**
	 * 已完成
	 */
	public static final Short TASK_STATUS_FINISH = 2;
	/**
	 * 等待处理
	 */
	public static final Short VLPRTASK_STATUS_WAIT = 0;
	/**
	 * 处理中
	 */
	public static final Short VLPRTASK_STATUS_PROCESSING = 1;
	/**
	 * 处理成功
	 */
	public static final Short VLPRTASK_STATUS_SUCCESS = 2;
	/**
	 * 处理失败
	 */
	public static final Short VLPRTASK_STATUS_FAIL = 3;
	/**
	 * 有效
	 */
	public static final Short VLPRTASK_FLAG_VALID = 1;
	/**
	 * 无效
	 */
	public static final Short VLPRTASK_FLAG_INVALID = 0;

	/**
	 * 数据源类型 :视频
	 */
	public static final Short DATASOURCE_TYPE_VIDEO = 2;
	/**
	 * 数据源类型 :图片
	 */
	public static final Short DATASOURCE_TYPE_PICTURE = 1;
	/**
	 * 数据源类型 :目录
	 */
	public static final Short DATASOURCE_TYPE_MENU = 3;

}
