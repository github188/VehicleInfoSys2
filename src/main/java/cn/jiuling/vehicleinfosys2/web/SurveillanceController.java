package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.annotation.Auditable;
import cn.jiuling.vehicleinfosys2.model.SurveillanceApplicationRecord;
import cn.jiuling.vehicleinfosys2.model.SurveillanceTask;
import cn.jiuling.vehicleinfosys2.service.SurveillanceService;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * 布控管理
 * 
 * @author phq
 * 
 * @date 2015年5月26日
 */
@Controller
@RequestMapping(value = "surveillance")
public class SurveillanceController extends BaseController {
	
	@Resource
	private SurveillanceService surveillanceService;

	@RequestMapping("index.action")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("surveillance/surveillanceIndex");
        String resource = "serverIp.properties";
		mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
		return mav;
	}
	
	@RequestMapping("list.action")
	@ResponseBody
	public Pager list(SurveillanceTask surveillanceTask,
					@RequestParam(defaultValue = "1") Integer page,
					@RequestParam(defaultValue = "10") Integer rows) {
		return surveillanceService.list(surveillanceTask, page, rows);
	}
	
	@RequestMapping("query.action")
	@ResponseBody
	public Pager query(ResultQuery rq,
				@RequestParam(defaultValue = "1") Integer page,
				@RequestParam(defaultValue = "10") Integer rows) {
		return surveillanceService.query(rq, page, rows);
		
	}

    @Auditable(remark = "添加布控任务",operType = Constant.USER_OPRE_TYPE_ADD)
	@RequestMapping("add.action")
	@ResponseBody
	public Object add(SurveillanceTask surveillanceTask) {
		/*Pager pager = surveillanceService.querySurveillanceTask(surveillanceTask);
		if (pager.getTotal() > 0) {
		return "surveillancing";
		}*/
		
		surveillanceService.add(surveillanceTask);
		return surveillanceTask;
	}

    @Auditable(remark = "修改布控任务",operType = Constant.USER_OPRE_TYPE_UPDATE)
	@RequestMapping("update.action")
	@ResponseBody
	public Object update(SurveillanceTask surveillanceTask) {		
		surveillanceService.update(surveillanceTask);
		return "success";
	}
	
	/**
	 * @Title: remove
	 * @Description: 删除资源，并判断是否连同资源下的任务和结果一起删除
	 * @param ids
	 *            所要删除的资源id
	 * @param isAllDelete
	 *            是否连同资源下的任务和结果一起删除
	 * @ReturnType: Object
	 */
    @Auditable(remark = "删除布控任务",operType = Constant.USER_OPRE_TYPE_DEL)
	@RequestMapping(value = "remove.action")
	@ResponseBody
	public Object remove(Integer[] ids, Boolean isAllDelete) {
		surveillanceService.delete(ids, isAllDelete);
		return SUCCESS;
	}
	
	@RequestMapping(value="stop.action")
	@ResponseBody
	public Object stop(Integer taskId) {
		return surveillanceService.stopSurveillanceTask(taskId);
	}
	
	/**
	 * 检查布控中任务的结果
	 */
	@RequestMapping(value="checkResult.action")
	@ResponseBody
	public Object checkResult(Long interval) {
		List list = null;
		List list1 = surveillanceService.findSurveillancingTask();
		if(null != list1){
			list = surveillanceService.checkResultByTask(list1, interval);
		}
		return list;
	}
	
	/**
	 * 插入布控记录信息
	 * @param surveillanceTask
	 * @return
	 */
	@RequestMapping("addSurveillanceApplicationRecord.action")
	@ResponseBody
	public Object add(SurveillanceApplicationRecord surveillanceApplicationRecord) {		
		surveillanceService.addSurveillanceApplicationRecord(surveillanceApplicationRecord);
		return "success";
	}
	
	/**
	 * 更改布控任务状态
	 * @param surveillanceTask
	 * @return
	 */
	@RequestMapping("updateSurveillanceTaskStatus.action")
	@ResponseBody
	 public Object updateSurveillanceTaskStatus(Integer taskId,Integer status) {		
		surveillanceService.updateSurveillanceTaskStatus(taskId, status);
		return "success";
	}
	
	/**
	 * 查询布控流程信息
	 * @param exampleEntity
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("querySurveillanceApplicationRecord.action")
	@ResponseBody
	public Pager querySurveillanceApplicationRecord(SurveillanceApplicationRecord exampleEntity,Integer page, Integer rows){
		Pager pager=surveillanceService.querySurveillanceApplicationRecord(exampleEntity, page, rows);
		return pager;
	}
	
	/**
	 * （布控报警页面）布控任务查询查询
	 * @param surveillanceTask
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("queryPracticeTaskList.action")
	@ResponseBody
	public Pager queryPracticeTaskList(SurveillanceTask surveillanceTask,
					@RequestParam(defaultValue = "1") Integer page,
					@RequestParam(defaultValue = "10") Integer rows) {
		return surveillanceService.queryPracticeTaskList(surveillanceTask, page, rows);
	}
	
	/**
     * 更新布控时间
     */
	@RequestMapping("updateDoTime.action")
	@ResponseBody
    public void updateDoTime(Integer taskId){
    	long now = System.currentTimeMillis();
    	Timestamp timestamp = new Timestamp(now);
    	surveillanceService.updateDoTime(taskId, timestamp);
    }
    
    /**
     * 更新停止布控时间
     */
	@RequestMapping("updateEndTime.action")
	@ResponseBody
    public void updateEndTime(Integer taskId){
		long now = System.currentTimeMillis();
    	Timestamp timestamp = new Timestamp(now);
    	surveillanceService.updateEndTime(taskId, timestamp);
	}
	
	/**
	 * 校验任务名
	 */
	@RequestMapping("valideName.action")
	@ResponseBody
	public Object valideName(String taskName){
		return surveillanceService.valideName(taskName);
	}
}
