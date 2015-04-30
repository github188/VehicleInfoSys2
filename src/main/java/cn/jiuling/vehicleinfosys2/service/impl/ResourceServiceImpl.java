package cn.jiuling.vehicleinfosys2.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.jiuling.vehicleinfosys2.dao.ResourceDao;
import cn.jiuling.vehicleinfosys2.model.Datasource;
import cn.jiuling.vehicleinfosys2.service.ResourceService;
import cn.jiuling.vehicleinfosys2.util.ImageUtil;
import cn.jiuling.vehicleinfosys2.util.PathUtils;
import cn.jiuling.vehicleinfosys2.vo.Constant;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	@Resource
	private ResourceDao resourceDao;

	private Logger log = Logger.getLogger(ResourceServiceImpl.class);

	@Override
	public String upload(MultipartFile[] files, Datasource ds) throws Exception {
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			log.info("正在上传的文件：" + file.getOriginalFilename());
			if (file == null || file.isEmpty()) {
				return "fail";
			}
			if (ds.getType().equals((short) 1) && file.getContentType().contains("video")) {
				return "typeError";
			}
			if (ds.getType().equals((short) 2) && file.getContentType().contains("image")) {
				return "typeError";
			}
		}
		log.info("保存上传文件");
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			Datasource data = saveFile(file);
			data.setCameraId(ds.getCameraId());
			data.setType(ds.getType());
			resourceDao.save(data);
		}
		return "success";
	}

	public Datasource saveFile(MultipartFile file) throws Exception {
		return saveFile(file, null);
	}

	public Datasource saveFile(MultipartFile file, String contentType) throws Exception {
		String path = PathUtils.getTempPath();
		return saveFile(file, contentType, path);
	}

	@Override
	public Datasource saveFile(MultipartFile file, String contentType, String path) throws Exception {
		String root = PathUtils.getRealPath("/");

		String fileName = createFileName(file.getOriginalFilename());
		String separator = File.separator;
		String filePath = root + separator + path + separator + fileName;
		String url = path + "/" + fileName;
		File urlFile = new File(filePath);
		// 检查父目录是否存在,不存在则新建
		File parentFile = urlFile.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		file.transferTo(urlFile);

		String smallFilePath = null;
		String smallPicUrl = null;
		String bigFilePath = null;
		String bigPicUrl = null;

		String smallFileName = PathUtils.rename(fileName, "-small");
		String bigFileName = PathUtils.rename(fileName, "-big");

		contentType = contentType == null ? file.getContentType() : contentType;
		log.info("contentType:" + contentType);
		path = PathUtils.getTempPath();
		if (contentType.contains("image")) {
			smallFilePath = root + separator + path + separator + smallFileName;
			ImageUtil.resize(urlFile, new File(smallFilePath), 120, 0.8f);
			smallPicUrl = path + "/" + smallFileName;
			bigFilePath = root + separator + path + separator + bigFileName;
			ImageUtil.resize(urlFile, new File(bigFilePath), 600, 0.8f);
			bigPicUrl = path + "/" + bigFileName;
		} else if (contentType.contains("video") || contentType.contains("stream")) {
			smallPicUrl = snapshotPic(root, path, smallFileName, filePath, "small");
			bigPicUrl = snapshotPic(root, path, bigFileName, filePath, "big");
		}

		Datasource data = new Datasource();
		data.setFilePath(filePath);
		data.setUrl(url);
		data.setCreateTime(new Timestamp(new Date().getTime()));
		data.setName(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.')));
		data.setThumbnail(smallPicUrl);
		data.setBigUrl(bigPicUrl);
		return data;
	}

	@Override
	public String createFileName(String fileName) {
		String suffix = fileName.substring(fileName.lastIndexOf('.'), fileName
				.length());

		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);// 得到年
		int month = cal.get(Calendar.MONTH) + 1;// 得到月，因为从0开始的，所以要加1
		int day = cal.get(Calendar.DAY_OF_MONTH);// 得到天
		int hour = cal.get(Calendar.HOUR_OF_DAY);// 得到小时
		int minute = cal.get(Calendar.MINUTE);// 得到分钟
		int second = cal.get(Calendar.SECOND);// 得到秒
		int millsecond = cal.get(Calendar.MILLISECOND);// 得到毫秒

		fileName = "" + year + month + day + hour + minute + second + millsecond + suffix;
		return fileName;
	}

	/**
	 * 
	 * @param root
	 *            项目根目录绝对地址
	 * @param path
	 *            缩略图相对目录
	 * @param smallFileName
	 *            缩略图文件名，包含扩展名
	 * @param filePath
	 *            原文件绝对路径
	 * @param picType
	 *            缩略图类型：small:小图，big：大图
	 * @return 缩略图url
	 * @throws IOException
	 */
	private String snapshotPic(String root, String path, String smallFileName, String filePath, String picType) throws IOException {
		String smallFilePath;
		String smallPicUrl;
		String ffmpegPath = root + "\\3nd\\ffmpeg.exe";

		// 改后缀，扩展名都改成jpg
		smallFileName = smallFileName.substring(0, smallFileName.lastIndexOf('.')) + ".jpg";

		smallFilePath = root + File.separator + path + File.separator + smallFileName;
		smallPicUrl = path + "/" + smallFileName;

		List<String> commend = new java.util.ArrayList<String>();
		commend.add(ffmpegPath);
		commend.add("-i");
		commend.add(filePath);
		commend.add("-y");
		commend.add("-f");
		commend.add("image2");
		commend.add("-ss");
		commend.add("1");
		if (picType.equals("small")) {
			commend.add("-s");
			commend.add("120x120");
		} else {
			commend.add("-vf");
			commend.add("scale=600:-1");
		}
		commend.add(smallFilePath);
		ProcessBuilder builder = new ProcessBuilder();
		builder.command(commend);
		builder.redirectErrorStream(true);
		log.info("视频截图开始" + commend.toString());
		Process process = builder.start();
		InputStream in = process.getInputStream();
		String info = StreamUtils.copyToString(in, Charset.defaultCharset());
		log.info("result:" + info);
		in.close();
		process.destroy();
		log.info("视频截图完成...");
		return smallPicUrl;
	}

	@Override
	public Pager list(Datasource ds, Integer page, Integer rows) {
		return resourceDao.list(ds, page, rows);
	}

	@Override
	public void delete(Integer[] ids, Boolean isAllDelete) {
		for (int i = 0; i < ids.length; i++) {
			Datasource d = resourceDao.find(ids[i]);
			deleteResourceFiles(d);
			resourceDao.delete(d, isAllDelete);
		}
	}

	/**
	 * 删除资源的物理文件，包括图片视频以及缩略图
	 */
	private void deleteResourceFiles(Datasource d) {
		// 如果是目录，缩略图就不删，目录的缩略图是公共资源
		if (d.getType().equals(Constant.DATASOURCE_TYPE_MENU)) {
			return;
		}
		String root = PathUtils.getRealPath("/");
		File file = new File(root + "\\" + d.getUrl());
		File smallFile = new File(root + "\\" + d.getThumbnail());
		File bigFile = new File(root + "\\" + d.getBigUrl());
		if (file.exists()) {
			file.delete();
		}

		if (smallFile.exists()) {
			smallFile.delete();
		}
		if (bigFile.exists()) {
			bigFile.delete();
		}
	}

	@Override
	public Pager list(Integer[] ids, String location, Integer page, Integer rows) {
		return resourceDao.list(ids, location, page, rows);
	}

	@Override
	public String upload(MultipartFile[] files, Integer cId, Short dataType, String type) throws Exception {

		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			log.info("正在上传的文件：" + file.getOriginalFilename());
			if (file == null || file.isEmpty()) {
				return "fail";
			}
			if (dataType.equals(Constant.DATASOURCE_TYPE_PICTURE) && (type.contains("video") || type.contains("stream"))) {
				return "typeError";
			}
			if (dataType.equals(Constant.DATASOURCE_TYPE_VIDEO) && type.contains("image")) {
				return "typeError";
			}
		}
		log.info("保存上传文件");
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			Datasource data = saveFile(file, type);
			data.setCameraId(cId);
			data.setType(dataType);
			resourceDao.save(data);
		}
		return "success";

	}

	@Override
	public Datasource fingById(Integer resourceId) {
		return resourceDao.find(resourceId);
	}

	@Override
	public void update(Datasource datasource) {
		resourceDao.update(datasource);
	}

	@Override
	public String findBigPicFromTheLast(Integer id) {
		return resourceDao.findBigPicFromTheLast(id);
	}

	@Override
	public Object createMenu(Integer cameraId) {
		String menuName = UUID.randomUUID().toString();
		String menuPath = PathUtils.getRealPath("/") + File.separator + PathUtils.getTempPath() + File.separator + menuName;
		File f = new File(menuPath);
		if (!f.exists()) {
			f.mkdirs();
		}
		Datasource menu = new Datasource();
		menu.setType(Constant.DATASOURCE_TYPE_MENU);
		menu.setFilePath(menuPath);
		menu.setUrl("/images/folder.png");
		menu.setThumbnail("/images/folder.png");
		menu.setCreateTime(new Timestamp(new Date().getTime()));
		menu.setName(menuName);
		menu.setCameraId(cameraId);
		resourceDao.save(menu);
		return menu;
	}

	@Override
    public Pager findByParentId(Integer parentId, Integer page, Integer rows) {
		return resourceDao.findByParentId(parentId, page, rows);
	    
    }
}
