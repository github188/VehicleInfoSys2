package cn.jiuling.vehicleinfosys2.web;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.jiuling.vehicleinfosys2.service.FrequentlyPassVehicleService;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.FakeLicenseQuery;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * 频繁过车分析
 * 
 * @author daixiaowei
 * @date 2015-8-25
 */
@Controller
@RequestMapping("frequentlyPassVehicle")
public class FrequentlyPassVehicleController {
	//日志管理
	private Logger log = Logger.getLogger(FrequentlyPassVehicleController.class);
	
	@Resource
	private FrequentlyPassVehicleService frequentlyPassVehicleService;
	
	/**
	 * 装载frequentlyPassVehicle.jsp页面
	 */
	@RequestMapping("index.action")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("frequentlyPassVehicle/frequentlyPassVehicle");
        String resource = "serverIp.properties";
		view.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
		return view;
	}
	
	/**
	 * 过车列表信息查询
	 * 
	 * @param query
	 * @param page
	 * @param rows
	 * @return Pager
	 */
	@RequestMapping("queryPassVehicleList.action")
	@ResponseBody
	public List queryPassVehicleList(Timestamp startTime,Timestamp endTime,String cameraIds,Integer frequentlyRate) {
		
		List result = frequentlyPassVehicleService.queryPassVehicleList(startTime, endTime, cameraIds, frequentlyRate);
		return result;
	}
	
	/**
   	 * 查询监控点过车列表信息
   	 * @param startTime
   	 * @param endTime
   	 * @param cameraIds
   	 * @param license
   	 * @return List
   	 */
	 @RequestMapping("queryPassVehicleByCameraList.action")
	 @ResponseBody
     public Pager queryPassVehicleByCameraList(final Timestamp startTime,final Timestamp endTime,final String cameraIds,final String license,Integer page, Integer rows){
		 Pager result = frequentlyPassVehicleService.queryPassVehicleByCameraList(startTime, endTime, cameraIds, license,page,rows);
 		 return result;
     }
	 
	 /**
	  * 查询过车详情信息
	  * @param startTime
	  * @param endTime
	  * @param cameraId
	  * @param license
	  * @param page
	  * @param rows
	  * @return
	  */
	 @RequestMapping("queryPassVehicleByDatilList.action")
	 @ResponseBody
	 public Pager queryPassVehicleByDatilList(final Timestamp startTime,final Timestamp endTime,final String cameraId,final String license,Integer page, Integer rows){
		 Pager pager = frequentlyPassVehicleService.queryPassVehicleByDatilList(startTime, endTime, cameraId, license, page, rows);
		 return pager;
	 }
	 
	 /**
	  * 地图数据查询查询
	  * @param startTime
	  * @param endTime
	  * @param cameraId
	  * @param license
	  * @return Pager
	  */
	 @RequestMapping("queryMapDataList.action")
	 @ResponseBody
	 public Pager queryMapDataList( Timestamp startTime, Timestamp endTime, String cameraIds, String license){
		 Pager pager = frequentlyPassVehicleService.queryMapDataList(startTime, endTime, cameraIds, license);
		 return pager;
	 }
}
