package cn.jiuling.vehicleinfosys2.service;

import java.util.Date;
import java.util.List;

/**
 * 过车信息Service(吉林白山)
 * Created by wangrb on 2015/10/16.
 */
public interface TblVehicleRecordService {
    List findTblVehicleRecordVo();

    /**
     * 查询过车信息
     * @param tollgateCode 卡口
     * @param maxRecordId  过车id
     * @param startTime
     * @param endTime
     * @return
     */
    List findTblVehicleRecordVo(String tollgateCode, Long maxRecordId, Date startTime, Date endTime);
}
