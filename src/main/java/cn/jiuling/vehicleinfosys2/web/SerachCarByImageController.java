package cn.jiuling.vehicleinfosys2.web;

import java.awt.Image;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.jiuling.vehicleinfosys2.model.Datasource;
import cn.jiuling.vehicleinfosys2.model.VlprTask;
import cn.jiuling.vehicleinfosys2.model.VlprVfmTask;
import cn.jiuling.vehicleinfosys2.service.DownloaderService;
import cn.jiuling.vehicleinfosys2.service.SerachCarByImageService;
import cn.jiuling.vehicleinfosys2.util.JsonUtils;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;
import cn.jiuling.vehicleinfosys2.vo.TaskObj;
/**
 * 以图搜车管理
 * 
 * @author daixiaowei
 *
 */
@Controller
@RequestMapping("serachCarByImage")
public class SerachCarByImageController {
	
	//日志管理
	private Logger log = Logger.getLogger(SerachCarByImageController.class);
	
	@Resource
	private SerachCarByImageService serachCarByImageService;
	
	@Resource
	private DownloaderService downloaderService;
	
	/**
	 * 装载index.jsp页面
	 */
	@RequestMapping("index.action")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView("searchCarByImage/index");
		return view;
	}
	
	/**
	 * 上传图片
	 * @param files
	 * @param cId
	 * @param dataType
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addImageFile.action")
	@ResponseBody
	public String addImage(HttpServletResponse res ,@RequestParam("files") MultipartFile[] files, Integer cId, Short dataType, String type) throws Exception{
		Datasource datasource = null;
		datasource = serachCarByImageService.uploadImage(files, cId, dataType, type);
		String jsonStr = JsonUtils.toJson(datasource);
		return jsonStr;
	}
	
	/**
	 * 添加图片识别任务
	 * @param id
	 * @param taskObj
	 * @return
	 */
	@RequestMapping("startOffLineTask.action")
	@ResponseBody
	public Long startOffLineTask(Integer[] id, TaskObj taskObj) {
		Long taskId = serachCarByImageService.startOffLineTask(id, taskObj);
		return taskId;
	}
	
	/**
	 * 根据任务id获取识别结果信息
	 * @param taskId
	 * @return list
	 * @return String 特征区坐标
	 * @throws IOException 
	 */
	@RequestMapping("getRecognitionResult.action")
	@ResponseBody
	public Pager getRecognitionResult(Long taskId,String followarea) throws IOException{
		Pager list = serachCarByImageService.getRecognitionResult(taskId);
		
		Pager result = new Pager(0L,new ArrayList());
		/**
		 * 根据特征区赛选识别结果
		 */
		List picList = list.getRows();
		for (Object item : picList) {
			boolean breakFlage = false;
			ResultVo resultVo = (ResultVo) item;
			// 原始图片
			File orgFile = new File(resultVo.getImagePath());

			ImageIcon imageIcon = new ImageIcon(orgFile.getCanonicalPath());
			Image image = imageIcon.getImage();
			// 图片的宽
			int width = image.getWidth(null);
			// 图片的高
			int height = image.getHeight(null);

			// 压缩率
			double ratio = 0;
			if (width > height) {
				ratio = width / 600.0;
			} else {
				ratio = height / 600.0;
			}
			
			//车身左上角x坐标
			Short vehicleLeft = resultVo.getVehicleLeft();			
			//车身左上角y坐标
			Short vehicleTop = resultVo.getVehicleTop();			
			//车身右下角x坐标
			Short vehicleRight = resultVo.getLocationRight();			
			//车身右下角y坐标
			Short vehicleBootom = resultVo.getVehicleBootom();
			
			String[] frameArray = followarea.split("#");
			for(String frame :frameArray){
				//多边形坐标
				String [] pointsArray = frame.split("\\|");
				Polygon polygon = new Polygon();
				for(String pointStr:pointsArray){
					String[] point = pointStr.split(",");
					polygon.addPoint((int)(Integer.valueOf(point[0])*ratio), (int)(Integer.valueOf(point[1])*ratio));
				}
				//特征物框多边形
				Area FeatureArea = new Area(polygon);	
				
				//特征物多变形与车身框是否有交集
				boolean flage = FeatureArea.intersects(vehicleLeft, vehicleTop, vehicleRight-vehicleLeft, vehicleBootom-vehicleTop);
				
				if(flage){
					result.setTotal(1L);
					result.getRows().add(item);
					breakFlage = true;
					break;
				}
			}
			
			if(breakFlage){
				break;
			}
			

		}
    	
		return result;
	}
	
	/**
	 * 添加以图搜车任务
	 * @param data
	 */
	@RequestMapping("saveVlprTaskPro.action")
	@ResponseBody
	public Long saveVlprTaskPro(Long vlprTaskId,VlprVfmTask data,TaskObj taskObj){
		Long taskId = serachCarByImageService.saveVlprTask(vlprTaskId, data, taskObj);
		return taskId;
	}
	
	/**
     * 根据识别任务id，查询识别任务
     */
	@RequestMapping("getVlprTask.action")
	@ResponseBody
	public VlprTask getVlprVfmTask(long vfmTaskId){
		return serachCarByImageService.getVlprTask(vfmTaskId);
	}
	
	@RequestMapping("serachIndex.action")
	public ModelAndView serachIndex() {
		ModelAndView mav = new ModelAndView("searchCarByImage/searchResults");
        String resource = "serverIp.properties";
		mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
		return mav;
	}
	
	@RequestMapping("serachManualIndex.action")
	public ModelAndView serachManualIndex() {
		ModelAndView mav = new ModelAndView("searchCarByImage/searchesResults");
        String resource = "serverIp.properties";
		mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
		return mav;
	}
	
	@RequestMapping("taskSerachIndex.action")
	public ModelAndView taskSerachIndex() {
		ModelAndView mav = new ModelAndView("searchCarByImage/taskResults");
        String resource = "serverIp.properties";
		mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
		return mav;
	}

	@RequestMapping("list.action")
	@ResponseBody
	public Pager list(Integer taskID,
			          @RequestParam(required = false, defaultValue = "1") Integer page,
					  @RequestParam(required = false, defaultValue = "10") Integer rows) {
		Pager p = serachCarByImageService.querylist(taskID, page, rows);
		return p;
	}

	@RequestMapping("originallistdetails.action")
	@ResponseBody
	public Pager originallistdetails(Integer taskID,
					  @RequestParam(required = false, defaultValue = "1") Integer page,
					  @RequestParam(required = false, defaultValue = "10") Integer rows) {
		Pager p = serachCarByImageService.originalquerylist(taskID, page, rows);
		return p;
	}

	@RequestMapping("originallisttask.action")
	@ResponseBody
	public Pager originallisttask(Integer taskID,
							  @RequestParam(required = false, defaultValue = "1") Integer page,
							  @RequestParam(required = false, defaultValue = "10") Integer rows) {
		Pager p = serachCarByImageService.originalquerylisttask(taskID, page, rows);
		return p;
	}

	@RequestMapping("originallisttaskid.action")
	@ResponseBody
	public Pager originallisttaskid(Integer taskID,
								  @RequestParam(required = false, defaultValue = "1") Integer page,
								  @RequestParam(required = false, defaultValue = "10") Integer rows) {
		Pager p = serachCarByImageService.originalquerylisttaskid(taskID, page, rows);
		return p;
	}
	
	/**
	 * 查询以图搜车任务
	 * @param vlprVfmTask
	 * @param page
	 * @param rows
	 * @return Pager
	 */
	@RequestMapping("getVfmTaskList.action")
	@ResponseBody
	public Pager getVlprVfmTask(VlprVfmTask vlprVfmTask,Integer page, Integer rows){
		return serachCarByImageService.getVlprVfmTask(vlprVfmTask, page, rows);
	}
	
	/**
     * 保存以图搜车任务(手动)
     * @param vlprVfmTask
     */
	@RequestMapping("saveManualVlprVfmTask.action")
	@ResponseBody
    public Long saveManualVlprVfmTask(Integer dsId,String plateFollowarea,String featurFollowarea,Integer width,Integer height,VlprVfmTask vlprVfmTask){
		Long id = serachCarByImageService.saveManualVlprVfmTask(dsId, plateFollowarea, featurFollowarea, width, height, vlprVfmTask);
    	return id;
    }
	
	/**
	 * 停止以图搜车任务
	 * @param taskId
	 * @return int
	 */
	@RequestMapping("stopVlprVfmTask.action")
	@ResponseBody
	public int stopVlprVfmTask(Long taskId){			
		int updateItemNum = serachCarByImageService.stopVlprVfmTask(taskId);		
		return updateItemNum;		
	}
	
	/**
	 * 以图收车计算要下载的数据条目数
	 * @param vfmTaskId
	 * @return Integer 要下载的数据条目数
	 */
	@RequestMapping("downloadItemCount.action")
	@ResponseBody
	public Integer downloadItemCount(Integer vfmTaskId){
		
		Integer count = downloaderService.downloadScarItemCount(vfmTaskId);		
		return count;
	}
	
	/**
	 * 以图搜车下载
	 * @param vfmTaskId
	 * @param response
	 */
	@RequestMapping("download.action")
	@ResponseBody
	public void download(Integer vfmTaskId,HttpServletResponse response) {	
		//文件路径
		String downloadFileDir = downloaderService.downloadScarTotal(vfmTaskId);
		
		File file = new File(downloadFileDir);
		
		//1.设置文件ContentType类型，这样设置，会自动判断下载文件类型  
        response.setContentType("multipart/form-data;charset=UTF-8");
        //2.设置文件头：最后一个参数是设置下载文件名 
        response.setHeader("Content-Disposition", "attachment;fileName="+file.getName());
        response.addHeader("Content-Length", "" + file.length());
        ServletOutputStream out=null;
        FileInputStream inputStream=null;
        
        try {
			inputStream = new FileInputStream(file);
			//3.通过response获取ServletOutputStream对象(out)  
            out = response.getOutputStream();
            
            int b = 0;
            byte[] buffer = new byte[512];
            
            while (b != -1){  
                b = inputStream.read(buffer);  
                //4.写到输出流(out)中  
                out.write(buffer,0,b);  
            } 
            
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(out !=null){
					out.close();
				}
				if(inputStream!=null){
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
            
		}	
		
	}
}
