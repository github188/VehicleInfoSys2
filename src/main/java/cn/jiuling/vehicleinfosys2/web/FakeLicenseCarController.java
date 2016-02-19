package cn.jiuling.vehicleinfosys2.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.jiuling.vehicleinfosys2.dao.CameraGroupDao;
import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.model.CameraGroup;
import cn.jiuling.vehicleinfosys2.model.FakeLicenseCarInfo;
import cn.jiuling.vehicleinfosys2.model.VehicleRegistrationGovernment;
import cn.jiuling.vehicleinfosys2.model.VlprResult;
import cn.jiuling.vehicleinfosys2.service.CameraService;
import cn.jiuling.vehicleinfosys2.service.FakeLicenseService;
import cn.jiuling.vehicleinfosys2.service.ResultService;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.FakeLicenseQuery;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * 假套牌车管理
 * 
 * @author daixiaowei
 * @date 2015-07-21
 */
@Controller
@RequestMapping("fakeLicensedCar")
public class FakeLicenseCarController {
	
	//日志管理
	private Logger log = Logger.getLogger(FakeLicenseCarController.class);
	
	@Resource
	private FakeLicenseService fakeLicenseService;
	
	@Resource
	private CameraService cameraService;
	
	@Resource
	private ResultService resultService;
	
	@Resource
	private CameraGroupDao cameraGroupDao;
	
