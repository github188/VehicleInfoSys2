package cn.jiuling.vehicleinfosys2.service;

import org.springframework.web.multipart.MultipartFile;

import cn.jiuling.vehicleinfosys2.model.Datasource;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface ResourceService {
	public String upload(MultipartFile[] files, Datasource ds) throws Exception;

	public Datasource saveFile(MultipartFile file) throws Exception;

	public String createFileName(String fileName);

	public Pager list(Datasource ds, Integer page, Integer rows);

	/**
	 * @Title: delete
	 * @Description: 删除资源，并判断是否连同资源下的任务和结果一起删除
	 * @param ids
	 *            所要删除的资源id数组
	 * @param isAllDelete
	 *            是否要删除该资源的任务和结果 true则删除，false则不删除
	 * @ReturnType: void
	 */
	public void delete(Integer[] ids, Boolean isAllDelete);

	public Pager list(Integer[] ids, String location, Integer page, Integer rows);

	public String upload(MultipartFile[] files, Integer cId, Short dataType, String type) throws Exception;

	public Datasource fingById(Integer resourceId);

	public void update(Datasource datasource);

	public String findBigPicFromTheLast(Integer id);

	/**
	 * 保存文件.
	 * 
	 * @param file
	 *            要上传的文件
	 * @param contentType
	 *            用户选择的文件类型
	 * @param path
	 *            文件要保存的目录,相对于根目录
	 * @return
	 * @throws Exception
	 */
	public Datasource saveFile(MultipartFile file, String contentType, String path) throws Exception;

	/**
	 * 在服务器上创建一个目录
	 * 
	 * @return
	 */
	public Object createMenu(Integer cameraId);
	
	/**
	 * 浏览目录下的文件
	 * @param id
	 */

	public Pager findByParentId(Integer parentId, Integer page, Integer rows);

}
