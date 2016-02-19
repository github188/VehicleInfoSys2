package cn.jiuling.vehicleinfosys2.service.impl;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.swing.ImageIcon;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.jiuling.vehicleinfosys2.dao.ResourceDao;
import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.dao.VlprSpeciallyResultDao;
import cn.jiuling.vehicleinfosys2.dao.VlprTaskDao;
import cn.jiuling.vehicleinfosys2.dao.VlprVfmResultDao;
import cn.jiuling.vehicleinfosys2.dao.VlprVfmTaskDao;
import cn.jiuling.vehicleinfosys2.model.Datasource;
import cn.jiuling.vehicleinfosys2.model.VlprTask;
import cn.jiuling.vehicleinfosys2.model.VlprVfmTask;
import cn.jiuling.vehicleinfosys2.service.ResourceService;
import cn.jiuling.vehicleinfosys2.service.SerachCarByImageService;
import cn.jiuling.vehicleinfosys2.util.ImageUtil;
import cn.jiuling.vehicleinfosys2.util.PathUtils;
import cn.jiuling.vehicleinfosys2.vo.Constant;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.PolygonPoint;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;
import cn.jiuling.vehicleinfosys2.vo.TaskObj;

/**
 * 以图搜车service
 * @author daixiaowei
 *
 */
@Service("serachCarByImageService")
public class SerachCarByImageServiceImpl implements SerachCarByImageService {
	
	@Resource
	private ResourceDao resourceDao;
	
	@Resource
    private VlprTaskDao vlprTaskDao;
	
	@Resource
	private VlprResultDao vlprResultDao;
	
	@Resource
    private ResourceService resourceService;
	
	@Resource
	private VlprVfmTaskDao vlprVfmTaskDao;
	
	@Resource
	private VlprVfmResultDao vlprVfmResultDao;
	
	@Resource
	private VlprSpeciallyResultDao vlprSpeciallyResultDao;

	private Logger log = Logger.getLogger(SerachCarByImageServiceImpl.class);
	
	/**
	 * 保存上传的图片
	 * @param files 文件对象list
	 * @param cId  监控点id
	 * @param dataType 资源类型（数据库）
	 * @param type  资源类型
	 * @return Datasource
	 * @throws Exception
	 */
	@Override
	public Datasource uploadImage(MultipartFile[] files, Integer cId, Short dataType, String type) throws Exception {
		
		MultipartFile file = files[0];
		
		if (file == null || file.isEmpty()) {
			return null;
		}		
		log.info("正在上传的文件：" + file.getOriginalFilename());
		
		//保存上传文件
		Datasource data = saveImageFile(file, type);
		data.setCameraId(cId);
		data.setType(dataType);
		resourceDao.save(data);
		Integer lastId = resourceDao.getLastInsertId().intValue();
		data.setId(lastId);
		return data;

	}
	
	//保存图片到服务器
	private Datasource saveImageFile(MultipartFile file, String contentType) throws IllegalStateException, IOException{
		
		//获取上传文件保存路径
		String uploadPath = PathUtils.getUploadPath();
		
		String fileName = createFileName(file.getOriginalFilename());
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
	
	//生成文件名
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
		Random r=new Random();
		fileName = "" + year + month + day + hour + minute + second + millsecond +"_"+Math.abs(r.nextInt())+ suffix;
		return fileName;
	}
	
	
	/**
	 * 添加图片识别任务
	 */
	@Override
	public Long startOffLineTask(Integer[] ids, TaskObj taskObj) {
		Long taskId = 0L;
        for (Integer resourceId : ids) {
            Datasource resource = resourceDao.find(resourceId);
            String filePath = "file://" + resource.getFilePath();
            taskId = startTask(resource.getCameraId(), taskObj.getType(), filePath, resourceId, taskObj);
        }
        
        return taskId;
    }
	
