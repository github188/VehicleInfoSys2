package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.FakeLicenseCarInfo;
import cn.jiuling.vehicleinfosys2.vo.FakeLicenseQuery;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * 
 * 假套牌车查询DAO
 * 
 * @author daixiaowei
 *
 */
public interface FakeLicenseDao extends BaseDao<FakeLicenseCarInfo>{
	
	/**
	 * 套牌车实时报警分页查询假套牌车辆信息
	 * 
	 * @param page
	 * @param rows
	 * @return Pager
	 */
	public Pager realTimelist(FakeLicenseQuery query,Integer page, Integer rows);
	
	/**
	 * 查询假套牌车辆信息(查询页面)
	 * 
	 * @param query
	 * @param page
	 * @param rows
	 * @return Pager
	 */
	public Pager queryFakeLicenseCarInfolist(FakeLicenseQuery query, Integer page, Integer rows);
	
	/**
	 * 查询最大id
	 * 
	 * @return Long
	 */
	public Long queryMaxId();
}
