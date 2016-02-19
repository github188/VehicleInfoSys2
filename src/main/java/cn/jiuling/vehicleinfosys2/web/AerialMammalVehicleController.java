package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.service.AerialMammalVehicleService;
import cn.jiuling.vehicleinfosys2.service.ResultService;
import cn.jiuling.vehicleinfosys2.service.VlprFirstIntoCityResultService;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.AerialMammalVehicleQuery;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 对外的接口
 *
 */
@Controller
@RequestMapping(value = "aerialMammalVehicle")
public class AerialMammalVehicleController extends BaseController {

    @Resource
    private AerialMammalVehicleService aerialMammalVehicleService;
    @Resource
    private ResultService resultService;

    @RequestMapping("index.action")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("aerialMammalVehicle/index");
        String resource = "serverIp.properties";
        mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
        return mav;
    }

    @RequestMapping("queryAerialMammalVehicle.action")
    @ResponseBody
    public List query(AerialMammalVehicleQuery aq) {
        return aerialMammalVehicleService.query(aq);
    }

    @RequestMapping("queryAerialMammalVehiclePassInfo.action")
    @ResponseBody
    public Pager queryAerialMammalVehiclePassInfo(ResultQuery rq,
                                                  @RequestParam(defaultValue = "1") Integer page,
                                                  @RequestParam(defaultValue = "10") Integer rows) {
        return resultService.query(rq, page, rows);
    }
}
