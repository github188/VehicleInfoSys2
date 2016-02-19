package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.annotation.Auditable;
import cn.jiuling.vehicleinfosys2.model.Area;
import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.model.Task;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.service.*;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.util.JsonUtils;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.CameraVO;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.Tree;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private CameraGroupService cameraGroupService;
	@Resource
	private TaskService taskService;
	@Resource
	private ResourceService resourceService;
	@Resource
	private AreaService areaService;

	/**
	 * @Title: indexCameraPage
	 * @Description: 处理摄像头页面请求信息
	 * @ReturnType: void
	 */
	@RequestMapping("camera.action")
	public Object indexCameraPage(Integer id) {
		ModelAndView mav = new ModelAndView("camera/camera");
        String resource = "serverIp.properties";
		mav.addObject("camera", cameraService.fingById(id));
        mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
		Task t = taskService.findLastRealTimeTask(id);
		Task realImageTask = taskService.findLastRealImageTask(id);
		if(null != t) {
			mav.addObject("taskId", t.getId());
		} else if(null != realImageTask) {
			mav.addObject("realImageTaskId", realImageTask.getId());
		}
		return mav;
	}

	@RequestMapping("manage.action")
	public Object manage(Integer pageType) {
		ModelAndView mav = new ModelAndView("camera/manage");
        mav.addObject("pageType",pageType);
		return mav;
	}

	@RequestMapping("getBigUrl.action")
	@ResponseBody
	public Object getBigUrl(Integer id) {
		return resourceService.findBigPicFromTheLast(id);
	}

	@RequestMapping("list.action")
	@ResponseBody
	public Object list(Camera c,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows) {
        Pager p = cameraService.list(c, page, rows);
        CameraVO cameraVO = new CameraVO();
        cameraVO.setP(p);
        cameraVO.setAreaTree(areaService.getAreaTree(null));
		return cameraVO;
	}

    @Auditable(remark = "添加监控点",operType = Constant.USER_OPRE_TYPE_ADD)
	@RequestMapping("add.action")
	@ResponseBody
	public Object add(Camera c) {

        cameraService.add(c);

        CameraVO cameraVO = cameraGroupService.responseCamVo(c);

        return cameraVO;
	}

	@RequestMapping("update.action")
	@ResponseBody
	public Object update(Integer id) {
        ModelAndView mav = new ModelAndView("camera/updateCamera");
        Camera c = cameraService.fingById(id);
        CameraVO cameraVO = cameraGroupService.responseCamVo(c);
        mav.addObject("cameraVO",cameraVO);
        mav.addObject("jsonObj", JsonUtils.toJson(cameraVO));
        return mav;
	}

    @Auditable(remark = "修改监控点",operType = Constant.USER_OPRE_TYPE_UPDATE)
    @RequestMapping(value = "saveUpdatedCamera.action")
    @ResponseBody
    public Object saveUpdatedCamera(Camera c, HttpSession session) {
        cameraService.updateCamera(c);
        return SUCCESS;
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

    @Auditable(remark = "删除监控点",operType = Constant.USER_OPRE_TYPE_DEL)
	@RequestMapping(value = "remove.action")
	@ResponseBody
	public String remove(Camera c) {
		cameraService.remove(c);
		return SUCCESS;
	}

    @Auditable(remark = "批量导入监控点",operType = Constant.USER_OPRE_TYPE_ADD)
	@RequestMapping(value = "import.action")
	@ResponseBody
	public String importCamera(MultipartFile cameraInfo) {
		cameraService.saveCameras(cameraInfo);
		return SUCCESS;
	}

	@RequestMapping(value = "camLstTree.action")
	@ResponseBody
	public Object camLstTree() {
        return cameraService.camLstTree();
	}

}
