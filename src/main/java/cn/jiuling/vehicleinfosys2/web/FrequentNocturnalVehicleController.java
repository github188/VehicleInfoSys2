package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.service.FrequentNocturnalVehicleService;
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
@RequestMapping(value = "frequentNocturnalVehicle")
public class FrequentNocturnalVehicleController extends BaseController {

    @Resource
    private FrequentNocturnalVehicleService frequentNocturnalVehicleService;

    @RequestMapping("index.action")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("frequentNocturnalVehicle/index");
        String resource = "serverIp.properties";
        mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
        return mav;
    }

    @RequestMapping("queryNocturnalVehicle.action")
    @ResponseBody
    public List query(AerialMammalVehicleQuery aq) {
        return frequentNocturnalVehicleService.query(aq);
    }
}