	/**
     * 开始任务的封装
     *
     * @param cameraId 监控点id
     * @param type     1实时任务2离线任务
     * @param filePath 数据源地址,可以是实时的也可以是离线的
     * @param taskObj
     */
    private Long startTask(Integer cameraId, Short type, String filePath, Integer resourceId, TaskObj taskObj) {
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp createTime = new Timestamp(currentTimeMillis);

        // 插入识别程序的任务表
        VlprTask vt = new VlprTask();
        vt.setStatus(Constant.VLPRTASK_STATUS_WAIT);
        vt.setVideoId(currentTimeMillis);
        vt.setCreateTime(createTime);
        vt.setFlag(Constant.VLPRTASK_FLAG_VALID);
        String followarea = taskObj.getFollowarea();

        if (type.equals(Constant.TASK_TYPE_OFFLINE)) {
            Datasource d = resourceDao.find(resourceId);
            d.setFollowarea(followarea);
            resourceDao.update(d);
        }

        //设置识别结果目录
        String resultPath = PathUtils.getResultPath();
        /**
         * 为从服务器设置共享路径
         */
        //获取本机ip
      /*  try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			resultPath = resultPath.replace(resultPath.substring(0, 2),"//"+ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}*/
        vt.setVlprResultPath(resultPath);

        vt.setFilePath(StringUtils.isEmpty(filePath) ? "" : filePath);
        vt.setProgress((short) 0);
        vt.setRecognitionSlaveIp("");
        vt.setRetryCount((short) 0);
        vt.setZoomWidth(taskObj.getZoomWidth());
        vt.setZoomHeight(taskObj.getZoomHeight());
        vt.setDropFrame(taskObj.getDropFrame());
        vt.setDetectMode(taskObj.getDetectMode());
		vt.setMinimumWidth(taskObj.getMinimumWidth());
		vt.setMaximumHeight(taskObj.getMaximumHeight());
        if(1==taskObj.getVedioDetectMode()){
        	vt.setVedioDetectMode(taskObj.getVedioDetectMode());
        }else{
        	vt.setVedioDetectMode((short)0);
        }       
        //插入以图搜车标示
        vt.setVlprTaskType(1);
        
        vlprTaskDao.save(vt);

        return vt.getTaskId();
    }
    
    
    /**
     * 生成特征图片png
     *
     * @param followArea
     * @param taskObj
     * @return 返回png的相对路径
     */
    private String getCharacteristicUrl(String followArea, TaskObj taskObj) {
        if (StringUtils.isEmpty(followArea)) {
            return "";
        }
        //填充二维数组,#是多边形分隔符,|是点分隔符
        //126,84|331,76|296,270|164,257#410,94|535,109|520,293|349,294
        String[] polygonArr = followArea.split("#");
        int polygonLen = polygonArr.length;
        PolygonPoint[][] polygons = new PolygonPoint[polygonLen][];
        for (int i = 0; i < polygonLen; i++) {
            String pointsStr = polygonArr[i];
            String[] pointsArr = pointsStr.split("\\|");
            int pointsLen = pointsArr.length;
            polygons[i] = new PolygonPoint[pointsLen];
            for (int j = 0; j < pointsLen; j++) {
                String xyStr = pointsArr[j];
                String[] point = xyStr.split(",");
                polygons[i][j] = new PolygonPoint(point[0], point[1]);
            }
        }
        String fileName = createFileName("test.png");
        String coverPngDir = PathUtils.getCoverPngPath();
        String coverPngAbsoluteDir = PathUtils.getRealPath("/") + File.separator + coverPngDir + File.separator;
        File file = new File(coverPngAbsoluteDir);
        if (!file.exists()) {
            file.mkdir();
        }
        
        String fnForward = fileName.substring(0,fileName.lastIndexOf('.'));
        String fnBack = fileName.substring(fileName.lastIndexOf('.'));
        String fn8bitFileDir =coverPngAbsoluteDir+fnForward+"_8bit_temp"+fnBack; 
        ImageUtil.generate8BitPNG(taskObj.getAreaType(), taskObj.getWidth(), taskObj.getHeight(), polygons, fn8bitFileDir);
              
        return fn8bitFileDir;
    }
    
