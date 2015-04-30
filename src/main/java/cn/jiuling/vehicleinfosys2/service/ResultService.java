package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;

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
}
