package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprResult;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;

import java.sql.Timestamp;
import java.util.List;

public interface VlprResultDao extends BaseDao<VlprResult> {
	/**
	 * 通过任务id分页查找结果.
	 * 
	 * @param taskId
	 *            任务ID
	 * @param page
	 *            页数
	 * @param rows
	 *            页面大小
	 * @return 指定任务,分页的识别结果对象
	 */
	public Pager findByTaskId(Integer taskId, Integer page, Integer rows);
	
	/**
	 * 通过任务id分页查找结果.(无分页)
	 * @param taskId 任务ID
	 * @return Pager 结果详情
	 */
	public Pager findMunityByTaskId(final Integer taskId);

	/**
	 * 车牌结果查询
	 * 
	 * @param rq
	 *            查询条件
	 * @param page
	 *            页数
	 * @param rows
	 *            页面大小
	 * @return
	 */
	public Pager query(ResultQuery rq, Integer page, Integer rows);

	/**
	 * 查找识别结果
	 * 
	 * @param id
	 * @return 封装的结果视图
	 */
	public ResultVo findResult(Long id);
	
	/**
	 * 
	 * @param ids serialNumber（多个组成的字符串）
	 * @return 结果
	 */
	public Pager queryResults(final String ids);
	
	/**
	 * 查询最大serialNumber
	 */
	public Long queryMaxSeriNumber();

    /**
     * 每次查询rows条
     * @param serialNumber rows
     * @return 10条中最大的serialNumber
     */
	public Long queryMaxSeriNumber(Long serialNumber,Integer rows);

	/**
	 * 获取seriNumber之间的数据
	 */
	public List queryResultBetweenBySeriNumber(Long maxSeriNumber,Long minSeriNumber);
	
	/**
	 * 根据seriNumber查询监控点名称
	 */
	public String queryCameraNameBySeriNumber(Long seriNumber);

	/**
	 * 无分页查询
	 * @param rq
	 * @return Pager
	 */
	public Pager queryMunityResult(final ResultQuery rq);
	
    /**
	 * 查询数据条目数
	 * @param rq
	 * @return Integer
	 */
	public Integer queryMunityResultCount(final ResultQuery rq);
	
	/**
	 * 无分页查询
	 * @param rq
	 * @return Pager
	 */
	public Pager queryMunityResult1(final ResultQuery rq);

    /**
     * 查找同行车
     * @param rq
     * @return
     */
    List queryByTravelTogetherVehicle(ResultQuery rq);

    /**
     * 查找大于当前时间的识别结果
     */
    List queryByNow();

    void addToChangHai(ResultVo r);
    
    /**
   	 * 查询过车列表信息
   	 * @param startTime
   	 * @param endTime
   	 * @param cameraIds
   	 * @param frequentlyRate
   	 * @return List
   	 */
     public List queryPassVehicleList(final Timestamp startTime,final Timestamp endTime,final String cameraIds,final Integer frequentlyRate);
     
     /**
      * 查询监控点过车列表信息
      * @param startTime
      * @param endTime
      * @param cameraIds
      * @param license
      * @return List
     */
      public Pager queryPassVehicleByCameraList(final Timestamp startTime,final Timestamp endTime,final String cameraIds,final String license,Integer page, Integer rows);
      
      public Pager queryMunityResultByFrequentlyPass(final ResultQuery rq);

    /**
	 * 查找昼伏夜出车
	 * @param rq
	 * @param nightAppearNum  夜间出现次数
	 * @param nightStartTime
	 * @param nightEndTime
	 * @return
	 */
	List queryAerialMammalVehicle(ResultQuery rq,Integer nightAppearNum,String nightStartTime,String nightEndTime);

	/**
	 * 查找频繁夜出车辆
	 * @param rq
	 * @param nightAppearNum  夜间出现次数
	 * @param nightStartTime
	 * @param nightEndTime
     * @return
     */
	List queryFrequentNocturnalVehicle(ResultQuery rq,Integer nightAppearNum,String nightStartTime,String nightEndTime);
    
    /**
     * 实时布控查询专用（其他人勿用，用了后果概不负责！！！）
     * @param rq
     * @return
     */
	public Pager querySurveillanceResult(final ResultQuery rq);
    
}
