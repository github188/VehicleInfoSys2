package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprCollectPictures;
import cn.jiuling.vehicleinfosys2.vo.CollectPicturesVo;
import cn.jiuling.vehicleinfosys2.vo.Pager;

import java.util.List;

/**
 * 采集图片任务Dao
 *
 * @author wangrb
 * @date 2015-11-25
 */
public interface CollectPicturesDao extends BaseDao<VlprCollectPictures> {

	/**
	 * 分页查找采集图片任务信息
	 * @param cpv  条件对象
	 * @param page 当前页
	 * @param rows 页面大小数
	 * @return
	 */
	public Pager list(CollectPicturesVo cpv,Integer page, Integer rows);

	/**
	 * 查询所有采集图片任务信息
	 * @param status 状态
	 * @return
	 */
	public List list(Short status);

	/**
	 * 查询所有采集图片任务信息
	 * @param status 状态（支持多种状态查询）
	 * @return
	 */
	public List list(Short status[]);

	/**
	 * 实时更新采集图片任务信息
	 * @param vcp
	 */
	public void updateRealCollectPictrue(VlprCollectPictures vcp);

}
