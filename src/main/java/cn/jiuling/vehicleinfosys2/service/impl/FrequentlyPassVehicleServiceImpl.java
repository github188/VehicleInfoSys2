package cn.jiuling.vehicleinfosys2.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.service.FrequentlyPassVehicleService;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;

/**
 * 频繁过车分析
 * @author daixiaowei
 *
 */
@Service("frequentlyPassVehicleService")
public class FrequentlyPassVehicleServiceImpl implements FrequentlyPassVehicleService {
	
	private Logger log = Logger.getLogger(FrequentlyPassVehicleServiceImpl.class);
	
	@Resource
	private VlprResultDao vlprResultDao;
	
	/**
	 * 查询过车列表信息
	 * @param startTime
	 * @param endTime
	 * @param cameraIds
	 * @param frequentlyRate
	 * @return List
	 */
	@Override
	public List queryPassVehicleList(Timestamp startTime,Timestamp endTime,String cameraIds,Integer frequentlyRate){
		
		List result = vlprResultDao.queryPassVehicleList(startTime, endTime, cameraIds, frequentlyRate);
		return result;
	}
	
	/**
   	 * 查询监控点过车列表信息
   	 * @param startTime
   	 * @param endTime
   	 * @param cameraIds
   	 * @param license
   	 * @return List
   	 */
	 @Override
     public Pager queryPassVehicleByCameraList(final Timestamp startTime,final Timestamp endTime,final String cameraIds,final String license,Integer page, Integer rows){
		 Pager result = vlprResultDao.queryPassVehicleByCameraList(startTime, endTime, cameraIds, license,page,rows);
			return result;
     }
	 
	 /**
	  * 查询过车详情信息
	  * @param startTime
	  * @param endTime
	  * @param cameraId
	  * @param license
	  * @param page
	  * @param rows
	  * @return
	  */
	 @Override
	 public Pager queryPassVehicleByDatilList(final Timestamp startTime,final Timestamp endTime,final String cameraId,final String license,Integer page, Integer rows){
		 ResultQuery query = new ResultQuery();
		 query.setStartTime(startTime);
		 query.setEndTime(endTime);
		 query.setCameraIds(cameraId);
		 query.setLicense(license);
		 
		 Pager pager = vlprResultDao.query(query, page, rows);
		 return pager;
     }
	 
	 /**
	  * 查询地图信息
	  * @param startTime
	  * @param endTime
	  * @param cameraId
	  * @param license
	  * @return Pager
	  */
	 @Override
	 public Pager queryMapDataList(Timestamp startTime, Timestamp endTime, String cameraId, String license){
		 ResultQuery query = new ResultQuery();
		 query.setStartTime(startTime);
		 query.setEndTime(endTime);
		 query.setCameraIds(cameraId);
		 query.setPlate(license);
		 
		 Pager pager = vlprResultDao.queryMunityResultByFrequentlyPass(query);
		 return pager;
	 }
}
