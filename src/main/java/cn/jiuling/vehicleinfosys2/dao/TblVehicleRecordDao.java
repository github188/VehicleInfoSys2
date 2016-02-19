package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.TblVehicleRecord;
import cn.jiuling.vehicleinfosys2.vo.TblVehicleRecordVo;

import java.util.Date;
import java.util.List;

/**
 * 过车信息Dao(吉林白山)
 * Created by wangrb on 2015/10/16.
 */
public interface TblVehicleRecordDao extends BaseDao<TblVehicleRecord> {

    public List<TblVehicleRecordVo> findTblVehicleRecordVo();

    /**
     * 查询过程信息
     * @param tollgateCode 卡口
     * @param startTime  起始时间（过车时间范围条件）
     * @param endTime   起始时间（过车时间范围条件）
     * @return
     */
    public List<TblVehicleRecordVo> findTblVehicleRecordVo(String tollgateCode, Long maxRecordId, Date startTime, Date endTime);

}
