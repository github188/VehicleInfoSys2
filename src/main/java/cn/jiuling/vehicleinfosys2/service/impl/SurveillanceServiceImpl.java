package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.SurveillanceApplicationRecordDao;
import cn.jiuling.vehicleinfosys2.dao.SurveillancePositionDao;
import cn.jiuling.vehicleinfosys2.dao.SurveillanceTaskDao;
import cn.jiuling.vehicleinfosys2.dao.SurveillanceresultDao;
import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.model.SurveillanceApplicationRecord;
import cn.jiuling.vehicleinfosys2.model.SurveillancePosition;
import cn.jiuling.vehicleinfosys2.model.SurveillanceTask;
import cn.jiuling.vehicleinfosys2.service.SurveillanceService;
import cn.jiuling.vehicleinfosys2.vo.Constant;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "unchecked"})
@Service("surveillanService")
public class SurveillanceServiceImpl implements SurveillanceService {
    @Resource
    private SurveillanceTaskDao surveillanceTaskDao;

    @Resource
    private VlprResultDao vlprResultDao;

    @Resource
    private SurveillanceresultDao surveillanceresultDao;
    
    @Resource
    private SurveillanceApplicationRecordDao surveillanceApplicationRecordDao;
    
    @Resource
    private SurveillancePositionDao surveillancePositionDao;

    @Override
    public Pager query(ResultQuery rq, Integer page, Integer rows) {
        return vlprResultDao.query(rq, page, rows);
    }

    @Override
    public Pager list(SurveillanceTask surveillanceTask, Integer page, Integer rows) {
        return surveillanceTaskDao.list(surveillanceTask, page, rows);
    }
    
    @Override
    public Pager queryPracticeTaskList(SurveillanceTask surveillanceTask, Integer page, Integer rows) {  	
        return surveillanceTaskDao.queryPracticeTaskList(surveillanceTask, page, rows);
    }

    @Override
    public void add(SurveillanceTask surveillanceTask) {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        surveillanceTask.setStartTime(startTime);
        //布控任务待审核（状态）
        surveillanceTask.setStatus((short)0);
        surveillanceTaskDao.save(surveillanceTask);
    }
    
    @Override
    public void update(SurveillanceTask surveillanceTask) {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        surveillanceTask.setStartTime(startTime);
        surveillanceTaskDao.update(surveillanceTask);
    }

    @Override
    public Pager querySurveillanceTask(SurveillanceTask surveillanceTask) {
        String plate = surveillanceTask.getPlate();
        String query = " and plate='" + plate + "'";
        surveillanceTask.setPlate(null);
        surveillanceTask.setStatus(Constant.SURVEILLANCE_STATUS_START);
        Pager pager = surveillanceTaskDao.list(surveillanceTask, 1, 1, query);
        surveillanceTask.setPlate(plate);
        return pager;
    }

    @Override
    public void delete(Integer[] ids, Boolean isAllDelete) {
        for (int i = 0; i < ids.length; i++) {
            SurveillanceTask s = surveillanceTaskDao.find(ids[i]);
            surveillanceTaskDao.delete(s, isAllDelete);
        }
        surveillanceresultDao.delete(ids);
    }

    @Override
    public Timestamp stopSurveillanceTask(Integer taskId) {
        SurveillanceTask surveillanceTask = surveillanceTaskDao.find(taskId);
        if (null != surveillanceTask) {
            if (surveillanceTask.getStatus() == 1) {
                surveillanceTaskDao.stopSurveillanceTask(taskId);
            } else {
                return surveillanceTask.getEndTime();
            }
        }
        return null;
    }

    @Override
    public List checkResult(String plates, Long interval) {
        long now = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(now - interval);
        return surveillanceTaskDao.findByPlate(plates, timestamp);
    }

