package cn.jiuling.vehicleinfosys2.service;

import java.sql.Timestamp;
import java.util.List;

import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * 频繁过车分析
 * @author daixiaowei
 *
 */
public interface FrequentlyPassVehicleService {
	
	/**
	 * 查询过车列表信息
	 * @param startTime
	 * @param endTime
	 * @param cameraIds
	 * @param frequentlyRate
	 * @return List
	 */
	public List queryPassVehicleList(Timestamp startTime,Timestamp endTime,String cameraIds,Integer frequentlyRate);
	
	/**
   	 * 查询监控点过车列表信息
   	 * @param startTime
   	 * @param endTime
   	 * @param cameraIds
   	 * @param license
   	 * @return List
   	 */
     public Pager queryPassVehicleByCameraList(final Timestamp startTime,final Timestamp endTime,final String cameraIds,final String license,Integer page, Integer rows);
     
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
     public Pager queryPassVehicleByDatilList(final Timestamp startTime,final Timestamp endTime,final String cameraId,final String license,Integer page, Integer rows);
     
     /**
	  * 查询地图信息
	  * @param startTime
	  * @param endTime
	  * @param cameraId
	  * @param license
	  * @return Pager
	  */
	 public Pager queryMapDataList(Timestamp startTime, Timestamp endTime, String cameraId, String license);
}
