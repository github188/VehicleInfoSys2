package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprResult;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;
import cn.jiuling.vehicleinfosys2.vo.TravelTogetherVehicleQuery;

import java.util.List;

public interface UnlicensedcarDao extends BaseDao<VlprResult> {
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

}
