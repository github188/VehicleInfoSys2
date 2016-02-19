package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.VlprResult;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;
import cn.jiuling.vehicleinfosys2.vo.TravelTogetherVehicleQuery;

import java.util.List;

public interface ResultService {

	public Pager list(ResultQuery rq, Integer page, Integer rows);

	public ResultVo findById(Long id);

	public Pager listByTaskId(Integer taskId, Integer page, Integer rows);

	/**
	 * 查询识别结果
	 * 
	 * @param rq
	 * @param page
	 * @param rows
	 * @return 返回页面对象
	 */
	public Pager query(ResultQuery rq, Integer page, Integer rows);
	
	public Pager list(VlprResult vr,Integer page, Integer rows);

	/**
	 * 无分页查询
	 * @param rq
	 * @return Pager
	 */
	public Pager queryMunityResult(final ResultQuery rq);

	String queryMunityResult1(ResultQuery rq);

    /**
     * 同行车所有信息
     * @param travelTogetherVehicleQuery
     * @return
     */
    Pager queryByTravelTogetherVehicle(TravelTogetherVehicleQuery travelTogetherVehicleQuery);

    /**
     * 查找大于当前时间的识别结果
     */
    List queryByNow();

    void addToChangHai(ResultVo r);

    /**
     * 查找同行车
     * @param resultVos
     * @param timeInterval
     * @param locations
     * @param page
     * @param rows
     * @return
     */
    Pager queryByTravelTogetherVehicle(ResultVo[] resultVos, Long timeInterval, Integer locations, Integer page, Integer rows);

    Pager queryMunityResult2(ResultQuery rq);
}
