package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.service.ResultService;
import cn.jiuling.vehicleinfosys2.util.JsonUtils;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;
import cn.jiuling.vehicleinfosys2.vo.TravelTogetherVehicleQuery;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 对外的接口
 *
 * @author phq
 * @date 2015-04-02
 */
@Controller
@RequestMapping(value = "travelTogetherVehicle")
public class TravelTogetherVehicleController extends BaseController {
    private static Logger logger = Logger.getLogger(TravelTogetherVehicleController.class);

    @Resource
    private ResultService resultService;

    @RequestMapping("index.action")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("travelTogetherVehicle/index");
        String resource = "serverIp.properties";
        mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
        return mav;
    }

    /**
     * 查找同行车
     * @return
     */
    @RequestMapping("queryTogetherVehicle.action")
    @ResponseBody
    public Pager queryTogetherVehicle(String resultVos, Integer locations, Long timeInterval) {
        ResultVo[] rvs = JsonUtils.toObjArr(resultVos,ResultVo[].class);
        return resultService.queryByTravelTogetherVehicle(rvs, timeInterval,locations, null,null);
    }

    /**
     * 同行车详细列表
     * @param travelTogetherVehicleQuery
     * @return
     */
    @RequestMapping("queryTogetherVehicleDetail.action")
    @ResponseBody
    public Pager queryTogetherVehicleDetail(TravelTogetherVehicleQuery travelTogetherVehicleQuery) {
        return resultService.queryByTravelTogetherVehicle(travelTogetherVehicleQuery);
    }

    /**
     * 查找原车
     * @param rq
     * @return
     */
    @RequestMapping("queryOrginalPlate.action")
    @ResponseBody
    public Pager queryOrginalPlate(ResultQuery rq) {
        return resultService.queryMunityResult2(rq);
    }
}
