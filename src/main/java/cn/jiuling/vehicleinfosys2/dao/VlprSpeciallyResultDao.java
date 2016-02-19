package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprSpeciallyResult;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;

public interface VlprSpeciallyResultDao extends BaseDao<VlprSpeciallyResult> {
	/**
	 * 查询以图收车任务图片识别结果
	 * @param rq
	 * @return
	 */
	public Pager querySerachCarByImageResult(final ResultQuery rq);
}