	/**
	 * 装载index.jsp页面
	 */
	@RequestMapping("index.action")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("fakeLicensedCar/index");
		return view;
	}
	
	/**
	 * 装载fakeLicenseedCarMonitor.jsp页面
	 */
	@RequestMapping("fakeLicenseedCarMonitor.action")
	public ModelAndView fakeLicenseedCarMonitor() {
		ModelAndView view = new ModelAndView("fakeLicensedCar/fakeLicenseedCarMonitor");
        String resource = "serverIp.properties";
		view.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
		return view;
	}
	
	/**
	 * 装载index.jsp页面
	 */
	@RequestMapping("fakeLicenseedCarSearch.action")
	public ModelAndView fakeLicenseedCarSearch() {
		ModelAndView view = new ModelAndView("fakeLicensedCar/fakeLicenseedCarSearch");
		return view;
	}
	
	/**
	 * 套牌车实时报警分页查询假套牌车辆信息
	 * 
	 * @param query
	 * @param page
	 * @param rows
	 * @return Pager
	 */
	@RequestMapping("realTimelist.action")
	@ResponseBody
	public Pager realTimelist(FakeLicenseQuery query,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows) {
		Pager p = fakeLicenseService.uploadRealTimelist(query, page, rows);
		return p;
	}
	
	/**
	 * 查询假套牌车辆信息(查询页面)
	 * 
	 * @param query
	 * @param page
	 * @param rows
	 * @return Pager
	 */
	@RequestMapping("querylist.action")
	@ResponseBody
	public Pager querylist(FakeLicenseQuery query,
			@RequestParam(required = false, defaultValue = "1") Integer page,
			@RequestParam(required = false, defaultValue = "10") Integer rows) {
		Pager p = fakeLicenseService.querylist(query, page, rows);
		return p;
	}
	
	/**
	 * 监控点树处理
	 * 
	 * @return Object
	 */
	@RequestMapping("cameraTreelist.action")
	@ResponseBody
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object treeList() {
		//城市名称列表
		List<Map<String,Object>> citys = new ArrayList<Map<String,Object>>();
		
		//查询监控点
		Pager pager = cameraService.list(new Camera(), null, null);
		List list = pager.getRows();
		
		/**
		 * 按监控点分组id进行分组
		 */
		Map<Integer,List> groupMap = new HashMap<Integer,List>();
		for(Object o:list){
			Camera camer = (Camera)o;
			if(groupMap.containsKey(camer.getCameraGroupId())){
				groupMap.get(camer.getCameraGroupId()).add(camer);
			}else{
				List cList = new ArrayList();
				cList.add(camer);
				groupMap.put(camer.getCameraGroupId(),cList);
			}
		}
		
		/**
		 * 遍历监控点分组map
		 */
		for(Entry<Integer, List> entry:groupMap.entrySet()){
			//监控点分组id
			Integer cameraGroupId = entry.getKey();
			//监控点list
			List cameraList = entry.getValue();
			
			//获取监控点分组名称
			String cameraGroupName = "";
			if(cameraGroupId == null){
				cameraGroupName = "无分组";
			}else{
				CameraGroup result = cameraGroupDao.findByProperty("id", cameraGroupId);
				if(result != null){
					cameraGroupName = result.getName();
				}								
			}
			
			if(cameraGroupName == null || "".equals(cameraGroupName.trim())){
				cameraGroupName = "无分组";
			}
						
			List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();

			for(Object o:cameraList){
				Camera camer = (Camera)o;	
				
				Map<String,Object> item = new HashMap<String,Object>();
				item.put("id", camer.getId());
				item.put("text", camer.getName());
				item.put("iconCls", "icon-tip");
				
				items.add(item);
			}
			
			Map<String,Object> cityItme = new HashMap<String,Object>();
			cityItme.put("id", cameraGroupId);
			cityItme.put("text", cameraGroupName);
			cityItme.put("children", items);
			citys.add(cityItme);		
		}
				
		return citys;
	}
	
	/**
	 * 根据serialNumber查询车辆识别结果
	 * 
	 * @param serialNumber
	 * @return Object
	 */
	@RequestMapping("vlprResultlist.action")
	@ResponseBody
	public Pager getVlprResultBySerialNumber(@RequestParam(required = true) Long serialNumber){
		
		VlprResult query = new VlprResult();
		query.setSerialNumber(serialNumber);
		
		Pager pager = resultService.list(query, null, null);
		
		return pager;
	}
	
	/**
	 * 根据车牌号查询车管所车辆信息
	 * 
	 * @param license
	 * @return Pager
	 */
	@RequestMapping("vehicleRegistrationGovernment.action")
	@ResponseBody
	public Pager getVehicleRegistrationGovernmentByLicense(@RequestParam(required = true) String license){
		
		VehicleRegistrationGovernment query = new VehicleRegistrationGovernment();
		query.setLicense(license);
		Pager pager = fakeLicenseService.queryVehicleRegistration(query, null, null);
		
		return pager;
	} 
	
	/**
	 * 根据id查询假套牌车辆信息
	 * 
	 * @param id
	 * @return Pager
	 */
	@RequestMapping("fakeLicenseCarInfoById.action")
	@ResponseBody
	public Pager getFakeLicenseCarInfoById(@RequestParam(required = true) Long id){
		
		FakeLicenseCarInfo fakeLicenseCarInfo = new FakeLicenseCarInfo();
		fakeLicenseCarInfo.setId(id);
		
		Pager result = fakeLicenseService.list(fakeLicenseCarInfo, null, null);
		
		return result;
		
	}
	
	/**
	 * 根据id跟新假套牌车辆信息
	 * 
	 * @param id
	 * @return void
	 */
	@RequestMapping("updateFakeLicenseCarInfoById.action")
	@ResponseBody
	public void updateFakeLicenseCarInfoById(@RequestParam(required = true) Long id,@RequestParam(required = true) String auditResult){
		
		FakeLicenseCarInfo fakeLicenseCarInfo = new FakeLicenseCarInfo();
		fakeLicenseCarInfo.setId(id);
		fakeLicenseCarInfo.setManulAudit(auditResult);
		fakeLicenseCarInfo.setOptionState("已处理");
		
		fakeLicenseService.update(fakeLicenseCarInfo);	
	}
	
	/**
	 * 查询是否有最新信息
	 */
	@RequestMapping("queryDifferenceNumber.action")
	@ResponseBody
	public Long queryDifferenceNumber(){
		
		Long diffNum = fakeLicenseService.uploadDifferenceNumber();
		return diffNum;
	}
	
	
	
	
}
