package cn.jiuling.vehicleinfosys2.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.jiuling.vehicleinfosys2.service.DownloaderService;
import cn.jiuling.vehicleinfosys2.service.ResultService;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;

@Controller
@RequestMapping("result")
public class ResultController extends BaseController {

	@Resource
	private ResultService resultService;
	@Resource
	private DownloaderService downloaderService;

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
        String resource = "serverIp.properties";
		mav.addObject("taskId", taskId);
		mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
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
		return resultService.listByTaskId(taskId, page, rows);
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
        String resource = "serverIp.properties";
		mav.addObject("result", resultService.findById(resultId));
		mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
		return mav;
	}
	
	@RequestMapping(value = "findById.action")
	@ResponseBody
	public Object findById(Long resultId) {
		return resultService.findById(resultId);
	}

	@RequestMapping("list.action")
	@ResponseBody
	public Pager list(ResultQuery rq,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		return resultService.list(rq, page, rows);
	}

	@RequestMapping("index.action")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("result/index");
        String resource = "serverIp.properties";
		mav.addObject("PictureServerHost", PropertiesUtils.get("PictureServerHost",resource));
		return mav;
	}

	@RequestMapping("query.action")
	@ResponseBody
	public Pager query(ResultQuery rq,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		return resultService.query(rq, page, rows);
	}
	
	@RequestMapping("downloadItemCount.action")
	@ResponseBody
	public Integer downloadItemCount(ResultQuery rq){
		
		Integer count = downloaderService.downloadItemCount(rq);		
		return count;
	} 
	
	@RequestMapping("download.action")
	@ResponseBody
	public void download(ResultQuery rq,HttpServletResponse response) {	
		//文件路径
		String downloadFileDir = downloaderService.downloadTotalComprehensive(rq);
		
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
	
	@RequestMapping("downloadChoose.action")
	@ResponseBody
	public void downloadChoose(String ids,HttpServletResponse response) {	
		//文件路径
		String downloadFileDir = downloaderService.downloadTotalChoose(ids);
		
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
            byte[] buffer = new byte[1024];
            
            while ((b = inputStream.read(buffer)) != -1){   
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
