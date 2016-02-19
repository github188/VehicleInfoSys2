package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.UserUploadVideo;
import cn.jiuling.vehicleinfosys2.vo.Pager;

import java.math.BigInteger;
import java.util.List;

/**
 *  视频转码Service
 *
 * @author wangrb
 * @version 1.001, 2015-10-09
 */
public interface UserUploadVideoService {

	/**
	 * 批量删除
	 * @param userUploadVideoIds
	 */
	public void delete(BigInteger[] userUploadVideoIds);

	/**
	 * 查询列表
	 * @param userUploadVideo
	 * @param page
	 * @param rows
	 * @return
	 */
	public Pager list(UserUploadVideo userUploadVideo, Integer page, Integer rows);

    /**
     * 查找已完成但未添加到datasource表的任务
     * @return
     */
    List queryUploadVideo();

	/**
	 * 查询列表
	 * @param uuv
	 * @param page
	 * @param rows
	 * @return
	 */
	public Pager queryUploadVideo(UserUploadVideo uuv, Integer page, Integer rows);

    void update(UserUploadVideo u);
}
