package cn.jiuling.vehicleinfosys2.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import cn.jiuling.vehicleinfosys2.annotation.Auditable;
import cn.jiuling.vehicleinfosys2.model.Task;
import cn.jiuling.vehicleinfosys2.service.DownloaderService;
import cn.jiuling.vehicleinfosys2.service.TaskService;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.vo.FollowArea;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.TaskObj;
import cn.jiuling.vehicleinfosys2.vo.TaskQuery;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("task")
public class TaskController extends BaseController {

	@Resource
	private TaskService taskService;
	
	@Resource
	private DownloaderService downloaderService;

	@RequestMapping("index.action")
	public void index() {
	}

    @Auditable(remark = "开始离线任务",operType = Constant.USER_OPRE_TYPE_ADD)
	@RequestMapping("startOffLineTask.action")
	@ResponseBody
	public Object startOffLineTask(Integer[] id, TaskObj taskObj) {
		taskService.startOffLineTask(id, taskObj);
		return SUCCESS;
	}

    @Auditable(remark = "删除任务",operType = Constant.USER_OPRE_TYPE_DEL)
	@RequestMapping("delete.action")
	@ResponseBody
	public Object delete(Integer[] ids) {
		taskService.delete(ids);
		return SUCCESS;
	}

	@RequestMapping("list.action")
	@ResponseBody
	public Pager list(Task task,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		
		TaskQuery taskQuery = new TaskQuery();
		taskQuery.setType(task.getType());
		
		if (!StringUtils.isEmpty(task.getCameraId())) {
			taskQuery.setIds(task.getCameraId().toString());
        }
		
		if (!StringUtils.isEmpty(task.getDataSourceId())) {
			taskQuery.setDataSourceId(task.getDataSourceId());
		}
		
		return taskService.query(taskQuery, page, rows);
	}

	@RequestMapping("query.action")
	@ResponseBody
	public Pager query(TaskQuery taskQuery,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		return taskService.query(taskQuery, page, rows);
	}

    @Auditable(remark = "开始实时任务",operType = Constant.USER_OPRE_TYPE_ADD)
	@RequestMapping("start.action")
	@ResponseBody
	public Object start(TaskObj task) {
		return taskService.startRealTimeTask(task);
	}

    @Auditable(remark = "开始实时图片任务",operType = Constant.USER_OPRE_TYPE_DEL)
	@RequestMapping("startRealImage.action")
	@ResponseBody
	public Object startRealImage(Integer[] id,TaskObj taskObj) {
		taskService.startRealImageTask(id,taskObj);
		return SUCCESS;
	}

	@RequestMapping("stop.action")
	@ResponseBody
	public Object stop(Integer taskId) {
		return taskService.stopRealTimeTask(taskId);
	}

	@RequestMapping(value = "bat.action")
	@ResponseBody
	public Object addFile(@RequestParam("files") MultipartFile[] files, Integer cId, Short dataType, String type, Integer parentId) throws Exception {
		if (files == null || files.length <= 0) {
			return "fail";
		}
		return taskService.addBatTask(files, cId, dataType, type, parentId);
	}
	
	@RequestMapping("downloadItemCount.action")
	@ResponseBody
	public Integer downloadItemCount(Integer taskId){
		
		Integer count = downloaderService.downloadItemCount(taskId);		
		return count;
	} 
	
	@RequestMapping("download.action")
	@ResponseBody
	public void download(Integer taskId,HttpServletResponse response) {	
		//文件路径
		String downloadFileDir = downloaderService.downloadTotalComprehensive(taskId);
		
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
