package cn.jiuling.vehicleinfosys2.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.jiuling.vehicleinfosys2.service.ResultService;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;

@Controller
@RequestMapping("result")
public class ResultController extends BaseController {

	@Resource
	private ResultService resultServcie;

	/**
	 * @Title: indexResultsPage
	 * @Description: 返回结果页面
	 * @ReturnType: void
	 */
	@RequestMapping(value = "results.action")
	@ResponseBody
	public ModelAndView indexResultsPage(Integer taskId,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "20") Integer rows) {
		ModelAndView mav = new ModelAndView("result/results");
		mav.addObject("resultList", resultServcie.listByTaskId(taskId, page, rows));
		mav.addObject("taskId", taskId);
		mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost"));
		return mav;
	}

	/**
	 * @Title: findByTaskId
	 * @Description: 按Task的Id查找结果集合
	 * @param taskId
	 * @param page
	 * @param rows
	 * @return 返回结果集合
	 * @ReturnType: Object
	 */
	@RequestMapping(value = "findByTaskId.action")
	@ResponseBody
	public Object findByTaskId(Integer taskId,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "20") Integer rows) {
		return resultServcie.listByTaskId(taskId, page, rows);
	}
	
	/**
	 * @Title: indexResultDetails
	 * @Description: 返回结果详情页面
	 * @return
	 * @ReturnType: ModelAndView
	 */
	@RequestMapping(value = "resultDetails.action")
	@ResponseBody
	public ModelAndView indexResultDetails(Long resultId) {
		ModelAndView mav = new ModelAndView("result/resultDetails");
		mav.addObject("result", resultServcie.findById(resultId));
		mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost"));
		return mav;
	}
	
	@RequestMapping(value = "findById.action")
	@ResponseBody
	public Object findById(Long resultId) {
		return resultServcie.findById(resultId);
	}

	@RequestMapping("list.action")
	@ResponseBody
	public Pager list(ResultQuery rq,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		return resultServcie.list(rq, page, rows);
	}

	@RequestMapping("index.action")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("result/index");
		mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost"));
		return mav;
	}

	@RequestMapping("query.action")
	@ResponseBody
	public Pager query(ResultQuery rq,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		return resultServcie.query(rq, page, rows);
	}
}
