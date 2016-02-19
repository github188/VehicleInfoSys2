package cn.jiuling.vehicleinfosys2.util;

public class Constant {
	/**
	 * 线索类型:图片
	 */
	public static final int CLUE_TYPE_PIC = 1;
	/**
	 * 线索类型:视频
	 */
	public static final int CLUE_TYPE_VIDEO = 2;
	/**
	 * 任务类型:实时任务
	 */
	public static final Short TASK_TYPE_REALTIME = 1;
	/**
	 * 任务类型:离线任务
	 */
	public static final Short TASK_TYPE_OFFLINE = 2;
	/**
	 * 任务类型:批量任务
	 */
	public static final Short TASK_TYPE_BAT = 3;

    /**
     * 每月毫秒数
     */
    public static final Long MILLISECONDS_PER_MONTH = (long)30*24*60*60*1000;

    /**
     * 时间分隔符
     */
    public static final String TIME_SEPARATOR = ":";

    /**
     * 定时器开关
     */
    public static final String TIMER_POWER_ON = "on";
    public static final String TIMER_POWER_OFF = "off";

    /**
     * 是否要对视频进行转码
     */
    public static final String IS_TRANSCODE_TRUE = "true";
    public static final String IS_TRANSCODE_FALSE = "false";

    /**
     * 转码任务状态
     */
    public static final int TRANSTASKMGR_STATUS_INCOMPLETE = 0;
    public static final int TRANSTASKMGR_STATUS_COMPLETED = 1;
    public static final int TRANSTASKMGR_STATUS_IN_PROGRESS = 2;
    public static final int TRANSTASKMGR_STATUS_OVER_FOR_FAIL = 3;
    public static final int TRANSTASKMGR_STATUS_DEL = 4;
    public static final int TRANSTASKMGR_STATUS_WAIT_FOR_RETRY = 5;
    public static final int TRANSTASKMGR_STATUS_RETRYING = 6;

    /**
     * 是否已添加到datasource表
     */
    public static final int ISADDEDTODATASOURCE_NO = 0;
    public static final int ISADDEDTODATASOURCE_YES = 1;
    /**
     * 用记操作类型
     * 登录、登出、查询、修改、添加、删除、审核
     */

    public static final String USER_OPRE_TYPE_LOGIN = "登录";
    public static final String USER_OPRE_TYPE_LOGOUT = "登出";
    public static final String USER_OPRE_TYPE_LIST = "查询";
    public static final String USER_OPRE_TYPE_UPDATE = "修改";
    public static final String USER_OPRE_TYPE_ADD = "添加";
    public static final String USER_OPRE_TYPE_DEL = "删除";
    public static final String USER_OPRE_TYPE_AUDIT = "审核";

    /**
     * 操作对象
     */

    public static final String OPRE_OBJ_USER = "用户";
    public static final String OPRE_OBJ_CAMERA = "监控点";
    public static final String OPRE_OBJ_VLPRTASK = "识别任务";
    public static final String OPRE_OBJ_RES = "资源";

    public static final String OPRE_RESULT_SUCCESS = "操作成功";
    public static final String OPRE_RESULT_FAIL = "操作失败";

}
