package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.model.Dept;
import cn.jiuling.vehicleinfosys2.service.DeptService;
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
@RequestMapping("dept")
public class DeptController extends BaseController {
	private Logger log = Logger.getLogger(DeptController.class);

	@Resource
	private DeptService deptService;

	@RequestMapping(value = "dept.action")
	public void index() {
	}

	@RequestMapping(value = "findDeptList.action")
	@ResponseBody
	public Object getDeptPages(Dept dept,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows) {
		Pager p = deptService.findDeptChildren(dept, page, rows);
		return p;
	}

	@RequestMapping(value = "deptAdd.action")
	public void addDept(Dept dept) {
	}

	@RequestMapping(value = "deptSave.action")
	@ResponseBody
	public String deptSave(Dept dept) {
		deptService.save(dept);
		return "success";
	}

	@RequestMapping(value = "deptDetail.action")
	public Object deptDetail(Integer id) {
		ModelAndView mav = new ModelAndView("dept/deptDetail");
		Dept dept = deptService.findDeptById(id);
		mav.addObject("deptVoObj", dept);
		return mav;
	}

	@RequestMapping(value = "getDeptById.action")
	@ResponseBody
	public Object getDeptById(Integer id) {
		Dept dept = deptService.findDeptById(id);
		if(dept.getParentId() == 0) dept.setParentId(null); 
		return dept;
	}

	@RequestMapping(value = "saveUpdateDept.action")
	@ResponseBody
	public String saveUpdateDept(Dept dept) {
		deptService.updateDept(dept);
		return "success";
	}

	@RequestMapping(value = "enableOrDisable.action")
	@ResponseBody
	public Object isEnableDept(Integer id, Short enableOrDisable) {
		deptService.enableOrDisable(id, enableOrDisable);
		return "success";
	}

	@RequestMapping(value = "deptTreeJson.action")
	@ResponseBody
	public String treeJson() {
		List<String> lstTree = new ArrayList<String>();
		List<Dept> deptList = new ArrayList<Dept>();
		deptList = deptService.getAll();
		for (Dept dto : deptList) {
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
	@RequestMapping(value = "deptJson.action")
	@ResponseBody
	public String deptJson(Short isEnable) {
		List<Dept> deptList = deptService.getAll();
		List<String> deptJson = new ArrayList<String>();
		for (Dept dept : deptList) {
			if (dept.getIsEnable().equals(isEnable)) {
				deptJson.add("{\"id\":" + dept.getId() + ",\"pId\":" + dept.getParentId() + ",\"name\":" + "\"" + dept.getName() + "\"}");
			}
		}
		return deptJson.toString();
	}

	@RequestMapping(value = "deptTree.action")
	@ResponseBody
	public Object deptTree(Integer id) {
		return deptService.deptTree(id);
	}

}
