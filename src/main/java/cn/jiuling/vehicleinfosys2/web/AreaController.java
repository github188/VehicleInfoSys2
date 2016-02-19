package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.annotation.Auditable;
import cn.jiuling.vehicleinfosys2.model.Area;
import cn.jiuling.vehicleinfosys2.service.AreaService;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("area")
public class AreaController {
	private Logger log = Logger.getLogger(AreaController.class);

	@Resource
	private AreaService areaService;

	@RequestMapping(value = "area.action")
	public void index() {
	}

	@RequestMapping(value = "findAreaList.action")
	@ResponseBody
	public Object getAreaPages(Area area,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows) {
		Pager p = areaService.findAreaChildren(area, page, rows);
		return p;
	}

	@RequestMapping(value = "areaAdd.action")
	public void addArea(Area area) {
	}

    @Auditable(remark = "添加行政区划",operType = Constant.USER_OPRE_TYPE_ADD)
	@RequestMapping(value = "areaSave.action")
	@ResponseBody
	public String areaSave(Area area) {
		areaService.save(area);
		return "success";
	}

	@RequestMapping(value = "areaDetail.action")
	public Object areaDetail(Integer id) {
		ModelAndView mav = new ModelAndView("area/areaDetail");
		Area area = areaService.findAreaById(id);
		mav.addObject("areaObj", area);
		return mav;
	}

	@RequestMapping(value = "getAreaById.action")
	@ResponseBody
	public Object areaEdit(Integer id) {
		Area area = areaService.findAreaById(id);
		if(area.getParentId() == 0) area.setParentId(null);
		return area;
	}

    @Auditable(remark = "修改行政区划",operType = Constant.USER_OPRE_TYPE_UPDATE)
	@RequestMapping(value = "saveUpdateArea.action")
	@ResponseBody
	public Object saveUpdateArea(Area area) {
		areaService.updateArea(area);
		return "success";
	}

	@RequestMapping(value = "enableOrDisable.action")
	@ResponseBody
	public Object isEnableArea(Integer id, Short enableOrDisable) {
		return areaService.enableOrDisable(id, enableOrDisable);
	}

	@RequestMapping(value = "areaTreeJson.action")
	@ResponseBody
	public String treeJson() {
		List<String> lstTree = new ArrayList<String>();
		List<Area> areaList = new ArrayList<Area>();
		areaList = areaService.getAll();
		for (Area dto : areaList) {
			lstTree.add("{\"id\":" + dto.getId() + ",\"pId\":" + dto.getParentId() + ",\"name\":" + "\"" + dto.getName() + "\"" + ",\"open\":true}");
		}
		return lstTree.toString();
	}

	/*
	[{
	    "id":1,
	    "text":"1111"
	}] 
	*/
	@RequestMapping(value = "areaJson.action")
	@ResponseBody
	public String areaJson(Short isEnable) {
		List<Area> areaList = areaService.getAll();
		List<String> areaJson = new ArrayList<String>();
		for (Area area : areaList) {
			if(area.getIsEnable().equals(isEnable)){
				areaJson.add("{\"id\":" + area.getId() + ",\"pId\":" + area.getParentId() + ",\"name\":" + "\"" + area.getName() + "\"}");
			}
		}
		return areaJson.toString();
	}
	
	@RequestMapping(value = "areaTree.action")
	@ResponseBody
	public Object areaTree(Integer id) {
		return areaService.areaTree(id);
	}

}
