package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.ResourceDao;
import cn.jiuling.vehicleinfosys2.dao.UserUploadVideoDao;
import cn.jiuling.vehicleinfosys2.exception.ServiceException;
import cn.jiuling.vehicleinfosys2.model.Datasource;
import cn.jiuling.vehicleinfosys2.model.UserUploadVideo;
import cn.jiuling.vehicleinfosys2.service.ResourceService;
import cn.jiuling.vehicleinfosys2.util.*;
import cn.jiuling.vehicleinfosys2.vo.Constant;
import cn.jiuling.vehicleinfosys2.vo.MultipartFileWrapper;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private ResourceDao resourceDao;
    @Resource
    private UserUploadVideoDao userUploadVideoDao;

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

    public Datasource saveJavaFile(File file) throws Exception {

        String root = PathUtils.getRealPath("/");
        String uploadPath = PathUtils.getUploadPath();

        Random r = new Random();
        String fileName = Math.abs(r.nextInt())+"_"+ file.getName();
        String separator = File.separator;
        String filePath = uploadPath + separator + fileName;
        String url = uploadPath + "/" + fileName;

        File urlFile = new File(filePath);
        FileUtils.copyFile(file, urlFile);

        // 检查父目录是否存在,不存在则新建
        File parentFile = urlFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }


        String smallPicUrl = null;
        String bigPicUrl = null;

        String smallFileName = PathUtils.rename(fileName, "-small");
        String bigFileName = PathUtils.rename(fileName, "-big");

        //截图并生成缩略图
        smallPicUrl = snapshotPic(root, uploadPath, smallFileName, filePath, "small");
        bigPicUrl = snapshotPic(root, uploadPath, bigFileName, filePath, "big");

        Datasource data = new Datasource();

        data.setFilePath(filePath);
        data.setUrl(url);
        data.setCreateTime(new Timestamp(new Date().getTime()));
        data.setName(file.getName());
        data.setThumbnail(smallPicUrl);
        data.setBigUrl(bigPicUrl);

        return data;
    }

    public Datasource saveFile(MultipartFile file, String contentType) throws Exception {
        String uploadPath = PathUtils.getUploadPath();
        return saveFile(file, contentType, uploadPath);
    }

    @Override
    public Datasource saveFile(MultipartFile file, String contentType, String uploadPath) throws Exception {
        String root = PathUtils.getRealPath("/");

        //String fileName = createFileName(file.getOriginalFilename());
        Random r = new Random();
        String suffix = "_" + Math.abs(r.nextInt());
        String fileName = PathUtils.rename(file.getOriginalFilename(), suffix);
        String separator = File.separator;
        String filePath = uploadPath + separator + fileName;
        String url = uploadPath + "/" + fileName;
        File urlFile = new File(filePath);
        // 检查父目录是否存在,不存在则新建
        File parentFile = urlFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }


        String smallFilePath = null;
        String smallPicUrl = null;
        String bigFilePath = null;
        String bigPicUrl = null;

        String smallFileName = PathUtils.rename(fileName, "-small");
        String bigFileName = PathUtils.rename(fileName, "-big");

        contentType = contentType == null ? file.getContentType() : contentType;
        log.info("contentType:" + contentType);
        uploadPath = PathUtils.getUploadPath();
        String uploadStr = uploadPath.substring(3);
        if (contentType.contains("image")) {
            file.transferTo(urlFile);
            smallFilePath = uploadPath + separator + smallFileName;
            ImageUtil.resize(urlFile, new File(smallFilePath), 120, 0.8f);
            smallPicUrl = uploadStr + "/" + smallFileName;
            bigFilePath = uploadPath + separator + bigFileName;
            ImageUtil.resize(urlFile, new File(bigFilePath), 600, 0.8f);
            bigPicUrl = uploadStr + "/" + bigFileName;
        } else if (contentType.contains("video") || contentType.contains("stream")) {
            file.transferTo(urlFile);
            smallPicUrl = snapshotPic(root, uploadPath, smallFileName, filePath, "small");
            bigPicUrl = snapshotPic(root, uploadPath, bigFileName, filePath, "big");
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
        Random r = new Random();
        fileName = "" + year + month + day + hour + minute + second + millsecond + "_" + Math.abs(r.nextInt()) + suffix;
        return fileName;
    }

    @Override
    public String generateAVIFileName(String fileName) {

        try {
            String preFileName = fileName.substring(0,fileName.lastIndexOf("."));
            fileName = preFileName + ".avi";
            fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return fileName;
    }
    public String generateSrcFileName(String fileName) {

        try {
            fileName = new String(fileName.getBytes("gb2312"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return fileName;
    }

    /**
     * @param root          项目根目录绝对地址
     * @param uploadPath    缩略图相对目录
     * @param smallFileName 缩略图文件名，包含扩展名
     * @param filePath      原文件绝对路径
     * @param picType       缩略图类型：small:小图，big：大图
     * @return 缩略图url
     * @throws IOException
     */
    private String snapshotPic(String root, String uploadPath, String smallFileName, String filePath, String picType) throws IOException {
        String smallFilePath;
        String smallPicUrl;
        String ffmpegPath = root + "\\3nd\\ffmpeg.exe";

        // 改后缀，扩展名都改成jpg
        smallFileName = smallFileName.substring(0, smallFileName.lastIndexOf('.')) + ".jpg";

        smallFilePath = uploadPath + File.separator + smallFileName;
        Random r = new Random();
        String oldFilePath = uploadPath + File.separator +Math.abs(r.nextInt()) + "_test.jpg";
        String uploadStr = uploadPath.substring(3);
        smallPicUrl = uploadStr + "/" + smallFileName;

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
        commend.add(oldFilePath);
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
        //重命名文件
        FileUtils.renameFile(oldFilePath,smallFilePath);
        return smallPicUrl;
    }

    @Override
    public Pager list(Datasource ds, Integer page, Integer rows) {
        String query = " and parentId is null ";
        return resourceDao.list(ds, page, rows, query);
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
    public String upload(MultipartFile[] files, Integer cId, Short dataType, String type, Integer userId, Integer videoType) throws Exception {

        String midUpload = PropertiesUtils.get("midUploadPath");
        midUpload = new String(midUpload.getBytes("gb2312"), "ISO8859-1");

        String isTranscode = PropertiesUtils.get("isTranscode");
        File midUploadFile = new File(midUpload);

        if (!midUploadFile.isDirectory()) {
            midUploadFile.mkdirs();
        }

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

            if (isTranscode.equalsIgnoreCase(cn.jiuling.vehicleinfosys2.util.Constant.IS_TRANSCODE_TRUE) && dataType.equals(Constant.DATASOURCE_TYPE_VIDEO)) {

                String root = PathUtils.getRealPath("");
                root = new String(root.getBytes("gb2312"), "ISO8859-1");
                //上传中转目录
                String tempPath = root + File.separator + "temp" + File.separator;
                //原始文件名
                String originalFilename = file.getOriginalFilename();
                //经过编码的文件名
                String aviFileName = generateAVIFileName(originalFilename);
                String srcFileName = generateSrcFileName(originalFilename);
                //数据库使用gb2312保存的文件名
                String srcUrl = tempPath + srcFileName;
                //真实文件路径
                String transSrcUrl = tempPath + originalFilename;

                String destURL = midUpload + File.separator + aviFileName;
                String fileName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));

                File srcUrlFile = new File(transSrcUrl);
                if (srcUrlFile.exists()) {
                    return "exists";
                }

                file.transferTo(new File(transSrcUrl));

                UserUploadVideo u = new UserUploadVideo();

                u.setUserid(BigInteger.valueOf(userId));
                u.setSrcURL(srcUrl);
                u.setDestURL(destURL);
                u.setStatus(cn.jiuling.vehicleinfosys2.util.Constant.TRANSTASKMGR_STATUS_INCOMPLETE);
                u.setLastErrCode(0);
                u.setProgress(0);
                u.setRetryCount(0);
                u.setLastTryVideoVendorType(-1);
                u.setResolution("--");
                u.setDuration(0);
                u.setSpace(0);
                u.setFrameRate(0);
                u.setVideoType(videoType);
                u.setCameraId(cId);
                u.setVideoName(fileName);
                u.setIsAddedToDataSource(cn.jiuling.vehicleinfosys2.util.Constant.ISADDEDTODATASOURCE_NO);

                userUploadVideoDao.save(u);

            } else {
                Datasource data = saveFile(file, type);
                data.setCameraId(cId);
                data.setType(dataType);
                resourceDao.save(data);
            }
        }
        return "success";

    }

    @Override
    public String upload(File file, Integer cId, Short dataType) throws Exception {

        Datasource data = saveJavaFile(file);

        data.setCameraId(cId);
        data.setType(dataType);

        resourceDao.save(data);

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
        String menuPath = PathUtils.getUploadPath() + File.separator + menuName;
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
    public Object createMenu(Integer cameraId, String menuPath) {
        File m = new File(menuPath);
        String menuName = m.getName();
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

    @Override
    public Object addServerFile(String menuPath, Integer cId, HttpServletRequest req, String uuid) {
        File sourceMenu = new File(menuPath);
        if (!sourceMenu.exists() || !sourceMenu.isDirectory()) {
            throw new ServiceException("服务器上目录不存在或目录填写错误!");
        }
        //提交任务时直接使用服务器上的目录
        Datasource menu = (Datasource) createMenu(cId, menuPath);
        copyFile(menuPath, menu, cId, req, uuid);
        return "success";
    }

    /**
     * 复制目录操作
     *
     * @param sourceDir
     * @param menu
     * @param cId
     * @param req
     * @param uuid
     */
    private void copyFile(String sourceDir, Datasource menu, Integer cId, HttpServletRequest req, String uuid) {
        try {
            int uploadFileNum = 0;
            UploadStatusCache.push(uuid, uploadFileNum);
            File s = new File(sourceDir);
            File f;
            Datasource data;
            File[] files = s.listFiles();
            String path = PathUtils.getUploadPath() + "/" + menu.getName();
            Integer parentId = menu.getId();
            String contentType = "image";
            for (int i = 0; i < files.length; i++) {
                f = files[i];
                if (f.getName().matches(".*\\.(bmp|png|jpg|jpeg|gif|tiff|tif)$")) {
                    //过滤损坏的文件
                    Image img = ImageIO.read(f);
                    if (null != img) {
                        if (UploadStatusCache.get(uuid) == null) {
                            log.info("上传中断了,已处理" + uploadFileNum + "个文件!");
                            break;
                        }
                        UploadStatusCache.push(uuid, ++uploadFileNum);
                        //只上传一张做为识别时画感兴趣区用
                        if (uploadFileNum > 1) {
                            //contentType="unknown";
                            break;
                        }
                        data = saveFile(new MultipartFileWrapper(f), contentType, path);
                        data.setCameraId(cId);
                        data.setType(Constant.DATASOURCE_TYPE_PICTURE);
                        // 将文件入库
                        data.setParentId(parentId);
                        resourceDao.save(data);
                    }
                }
            }
        } catch (Exception e) {
            String msg = "文件复制错误!sourceDir:" + sourceDir + ",targetDir:" + menu.getFilePath();
            log.error(msg, e);
            throw new ServiceException(msg);
        }
        UploadStatusCache.remove(uuid);
    }

    @Override
    public Pager downloadRemoteImages() {
        Pager pager = resourceDao.fromRemoteDB(null, null);
        return pager;
    }
}
