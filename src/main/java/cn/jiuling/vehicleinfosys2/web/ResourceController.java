package cn.jiuling.vehicleinfosys2.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.jiuling.vehicleinfosys2.model.Datasource;
import cn.jiuling.vehicleinfosys2.service.ResourceService;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * @ClassName: ResourceController
 * @Description: 资源控制类
 * @author Gost_JR
 * @date: 2014-12-8 上午09:18:41
 */
@RequestMapping("resource")
@Controller
public class ResourceController extends BaseController {

	@Resource
	private ResourceService resourceService;

	/**
	 * 
	 * @param ds
	 * @param ids
	 * @param location
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("list.action")
	@ResponseBody
	public Pager list(Datasource ds,
			@RequestParam(required = false) Short type,
			@RequestParam(required = false) Integer[] ids,
			@RequestParam(defaultValue = "") String location,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {

		if ((("").equals(ids) || null == ids) && (location.trim().equals("") || null == location)) {
			return resourceService.list(ds, page, rows);
		}
		return resourceService.list(ids, location, page, rows);
	}

	@RequestMapping(value = "add.action")
	@ResponseBody
	public Object add(@RequestParam("files") MultipartFile[] files, Datasource ds) throws Exception {
		if (files == null || files.length <= 0) {
			return "fail";
		}
		return resourceService.upload(files, ds);
	}

	@RequestMapping(value = "createMenu.action")
	@ResponseBody
	public Object createMenu(Integer cameraId) {
		return resourceService.createMenu(cameraId);
	}

	/**
	 * 
	 * @param files
	 *            文件对象
	 * @param cId
	 *            监控点id
	 * @param dataType
	 *            用户选择的文件类型
	 * @param type
	 *            文件本身带的类型
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "addFile.action")
	@ResponseBody
	public Object addFile(@RequestParam("files") MultipartFile[] files, Integer cId, Short dataType, String type) throws Exception {
		if (files == null || files.length <= 0) {
			return "fail";
		}
		return resourceService.upload(files, cId, dataType, type);
	}

	/**
	 * @Title: remove
	 * @Description: 删除资源，并判断是否连同资源下的任务和结果一起删除
	 * @param ids
	 *            所要删除的资源id
	 * @param isAllDelete
	 *            是否连同资源下的任务和结果一起删除
	 * @ReturnType: Object
	 */
	@RequestMapping(value = "remove.action")
	@ResponseBody
	public Object remove(Integer[] ids, Boolean isAllDelete) {
		resourceService.delete(ids, isAllDelete);
		return "success";
	}
	
	@RequestMapping(value="browseList.action")
	@ResponseBody
	public Object browseList(Integer parentId,
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer rows) {
		return resourceService.findByParentId(parentId, page, rows);
	}
}
