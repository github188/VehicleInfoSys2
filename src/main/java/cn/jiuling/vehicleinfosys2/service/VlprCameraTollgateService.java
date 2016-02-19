package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.TSSupportVideoType;
import cn.jiuling.vehicleinfosys2.model.VlprCameraTollgate;

import java.util.List;

/**
 * 监控点与卡口关系Serveice
 * Created by wangrb on 2015/10/19.
 */
public interface VlprCameraTollgateService {
    List getAll();

    /**
     * 通过监控点id获取VlprCameraTollgate
     * @param cameraId 监控点id
     * @return
     */
    public VlprCameraTollgate findByCameraId(Integer cameraId);

    /**
     * 通过监控点id获取卡口号集合
     * @param cameraIds 监控点id
     * @return
     */
    public List queryTollgateCodesByCameraIds(Integer cameraIds[]);
}
