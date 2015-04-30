package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprResult;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;

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

}
