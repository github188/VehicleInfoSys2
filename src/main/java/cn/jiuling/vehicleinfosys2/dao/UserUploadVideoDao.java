package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.UserUploadVideo;
import cn.jiuling.vehicleinfosys2.vo.Pager;

import java.util.List;

/**
 *  视频转码Dao层
 *
 * @author wangrb
 * @version 1.001, 2015-10-09
 */
public interface UserUploadVideoDao extends BaseDao<UserUploadVideo> {

    /**
     * 查找已完成但未添加到datasource表的任务
     * @return
     */
    List queryUploadVideo();

    /**
     * 查询转码任务列表
     * @param uuv
     * @param page
     * @param rows
     * @return
     */
    public Pager queryUploadVideo(UserUploadVideo uuv, Integer page, Integer rows);

}
