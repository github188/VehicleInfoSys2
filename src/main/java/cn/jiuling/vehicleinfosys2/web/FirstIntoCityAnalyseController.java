package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.service.VlprFirstIntoCityResultService;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 对外的接口
 *
 */
@Controller
@RequestMapping(value = "firstIntoCityAnalyse")
public class FirstIntoCityAnalyseController extends BaseController {

    @Resource
    private VlprFirstIntoCityResultService vlprFirstIntoCityResultService;

    @RequestMapping("index.action")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("firstIntoCityAnalyse/index");
        String resource = "serverIp.properties";
        mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
        return mav;
    }

    @RequestMapping("query.action")
    @ResponseBody
    public Pager query(ResultQuery rq,
                              @RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer rows) {
        return vlprFirstIntoCityResultService.query(rq, page, rows);
    }
}