    @Override
    public String findPlates() {
        List list = surveillanceTaskDao.findSurveillancingPlate();
        StringBuilder sb = new StringBuilder();
        for (Object object : list) {
            sb.append("'" + object + "',");
        }
        if (sb.length() > 0) {
            sb = sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public List findSurveillancingTask() {
        List list = surveillanceTaskDao.findSurveillancingTask();
        return list;
    }

    @Override
    public List checkResultByTask(List list, Long interval) {
        long now = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(now - interval);
        List list1 = new ArrayList();
        Pager pager = null;

        for (Object o : list) {
            SurveillanceTask st = (SurveillanceTask) o;
            ResultQuery rq = new ResultQuery();

            rq.setCameraIds(st.getCamera());
            rq.setPlate(st.getPlate());
            if (null != st.getPlateType()) {
            rq.setPlateType(ResultVo.getPlatetypes()[st.getPlateType()]);
            }
            rq.setCarColor(st.getCarcolor());
            rq.setVehicleKind(st.getVehicleKind());
            rq.setVehicleBrand(st.getVehicleBrand());
            rq.setVehicleSeries(st.getVehicleSeries());
            rq.setVehicleStyle(st.getVehicleStyle());
            rq.setTag(st.getTag());
            rq.setPaper(st.getPaper());
            rq.setSun(st.getSun());
            rq.setDrop(st.getDrop());
            rq.setStartTime(timestamp);

            pager = vlprResultDao.querySurveillanceResult(rq);

            int size = pager.getRows().size();
            ResultVo resultVo;
            if (size != 0) {
                for (int i=0;i<size;i++) {
                    resultVo = (ResultVo) pager.getRows().get(i);
                    list1.add(resultVo);
                }
            }
        }
        return list1;
    }
    
    @Override
    public void addSurveillanceApplicationRecord(SurveillanceApplicationRecord surveillanceApplicationRecord) {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        surveillanceApplicationRecord.setRecordTime(startTime);
        surveillanceApplicationRecordDao.save(surveillanceApplicationRecord);
    }
    
    @Override
    public void updateSurveillanceTaskStatus(Integer taskId,Integer status) {
    	surveillanceTaskDao.updateSurveillanceTaskStatus(taskId, status);
    }
    
    @Override
    public Pager querySurveillanceApplicationRecord(SurveillanceApplicationRecord exampleEntity,Integer page, Integer rows){
    	Pager pager =surveillanceApplicationRecordDao.list(exampleEntity, page, rows);
    	return pager;
    }
    
    /**
     * 更新布控时间
     */
    @Override
    public void updateDoTime(Integer taskId,Timestamp doTime){
    	Object[] para = new Object[2];
    	para[0] = doTime;
    	para[1] = taskId;
    	surveillanceTaskDao.updateOrDelete(" update surveillance_task set doTime = ? where id=? ", para);
    }
    
    /**
     * 更新停止布控时间
     */
    @Override
    public void updateEndTime(Integer taskId,Timestamp doTime){
    	Object[] para = new Object[2];
    	para[0] = doTime;
    	para[1] = taskId;
    	surveillanceTaskDao.updateOrDelete(" update surveillance_task set endTime = ? where id=? ", para);
    }
    
    /**
     * 根据任务id获取上次处理的位置
     */
    @Override
    public long getPreviousPosition(Integer id){
    	SurveillancePosition surveillancePosition=surveillancePositionDao.findByProperty("id", id);
    	if(surveillancePosition != null){
    		return surveillancePosition.getThreadPostion();
    	}else{
    		return 0L;
    	}
    }
    
    /**
     * 根据任务id更新或插入本次处理位置
     */
    @Override
    public void saveOrUpdateProcessPosition(Integer id,Long serialNumber){
    	SurveillancePosition instData = new SurveillancePosition();
    	instData.setId(id);
    	instData.setThreadPostion(serialNumber);
    	
    	surveillancePositionDao.saveOrUpdate(instData);
    }
    
    /**
	 * 校验任务名
	 */
    @Override
	public boolean valideName(String taskName){
		Object obj=surveillanceTaskDao.findByProperty("name", taskName);
		return null==obj;
	}
}
