package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.FakeLicenseCarInfo;
import cn.jiuling.vehicleinfosys2.model.VehicleRegistrationGovernment;
import cn.jiuling.vehicleinfosys2.vo.FakeLicenseQuery;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * 
 * 假套牌车service
 * 
 * @author daixiaowei
 *
 */
public interface FakeLicenseService {
	
	/**
	 * 套牌车实时报警分页查询假套牌车辆信息
	 * 
	 * @param page
	 * @param rows
	 * @return list
	 */
	public Pager uploadRealTimelist(FakeLicenseQuery query,Integer page, Integer rows);
	
	/**
	 * 查询假套牌车辆信息(查询页面)
	 * 
	 * @param query
	 * @param page
	 * @param rows
	 * @return Pager
	 */
	public Pager querylist(FakeLicenseQuery query, Integer page, Integer rows);
	
	/**
	 * 查询假套牌车辆信息（框架的原始查询方法）
	 * 
	 * @param query
	 * @param page
	 * @param rows
	 * @return Pager
	 */
	public Pager list(FakeLicenseCarInfo query,Integer page, Integer rows);
	
	/**
	 * 查询车管所车辆信息
	 * 
	 * @param query
	 * @param page
	 * @param rows
	 * @return Pager
	 */
	public Pager queryVehicleRegistration(VehicleRegistrationGovernment query,Integer page, Integer rows);
	
	/**
	 * 更新假套牌车辆信息
	 */
	public void update(FakeLicenseCarInfo updater);
	
	/**
	 * 假套牌车分析业务处理 
	 */
	public void saveFakeLicenseAnalyze();
	
	/**
	 * 是否有最新信息查询
	 */
	public Long uploadDifferenceNumber();
}
