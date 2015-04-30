package cn.jiuling.vehicleinfosys2.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.model.Task;
import cn.jiuling.vehicleinfosys2.service.CameraService;
import cn.jiuling.vehicleinfosys2.service.ResourceService;
import cn.jiuling.vehicleinfosys2.service.TaskService;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * 地图上的监控点controller
 * 
 * @author phq
 * 
 * @date 2014-12-9
 */
@Controller
@RequestMapping("camera")
public class CameraController extends BaseController {

	@Resource
	private CameraService cameraService;
	@Resource
	private TaskService taskService;
	@Resource
	private ResourceService resourceService;

	/**
	 * @Title: indexCameraPage
	 * @Description: 处理摄像头页面请求信息
	 * @ReturnType: void
	 */
	@RequestMapping("camera.action")
	public Object indexCameraPage(Integer id) {
		ModelAndView mav = new ModelAndView("camera/camera");
		mav.addObject("camera", cameraService.fingById(id));
		Task t = taskService.findLastRealTimeTask(id);
		if(null != t) {
			mav.addObject("taskId", t.getId());
		}
		return mav;
	}

	@RequestMapping("getBigUrl.action")
	@ResponseBody
	public Object getBigUrl(Integer id) {
		return resourceService.findBigPicFromTheLast(id);
	}
	
	@RequestMapping("list.action")
	@ResponseBody
	public Pager list(Camera c,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows) {
		Pager p = cameraService.list(c, page, rows);
		return p;
	}

	@RequestMapping("add.action")
	@ResponseBody
	public Object add(Camera c) {
		cameraService.add(c);
		return c;
	}

	@RequestMapping("update.action")
	@ResponseBody
	public Object update(Camera c) {
		cameraService.update(c);
		return c;
	}

	@RequestMapping("valideName.action")
	@ResponseBody
	public Object valideName(Integer id, String name) {
		if (null == id) {
			return cameraService.valideName(name);
		} else {
			return cameraService.valideName(id, name);
		}
	}

	@RequestMapping(value = "remove.action")
	@ResponseBody
	public String remove(Camera c) {
		cameraService.remove(c);
		return SUCCESS;
	}

}
