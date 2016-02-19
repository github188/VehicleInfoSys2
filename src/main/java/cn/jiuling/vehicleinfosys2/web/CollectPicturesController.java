package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.model.VlprCollectPictures;
import cn.jiuling.vehicleinfosys2.service.CollectPicturesService;
import cn.jiuling.vehicleinfosys2.vo.CollectPicturesVo;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 采集图片Controller
 *
 * @author wangrb
 * @date 2015-11-25
 */
@Controller
@RequestMapping("collectPictures")
public class CollectPicturesController extends BaseController {
    @Resource
    private CollectPicturesService collectPicturesService;

    @RequestMapping("index.action")
    public void index() {
    }

    @RequestMapping("list.action")
    @ResponseBody
    public Pager list(CollectPicturesVo cpv,
                      @RequestParam(defaultValue = "1") Integer page,
                      @RequestParam(defaultValue = "10") Integer rows) {
        return collectPicturesService.list(cpv, page, rows);
    }

    @RequestMapping(value = "save.action")
    @ResponseBody
    public String save(CollectPicturesVo vcpv) {
        collectPicturesService.save(vcpv);
        return SUCCESS;
    }

    @RequestMapping(value = "checkAdd.action")
    @ResponseBody
    public String checkAdd(String cameraNames) {
        boolean flag = collectPicturesService.check(cameraNames);
        if(flag){
            return SUCCESS;
        }else{
            return null;
        }
    }

    @RequestMapping("start.action")
    @ResponseBody
    public Object start(Long ids[]) {
        collectPicturesService.start(ids);
        return SUCCESS;
    }

    @RequestMapping("stopOrPause.action")
    @ResponseBody
    public Object stopOrPause(Long ids[],Integer oper) {
        collectPicturesService.stopOrPause(ids,oper);
        return SUCCESS;
    }

    @RequestMapping("delete.action")
    @ResponseBody
    public Object delete(Long ids[]) {
        collectPicturesService.delete(ids);
        return SUCCESS;
    }

}
