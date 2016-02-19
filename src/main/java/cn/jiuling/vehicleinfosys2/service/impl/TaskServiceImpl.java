//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.CameraDao;
import cn.jiuling.vehicleinfosys2.dao.ResourceDao;
import cn.jiuling.vehicleinfosys2.dao.TaskDao;
import cn.jiuling.vehicleinfosys2.dao.VlprTaskDao;
import cn.jiuling.vehicleinfosys2.model.Camera;
import cn.jiuling.vehicleinfosys2.model.Datasource;
import cn.jiuling.vehicleinfosys2.model.Task;
import cn.jiuling.vehicleinfosys2.model.VlprTask;
import cn.jiuling.vehicleinfosys2.service.CameraService;
import cn.jiuling.vehicleinfosys2.service.ResourceService;
import cn.jiuling.vehicleinfosys2.service.TaskService;
import cn.jiuling.vehicleinfosys2.util.ImageUtil;
import cn.jiuling.vehicleinfosys2.util.PathUtils;
import cn.jiuling.vehicleinfosys2.vo.Constant;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.PolygonPoint;
import cn.jiuling.vehicleinfosys2.vo.TaskObj;
import cn.jiuling.vehicleinfosys2.vo.TaskQuery;
import cn.jiuling.vehicleinfosys2.vo.TaskVo;

import java.io.File;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service("taskService")
public class TaskServiceImpl implements TaskService {
    private static final Logger log = Logger.getLogger(TaskServiceImpl.class);
    @Resource
    private TaskDao taskDao;
    @Resource
    private VlprTaskDao vlprTaskDao;
    @Resource
    private CameraDao cameraDao;
    @Resource
    private ResourceDao resourceDao;
    @Resource
    private CameraService cameraService;
    @Resource
    private ResourceService resourceService;

    public TaskServiceImpl() {
    }

    public void delete(Integer[] taskIds) {
        Integer[] arr = taskIds;
        int len = taskIds.length;

        for(int i = 0; i < len; ++i) {
            Integer id = arr[i];
            Task t = (Task)taskDao.find(id);
            taskDao.delete(t);
            Long vId = t.getVlprTaskId();
            VlprTask vlprTask = (VlprTask)vlprTaskDao.find(vId);
            vlprTaskDao.delete(vlprTask);
        }

    }

    public Pager list(Task task, Integer page, Integer rows) {
        return taskDao.list(task, page, rows);
    }

    public Task findLastRealTimeTask(Integer cameraId) {
        VlprTask vlprTask = vlprTaskDao.findStartedRealTimeTask(cameraId);
        return null == vlprTask?null:(Task)taskDao.findByProperty("vlprTaskId", vlprTask.getTaskId());
    }

    @Override
    public Task findLastRealImageTask(Integer cameraId) {
        VlprTask vlprTask = vlprTaskDao.findStartedRealImageTask(cameraId);
        return null == vlprTask?null:(Task)taskDao.findByProperty("vlprTaskId", vlprTask.getTaskId());
    }

    public void startOffLineTask(Integer[] ids, TaskObj taskObj) {
        Integer[] arr = ids;
        int len = ids.length;

        for(int i = 0; i < len; ++i) {
            Integer resourceId = arr[i];
            Datasource resource = (Datasource)resourceDao.find(resourceId);
            String filePath = "file://" + resource.getFilePath();
            startTask(resource.getCameraId(), taskObj.getType(), filePath, resourceId, taskObj);
        }

    }

    public Integer startRealTimeTask(TaskObj taskObj) {
        Integer cameraId = taskObj.getId();
        Task task = findLastRealTimeTask(cameraId);
        if(null != task) {
            return null;
        } else {
            Integer taskId = startTask(cameraId, Constant.TASK_TYPE_ONLINE, null, null, taskObj);
            return taskId;
        }
    }

