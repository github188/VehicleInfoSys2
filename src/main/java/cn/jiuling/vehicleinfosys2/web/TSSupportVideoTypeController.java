package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.model.TSSupportVideoType;
import cn.jiuling.vehicleinfosys2.service.TSSupportVideoTypeService;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 对外的接口
 * 
 * @author phq
 * 
 * @date 2015-04-02
 */
@Controller
@RequestMapping(value = "tssupportVideoType")
public class TSSupportVideoTypeController extends BaseController {
	private static Logger logger = Logger.getLogger(TSSupportVideoTypeController.class);

    @Resource
    private TSSupportVideoTypeService tsSupportVideoTypeService;

    @RequestMapping("list.action")
    @ResponseBody
    public Object query(TSSupportVideoType tv) {
        return tsSupportVideoTypeService.list(tv);
    }
}
