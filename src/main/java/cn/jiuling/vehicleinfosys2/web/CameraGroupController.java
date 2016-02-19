package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.model.CameraGroup;
import cn.jiuling.vehicleinfosys2.service.CameraGroupService;
import cn.jiuling.vehicleinfosys2.util.JsonUtils;
import cn.jiuling.vehicleinfosys2.util.UploadStatusCache;
import cn.jiuling.vehicleinfosys2.vo.IpVo;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 对外的接口
 * 
 * @author phq
 * 
 * @date 2015-04-02
 */
@Controller
@RequestMapping(value = "cameraGroup")
public class CameraGroupController extends BaseController {
	private static Logger logger = Logger.getLogger(CameraGroupController.class);

    @Resource
    private CameraGroupService cameraGroupService;

    @RequestMapping("index.action")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("cameraGroup/index");
        return mav;
    }

    @RequestMapping(value = "groupSave.action")
    @ResponseBody
    public String groupSave(CameraGroup cameraGroup) {
        cameraGroupService.save(cameraGroup);
        return "success";
    }

    @RequestMapping(value = "findCameraGroupList.action")
    @ResponseBody
    public Object getCameraGroupPages(CameraGroup cameraGroup,
                               @RequestParam(required = false, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "10") Integer rows) {
        Pager p = cameraGroupService.list(cameraGroup, page, rows);
        return p;
    }
    @RequestMapping(value = "findAllCameraGroup.action")
    @ResponseBody
    public Object getAllCameraGroup(CameraGroup cameraGroup) {
        Pager p = cameraGroupService.list(cameraGroup, null, null);
        return p;
    }
    @RequestMapping(value = "findByGroupId.action")
    @ResponseBody
    public CameraGroup findByGroupId(CameraGroup cameraGroup,
                               @RequestParam(required = false, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "10") Integer rows) {
        return cameraGroupService.findByGroupId(cameraGroup.getId());
    }
}
