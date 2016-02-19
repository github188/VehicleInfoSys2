package cn.jiuling.vehicleinfosys2.timerTask;

import cn.jiuling.vehicleinfosys2.dao.SurveillanceresultDao;
import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.model.SurveillanceResult;
import cn.jiuling.vehicleinfosys2.model.SurveillanceTask;
import cn.jiuling.vehicleinfosys2.service.SurveillanceService;
import cn.jiuling.vehicleinfosys2.util.Constant;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 布控定时任务
 * @author baoli
 *
 */
@Component("surveillanceTimerTask")
public class SurveillanceTimerTask {
	
	@Resource
	private SurveillanceService surveillanceService;
	
	@Resource
	private VlprResultDao vlprResultDao;

	@Resource
	private SurveillanceresultDao surveillanceresultDao;

	//记录最大值
	Map<Integer,Long> taskIdMap = new HashMap<Integer,Long>();

	long count = 0;

    @Scheduled(cron = "*/3 * * * * ? ")
	public void realTimeSurveillance(){

        String timerSwitch = PropertiesUtils.get("surveillanceTimerTask");

        if (timerSwitch.equalsIgnoreCase(Constant.TIMER_POWER_ON)) {
            System.out.println("实时布控后台定时任务进行中。。。"+(count=count+1));

            List list = surveillanceService.findSurveillancingTask();
            List list1 = null;
            Pager pager = null;

            for (Object o : list) {
                SurveillanceTask st = (SurveillanceTask) o;

                //布控任务id
                Integer serveillanceId = st.getId();
                if(!taskIdMap.containsKey(serveillanceId)){
                    taskIdMap.put(serveillanceId,0L);
                }

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
                rq.setStartTime(st.getDoTime());
                //获取上传处理位置
                rq.setSerialNumber(surveillanceService.getPreviousPosition(serveillanceId));
                
                //查询最大的serialNumber
                Long maxSeriNumber = vlprResultDao.queryMaxSeriNumber();
                //查询数据
                pager = vlprResultDao.querySurveillanceResult(rq);

                if (pager.getRows().size() > 0) {
                    list1 = pager.getRows();
                    for(Object a : list1){
                        SurveillanceResult sr = new SurveillanceResult();
                        ResultVo vr = (ResultVo)a;
                        sr.setSurveillanceTaskId(st.getId().longValue());
                        sr.setSerialNumber(vr.getSerialNumber());
                        sr.setLicense(vr.getLicense());
                        sr.setLicenseAttribution(vr.getLicenseAttribution());
                        sr.setPlateColor(vr.getPlateColor());
                        sr.setPlateType(vr.getPlateType());
                        sr.setConfidence(vr.getConfidence());
                        Short direction = null;
                        if(vr.getDirection().equals("未知")){
                            direction = 0;
                            sr.setDirection(direction);
                        }else  if(vr.getDirection().equals("向上")){
                            direction = 3;
                            sr.setDirection(direction);
                        }else  if(vr.getDirection().equals("向下")){
                            direction = 4;
                            sr.setDirection(direction);
                        }else  if(vr.getDirection().equals("向左")){
                            direction = 1;
                            sr.setDirection(direction);
                        }else  if(vr.getDirection().equals("向右")){
                            direction = 2;
                            sr.setDirection(direction);
                        }
                        sr.setLocationLeft(vr.getLocationLeft());
                        sr.setLocationTop(vr.getLocationTop());
                        sr.setLocationRight(vr.getLocationRight());
                        sr.setLocationBottom(vr.getLocationBottom());
                        sr.setCarColor(vr.getCarColor());
                        sr.setImageUrl(vr.getImageUrl());
                        sr.setResultTime(vr.getResultTime());
                        sr.setFrame_index(vr.getFrame_index());
                        sr.setVehicleKind(vr.getVehicleKind());
                        sr.setVehicleBrand(vr.getVehicleBrand());
                        sr.setVehicleSeries(vr.getVehicleSeries());
                        sr.setVehicleStyle(vr.getVehicleStyle());
                        sr.setTag(vr.getTag());
                        sr.setPaper(vr.getPaper());
                        sr.setDrop(vr.getDrop());
                        sr.setSun(vr.getSun());
                        sr.setLocation(vr.getLocation());
                        surveillanceresultDao.save(sr);

                        //识别结果id
                        Long serisNum = vr.getSerialNumber();
                        if(serisNum > taskIdMap.get(serveillanceId)){
                            taskIdMap.put(serveillanceId,serisNum);
                        }
                    }                                    
                }               
                //插入本次处理位置
                surveillanceService.saveOrUpdateProcessPosition(serveillanceId, (maxSeriNumber==null?0:maxSeriNumber)>taskIdMap.get(serveillanceId)?maxSeriNumber:taskIdMap.get(serveillanceId));
                                
            }
        }
	}
}