    /**
     * 获取识别结果信息
     * @return list
     */
    @Override
    public Pager getRecognitionResult(Long taskId){
    	ResultQuery qc = new ResultQuery();
    	qc.setTaskId(taskId);
    	Pager result =vlprSpeciallyResultDao.querySerachCarByImageResult(qc);    	
    	return result;
    }
    
    /**
     * 保存以图搜车任务(自动)
     * @param vlprVfmTask
     */
    @Override
    public Long saveVlprTask(Long vlprTaskId,VlprVfmTask vlprVfmTask,TaskObj taskObj){
    	
    	if(vlprVfmTask.getPlateType() == -1){
    		vlprVfmTask.setPlateType(null);
    	}
    	if("0002-11-30 00:00:00.0".equals(vlprVfmTask.getPassStartTime().toString())){
    		vlprVfmTask.setPassStartTime(null);
    	}
    	if("0002-11-30 00:00:00.0".equals(vlprVfmTask.getPassEndTime().toString())){
    		vlprVfmTask.setPassEndTime(null);
    	}
    	if(StringUtils.isEmpty(vlprVfmTask.getCameraId())){
    		vlprVfmTask.setCameraId(null);
    	}
    	if(StringUtils.isEmpty(vlprVfmTask.getVehicleColor())){
    		vlprVfmTask.setVehicleColor(null);
    	}
    	if(StringUtils.isEmpty(vlprVfmTask.getVehicleKind())){
			vlprVfmTask.setVehicleKind(null);		
		}
		if(StringUtils.isEmpty(vlprVfmTask.getVehicleBrand())){
			vlprVfmTask.setVehicleBrand(null);		
		}
		if(StringUtils.isEmpty(vlprVfmTask.getVehicleSeries())){
			vlprVfmTask.setVehicleSeries(null);
		}
		if(StringUtils.isEmpty(vlprVfmTask.getVehicleStyle())){
			vlprVfmTask.setVehicleStyle(null);
		}
		if(StringUtils.isEmpty(vlprVfmTask.getLicense())){
			vlprVfmTask.setLicense(null);
		}
    	
    	long time = System.currentTimeMillis();
    	Timestamp currentTime = new Timestamp(time);
    	vlprVfmTask.setInsertTime(currentTime);
    	vlprVfmTask.setProgress((short)0);
    	vlprVfmTask.setRetryCount((short)0);
    	vlprVfmTask.setStatus(Constant.VLPRTASK_STATUS_WAIT);
    	vlprVfmTask.setFlag(Constant.VLPRTASK_FLAG_VALID);
    	vlprVfmTask.setVlprTaskID(vlprTaskId);
    	
    	if(vlprTaskId !=null){
    		VlprTask vlprTask = vlprTaskDao.findByProperty("taskId", vlprTaskId);

    		//目标图片路径
    		String orgImagePath = "";
    		if(vlprTask.getFilePath() !=null){
    			orgImagePath = vlprTask.getFilePath().replace("file://", "");
    			vlprVfmTask.setDesImagePath(orgImagePath);
    		}    		
    		
    		/**
    		 * 生成8位的特征图片png
    		 */
    		//缩小的特征图片路径
    		String characteristicUrl = getCharacteristicUrl(taskObj.getFollowarea(), taskObj);
    		//原图片大小的特征图片
    		String orgCharacteristicUrl = "";
            
            if (null != characteristicUrl && !"".endsWith(characteristicUrl)) {
            	//原图片地址
                String orgFile = orgImagePath;
                //png图片地址（遮罩）
                String pngFile = characteristicUrl;
        		try {
        			ImageIcon imageIcon = new ImageIcon(new File(orgFile).getCanonicalPath());
        			Image image = imageIcon.getImage();
        			
        			//原始图片宽度
        			int newWidth = image.getWidth(null);
        			//原始图片高度
        			int newHeight = image.getHeight(null);
        			
        			//临时png图片
        			File temPngFile = new File(pngFile);
        			orgCharacteristicUrl = pngFile.replace("_8bit_temp", "_8bit");
        			//图片放大
        			ImageUtil.resizePngPicture(temPngFile,new File(orgCharacteristicUrl), newWidth,newHeight, 1L);
        			//删除临时图片
        			if(temPngFile.exists()){
        				temPngFile.delete();
        			}
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
            }
            if(orgCharacteristicUrl !=null && !"".equals(orgCharacteristicUrl)){
            	int index = orgCharacteristicUrl.lastIndexOf("coverpng");
            	orgCharacteristicUrl = orgCharacteristicUrl.substring(index-1);
            	orgCharacteristicUrl = orgCharacteristicUrl.replace("\\", "/");
            }
    		//插入特征图片png
            vlprVfmTask.setFeatureImagePath(orgCharacteristicUrl);  		
    		
    		//插入车牌位置
    		Pager page = getRecognitionResult(vlprTaskId);
    		if(page.getTotal()>0){
    			ResultVo result = (ResultVo) page.getRows().get(0);
    			vlprVfmTask.setPlateLeft(result.getLocationLeft());
    			vlprVfmTask.setPlateTop(result.getLocationTop());
    			vlprVfmTask.setPlateRight(result.getLocationRight());
    			vlprVfmTask.setPlateBottom(result.getLocationBottom());
    		}
    		
    		
    	}
    	  	
    	vlprVfmTaskDao.save(vlprVfmTask);
    	
    	return vlprVfmTask.getTaskID();
    }
    
    /**
     * 根据任务id，查询识别任务
     */
    public VlprTask getVlprTask(long vlprTaskId){
    	VlprTask vlprTask = vlprTaskDao.findByProperty("taskId", vlprTaskId);
    	return vlprTask;
    }
    
    
    @Override
	public Pager querylist(Integer surveillanceTaskId, Integer page, Integer rows){
		return vlprVfmResultDao.querylist(surveillanceTaskId, page, rows);
	}

	@Override
	public Pager originalquerylist(Integer taskID, Integer page, Integer rows){
		return vlprVfmResultDao.originalquerylist(taskID, page, rows);
	}

	@Override
	public Pager originalquerylisttask(Integer taskID, Integer page, Integer rows){
		return vlprVfmResultDao.originalquerylisttask(taskID, page, rows);
	}

	@Override
	public Pager originalquerylisttaskid(Integer taskID, Integer page, Integer rows){
		return vlprVfmResultDao.originalquerylisttaskid(taskID, page, rows);
	}
    
	/**
	 * 查询以图搜车任务
	 * @param vlprVfmTask
	 * @param page
	 * @param rows
	 * @return Pager
	 */
	@Override
	public Pager getVlprVfmTask(VlprVfmTask vlprVfmTask,Integer page, Integer rows){
		if(vlprVfmTask.getPlateType() == -1){
    		vlprVfmTask.setPlateType(null);
    	}
    	if(StringUtils.isEmpty(vlprVfmTask.getCameraId())){
    		vlprVfmTask.setCameraId(null);
    	}
    	if(StringUtils.isEmpty(vlprVfmTask.getVehicleColor())){
    		vlprVfmTask.setVehicleColor(null);
    	}
    	if(StringUtils.isEmpty(vlprVfmTask.getVehicleKind())){
			vlprVfmTask.setVehicleKind(null);		
		}
		if(StringUtils.isEmpty(vlprVfmTask.getVehicleBrand())){
			vlprVfmTask.setVehicleBrand(null);		
		}
		if(StringUtils.isEmpty(vlprVfmTask.getVehicleSeries())){
			vlprVfmTask.setVehicleSeries(null);
		}
		if(StringUtils.isEmpty(vlprVfmTask.getVehicleStyle())){
			vlprVfmTask.setVehicleStyle(null);
		}
		if(StringUtils.isEmpty(vlprVfmTask.getLicense())){
			vlprVfmTask.setLicense(null);
		}
		Pager result=vlprVfmTaskDao.getVlprVfmTask(vlprVfmTask, page, rows);
		return result;
	}
	
	/**
     * 保存以图搜车任务(手动)
     * @param vlprVfmTask
     */
    @Override
    public Long saveManualVlprVfmTask(Integer dsId,String plateFollowarea,String featurFollowarea,Integer width,Integer height,VlprVfmTask vlprVfmTask){
    	
    	if(vlprVfmTask.getPlateType() == -1){
    		vlprVfmTask.setPlateType(null);
    	}
    	if("0002-11-30 00:00:00.0".equals(vlprVfmTask.getPassStartTime().toString())){
    		vlprVfmTask.setPassStartTime(null);
    	}
    	if("0002-11-30 00:00:00.0".equals(vlprVfmTask.getPassEndTime().toString())){
    		vlprVfmTask.setPassEndTime(null);
    	}
    	if(StringUtils.isEmpty(vlprVfmTask.getCameraId())){
    		vlprVfmTask.setCameraId(null);
    	}
    	if(StringUtils.isEmpty(vlprVfmTask.getVehicleColor())){
    		vlprVfmTask.setVehicleColor(null);
    	}
    	if(StringUtils.isEmpty(vlprVfmTask.getVehicleKind())){
			vlprVfmTask.setVehicleKind(null);		
		}
		if(StringUtils.isEmpty(vlprVfmTask.getVehicleBrand())){
			vlprVfmTask.setVehicleBrand(null);		
		}
		if(StringUtils.isEmpty(vlprVfmTask.getVehicleSeries())){
			vlprVfmTask.setVehicleSeries(null);
		}
		if(StringUtils.isEmpty(vlprVfmTask.getVehicleStyle())){
			vlprVfmTask.setVehicleStyle(null);
		}
		if(StringUtils.isEmpty(vlprVfmTask.getLicense())){
			vlprVfmTask.setLicense(null);
		}
    	
    	long time = System.currentTimeMillis();
    	Timestamp currentTime = new Timestamp(time);
    	vlprVfmTask.setInsertTime(currentTime);
    	vlprVfmTask.setProgress((short)0);
    	vlprVfmTask.setRetryCount((short)0);
    	vlprVfmTask.setStatus(Constant.VLPRTASK_STATUS_WAIT);
    	vlprVfmTask.setFlag(Constant.VLPRTASK_FLAG_VALID);
    	vlprVfmTask.setVlprTaskID(null);
    	
    	if(dsId !=null){
    		Datasource resource = resourceDao.find(dsId);
    		//目标图片路径
            String orgImagePath = resource.getFilePath();   		    		
            //保存目标图片路径
    		vlprVfmTask.setDesImagePath(orgImagePath);
 		
    		
    		/**
    		 * 生成8位的特征图片png
    		 */
    		TaskObj taskObj = new TaskObj();
    		taskObj.setAreaType(new Integer(1).shortValue());
    		taskObj.setWidth(width);
    		taskObj.setHeight(height);
    		//缩小的特征图片路径
    		String characteristicUrl = getCharacteristicUrl(featurFollowarea, taskObj);
    		//原图片大小的特征图片
    		String orgCharacteristicUrl = "";
    		
    		//缩放比率
    		double rate = 1;
            if (null != characteristicUrl && !"".endsWith(characteristicUrl)) {
            	//原图片地址
                String orgFile = orgImagePath;
                //png图片地址（遮罩）
                String pngFile = characteristicUrl;
        		try {
        			ImageIcon imageIcon = new ImageIcon(new File(orgFile).getCanonicalPath());
        			Image image = imageIcon.getImage();
        			
        			//原始图片宽度
        			int newWidth = image.getWidth(null);
        			//原始图片高度
        			int newHeight = image.getHeight(null);
        			
        			if (newWidth > newHeight) {
        				rate = newWidth / 600.0;
        			} else {
        				rate = newHeight / 600.0;
        			}
        			
        			//临时png图片
        			File temPngFile = new File(pngFile);
        			orgCharacteristicUrl = pngFile.replace("_8bit_temp", "_8bit");
        			//图片放大
        			ImageUtil.resizePngPicture(temPngFile,new File(orgCharacteristicUrl), newWidth,newHeight, 1L);
        			//删除临时图片
        			if(temPngFile.exists()){
        				temPngFile.delete();
        			}
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
            }
            if(orgCharacteristicUrl !=null && !"".equals(orgCharacteristicUrl)){
            	int index = orgCharacteristicUrl.lastIndexOf("coverpng");
            	orgCharacteristicUrl = orgCharacteristicUrl.substring(index-1);
            	orgCharacteristicUrl = orgCharacteristicUrl.replace("\\", "/");
            }
    		//插入特征图片png
            vlprVfmTask.setFeatureImagePath(orgCharacteristicUrl);  		
    		
    		//插入车牌位置
            int[] postion = getPlatePostion(plateFollowarea);
    		vlprVfmTask.setPlateLeft((short)(postion[0]*rate));
    		vlprVfmTask.setPlateTop((short)(postion[1]*rate));
    		vlprVfmTask.setPlateRight((short)(postion[2]*rate));
    		vlprVfmTask.setPlateBottom((short)(postion[3]*rate));
    		
    		
    	}
    	  	
    	vlprVfmTaskDao.save(vlprVfmTask);
    	
    	return vlprVfmTask.getTaskID();
    }
    
    /**
     * 根据车牌画图区获取车牌长方形坐标
     * int[0]=x1,int[1]=y1,int[2]=x2,int[3]=y2
     * @param plateFollowarea
     * @return int[]
     */
    private int[] getPlatePostion(String plateFollowarea){
    	int [] postion = new int[4];
    	
        if (StringUtils.isEmpty(plateFollowarea)) {
            return postion;
        }
        //填充二维数组,#是多边形分隔符,|是点分隔符
        //126,84|331,76|296,270|164,257#410,94|535,109|520,293|349,294
        String[] polygonArr = plateFollowarea.split("#");
        int polygonLen = polygonArr.length;
        PolygonPoint[][] polygons = new PolygonPoint[polygonLen][];
        for (int i = 0; i < polygonLen; i++) {
            String pointsStr = polygonArr[i];
            String[] pointsArr = pointsStr.split("\\|");
            int pointsLen = pointsArr.length;
            polygons[i] = new PolygonPoint[pointsLen];
            for (int j = 0; j < pointsLen; j++) {
                String xyStr = pointsArr[j];
                String[] point = xyStr.split(",");
                polygons[i][j] = new PolygonPoint(point[0], point[1]);
            }
        }
        
        for(int i =0;i<polygons.length;i++){
        	for(int j=0;j<polygons[i].length;j++){      		
        		PolygonPoint point = polygons[i][j];
        		if(i==0&&j==0){
        			postion[0] = point.getX();
        			postion[1] = point.getY();
        			postion[2] = point.getX();
        			postion[3] = point.getY();       			
        		}else {
            		if(point.getX()<postion[0]){
            			postion[0] = point.getX();
            		}
            		if(point.getY()<postion[1]){
            			postion[1] = point.getY();
            		}
            		if(point.getX()>postion[2]){
            			postion[2] = point.getX();
            		}
            		if(point.getY()>postion[3]){
            			postion[3] = point.getY();
            		}
        		}
        		
        	}
        }
    	
    	return postion;
    }
    
    /**
	 * 停止以图搜车任务
	 * @param taskId
	 * @return int
	 */
	@Override
	public int stopVlprVfmTask(Long taskId){
		int updateItemNum = vlprVfmTaskDao.stopVlprVfmTask(taskId);
		return updateItemNum;	
	}
    
}
