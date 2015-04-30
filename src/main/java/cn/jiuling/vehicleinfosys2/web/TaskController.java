package cn.jiuling.vehicleinfosys2.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.jiuling.vehicleinfosys2.model.Task;
import cn.jiuling.vehicleinfosys2.service.TaskService;
import cn.jiuling.vehicleinfosys2.vo.FollowArea;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.TaskQuery;

@Controller
@RequestMapping("task")
public class TaskController extends BaseController {

	@Resource
	private TaskService taskService;

	@RequestMapping("index.action")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("task/index");
		return mav;
	}

	@RequestMapping("startOffLineTask.action")
	@ResponseBody
	public Object startOffLineTask(Integer[] id, FollowArea followArea) {
		taskService.startOffLineTask(id, followArea);
		return "success";
	}

	@RequestMapping("delete.action")
	@ResponseBody
	public Object delete(Integer[] ids) {
		taskService.delete(ids);
		return "success";
	}

	@RequestMapping("list.action")
	@ResponseBody
	public Pager list(Task task,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		return taskService.list(task, page, rows);
	}

	@RequestMapping("query.action")
	@ResponseBody
	public Pager query(TaskQuery taskQuery,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		return taskService.query(taskQuery, page, rows);
	}

	@RequestMapping("start.action")
	@ResponseBody
	public Object start(Integer id, FollowArea followArea) {
		return taskService.startRealTimeTask(id, followArea);
	}

	@RequestMapping("stop.action")
	@ResponseBody
	public Object stop(Integer taskId) {
		return taskService.stopRealTimeTask(taskId);
	}

	@RequestMapping(value = "bat.action")
	@ResponseBody
	public Object addFile(@RequestParam("files") MultipartFile[] files, Integer cId, Short dataType, String type, Integer parentId) throws Exception {
		if (files == null || files.length <= 0) {
			return "fail";
		}
		return taskService.addBatTask(files, cId, dataType, type, parentId);
	}
}
