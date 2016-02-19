package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.VlprCollectPictures;
import cn.jiuling.vehicleinfosys2.vo.CollectPicturesVo;
import cn.jiuling.vehicleinfosys2.vo.Pager;

import java.util.List;

/**
 * 采集图片任务Service
 *
 * @author wangrb
 * @date 2015-11-25
 */
public interface CollectPicturesService {

	/**
	 * 检查监控点名称中是否存在已启动的采集任务
	 * @param cameraNames 监控点名称信息（例如："莲花北,上梅林..."）
	 * @return 返回检查结果（true:通过 false:不通过）
	 */
	public boolean check(String cameraNames);

	/**
	 * 保存
	 * @param collectPicturesVo
	 */
	public void save(CollectPicturesVo collectPicturesVo);

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
	 * 修改
	 * @param vcp
	 */
	public void update(VlprCollectPictures vcp);

	/**
	 * 实时更新采集图片任务信息
	 * @param vcp
	 */
	public void updateRealCollectPictrue(VlprCollectPictures vcp);

	/**
	 * 删除
	 * @param ids 采集图片id
	 */
	public void delete(Long ids[]);

	/**
	 * 开启采集任务
	 * @param ids
	 */
	public void start(Long ids[]);

	/**
	 * 终止/暂停 采集任务
	 * @param ids
	 * @param oper  操作标识（3:暂停 4:终止）
	 */
	public void stopOrPause(Long ids[],Integer oper);

	/**
	 * 通过id查找对象
	 * @param id
	 * @return
	 */
	public VlprCollectPictures findById(Long id);
}