    public void startRealImageTask(Integer[] ids, TaskObj taskObj) {
        String filePath = PathUtils.getVlprDataSrcPath() + File.separator + taskObj.getId() + File.separator + "0" + File.separator + "todo";
        File file = new File(filePath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        filePath = "picstream://" + filePath;
        startTask(taskObj.getId(), taskObj.getType(), filePath, null, taskObj);
    }

    private Integer startTask(Integer cameraId, Short type, String filePath, Integer resourceId, TaskObj taskObj) {
        long currentTimeMillis = System.currentTimeMillis();
        Timestamp createTime = new Timestamp(currentTimeMillis);
        Camera c = cameraDao.find(cameraId);
        VlprTask vt = new VlprTask();
        vt.setStatus(Constant.VLPRTASK_STATUS_WAIT);
        vt.setVideoId(Long.valueOf(currentTimeMillis));
        vt.setCreateTime(createTime);
        vt.setFlag(Constant.VLPRTASK_FLAG_VALID);
        String followarea = taskObj.getFollowarea();
        if(type.equals(Constant.TASK_TYPE_ONLINE)) {
            filePath = cameraService.getFilepath(c);
            c.setFollowarea(followarea);
            cameraService.update(c);
        }

        if(type.equals(Constant.TASK_TYPE_OFFLINE)) {
            Datasource d = resourceDao.find(resourceId);
            d.setFollowarea(followarea);
            resourceDao.update(d);
        }

        if(type.equals(Constant.TASK_TYPE_REALIMAGE)) {
            String vlprDataSrcHisPath = PathUtils.getVlprDataSrcHisPath() + File.separator + taskObj.getId() + File.separator + "0";
            File file = new File(vlprDataSrcHisPath);
            if(!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
            vt.setPicstreamHisPath(vlprDataSrcHisPath);
        }

        String resultPath = PathUtils.getResultPath();
        /**
         * 为从服务器设置共享路径
         */
        //获取本机ip
        /*try {
			String ip = InetAddress.getLocalHost().getHostAddress();
			resultPath = resultPath.replace(resultPath.substring(0, 2),"//"+ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} */            
        vt.setVlprResultPath(resultPath);
        vt.setFilePath(StringUtils.isEmpty(filePath)?"":filePath);
        vt.setProgress(Short.valueOf((short)0));
        vt.setRecognitionSlaveIp("");
        vt.setRetryCount(Short.valueOf((short)0));
        vt.setZoomWidth(taskObj.getZoomWidth());
        vt.setZoomHeight(taskObj.getZoomHeight());
        vt.setDropFrame(taskObj.getDropFrame());
        vt.setDetectMode(taskObj.getDetectMode());
        vt.setMinimumWidth(taskObj.getMinimumWidth());
        vt.setMaximumHeight(taskObj.getMaximumHeight());
        if(taskObj.getVedioDetectMode()!=null && 1==taskObj.getVedioDetectMode()){
        	vt.setVedioDetectMode(taskObj.getVedioDetectMode());
        }else{
        	vt.setVedioDetectMode((short)0);
        }
        String coverUrl1 = getCoverUrl(followarea, taskObj);
        vt.setCoverUrl(coverUrl1);
        String dataFormatTemp = taskObj.getDateFormatTemp();
        if(dataFormatTemp !=null && !"".equals(dataFormatTemp)) {
            vt.setDateFormatTemp(dataFormatTemp);
        }
        vlprTaskDao.save(vt);
        Task t = new Task();
        t.setName(c.getName() + "_" + createTime.toString().replaceAll("\\..*", ""));
        t.setCameraId(cameraId);
        t.setCreateTime(createTime);
        t.setType(type);
        t.setStatus(Constant.TASK_STATUS_UNFINISHED);
        t.setStartTime(createTime);
        t.setVlprTaskId(vt.getTaskId());
        t.setDataSourceId(resourceId);
        taskDao.save(t);
        return t.getId();
    }

    private String getCoverUrl(String followArea, TaskObj taskObj) {
        if(StringUtils.isEmpty(followArea)) {
            return "";
        } else {
            String[] polygonArr = followArea.split("#");
            int polygonLen = polygonArr.length;
            PolygonPoint[][] polygons = new PolygonPoint[polygonLen][];

            String coverPngDir;
            for(int fileName = 0; fileName < polygonLen; ++fileName) {
                coverPngDir = polygonArr[fileName];
                String[] coverPngAbsoluteDir = coverPngDir.split("\\|");
                int file = coverPngAbsoluteDir.length;
                polygons[fileName] = new PolygonPoint[file];

                for(int pngPath = 0; pngPath < file; ++pngPath) {
                    String xyStr = coverPngAbsoluteDir[pngPath];
                    String[] point = xyStr.split(",");
                    polygons[fileName][pngPath] = new PolygonPoint(point[0], point[1]);
                }
            }

            String fileName = resourceService.createFileName("test.png");
            coverPngDir = PathUtils.getCoverPngPath();
            String rootPath = PathUtils.getRealPath("/") + File.separator + coverPngDir + File.separator;
            File file = new File(rootPath);
            if(!file.exists()) {
                file.mkdir();
            }

            String filePath = rootPath + fileName;
            ImageUtil.generatePNG(taskObj.getAreaType(), taskObj.getWidth().intValue(), taskObj.getHeight().intValue(), polygons, filePath);
            return "/" + coverPngDir + "/" + fileName;
        }
    }

    public Timestamp stopRealTimeTask(Integer taskId) {
        Task task = taskDao.find(taskId);
        if(null != task) {
            if(task.getStatus().shortValue() != 1) {
                return task.getEndTime();
            }

            taskDao.stopSingleTask(task.getVlprTaskId());
        }

        return null;
    }

    public Pager query(TaskQuery taskQuery, Integer page, Integer rows) {
        Pager pager = taskDao.query(taskQuery, page, rows);
        List list = pager.getRows();
        ArrayList ids = new ArrayList();
        HashMap taskVoMap = new HashMap();
        new StringBuilder();
        Iterator task_result = list.iterator();

        TaskVo taskVo;
        while(task_result.hasNext()) {
            Object i = task_result.next();
            taskVo = (TaskVo)i;
            ids.add(taskVo.getId());
            taskVoMap.put(taskVo.getId(), taskVo);
        }

        if(ids.size() == 0) {
            return pager;
        } else {
            List listVO = taskDao.queryByIds(ids);

            for(int i = 0; i < listVO.size(); ++i) {
                Object[] objs = (Object[])listVO.get(i);
                taskVo = (TaskVo)taskVoMap.get(objs[0]);
                //设置已识别车辆数
                taskVo.setCount(Long.valueOf(((BigInteger)objs[1]).longValue()));
            }

            return pager;
        }
    }

    public Object addBatTask(MultipartFile[] files, Integer cId, Short dataType, String type, Integer parentId) {
        int data = 0;

        while(data < files.length) {
            MultipartFile e = files[data];
            log.info("正在上传的文件：" + e.getOriginalFilename());
            if(e != null && !e.isEmpty()) {
                if(!dataType.equals(Constant.DATASOURCE_TYPE_PICTURE) || !type.contains("video") && !type.contains("stream")) {
                    if(dataType.equals(Constant.DATASOURCE_TYPE_VIDEO) && type.contains("image")) {
                        return "typeError";
                    }

                    ++data;
                    continue;
                }

                return "typeError";
            }

            return "fail";
        }

        log.info("保存上传文件");

        try {
            Datasource d = resourceDao.find(parentId);
            MultipartFile file = files[0];
            String path = PathUtils.getUploadPath() + "/" + d.getName();
            Datasource d1 = resourceService.saveFile(file, type, path);
            d1.setCameraId(cId);
            d1.setType(dataType);
            d1.setParentId(parentId);
            resourceDao.save(d1);
            return "success";
        } catch (Exception e) {
            log.error("文件保存错误!!", e);
            return "saveError";
        }
    }
}
