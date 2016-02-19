package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.VlprCameraTollgate;

import java.util.List;

/**
 * 监控点与卡口关系Dao
 * Created by wangrb on 2015/10/19.
 */
public interface VlprCameraTollgateDao extends BaseDao<VlprCameraTollgate> {

    /**
     * 通过监控点id获取VlprCameraTollgate
     * @param cameraId 监控点id
     * @return
     */
    public VlprCameraTollgate findByCameraId(Integer cameraId);

    /**
     * 通过监控点id获取卡口号
     * @param cameraIds 监控点id
     * @return
     */
    public List queryTollgateCodesByCameraIds(Integer cameraIds[]);
}
