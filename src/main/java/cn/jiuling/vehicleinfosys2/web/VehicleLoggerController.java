package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.model.VehicleLogger;
import cn.jiuling.vehicleinfosys2.service.VehicleLoggerService;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.VehicleLoggerQuery;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Controller
@RequestMapping("logger")
public class VehicleLoggerController extends BaseController {
	private Logger log = Logger.getLogger(VehicleLoggerController.class);

	@Resource
	private VehicleLoggerService vehicleLoggerService;

    @RequestMapping("index.action")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("logger/index");
        return mav;
    }

    @RequestMapping("query.action")
    @ResponseBody
    public Pager query(VehicleLoggerQuery vehicleLoggerQuery,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer rows) {
        return vehicleLoggerService.query(vehicleLoggerQuery, page, rows);
    }

	@RequestMapping(value = "list.action")
    @ResponseBody
	public Pager list(VehicleLogger vehicleLogger,
                       @RequestParam(required = false, defaultValue = "1") Integer page,
                       @RequestParam(required = false, defaultValue = "10") Integer rows) {
        return vehicleLoggerService.list(vehicleLogger, page, rows);
	}
}
