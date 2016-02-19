package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.TblVehicleRecordDao;
import cn.jiuling.vehicleinfosys2.service.TblVehicleRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@SuppressWarnings( { "unused", "unchecked" })
@Service("tblVehicleRecordService")
public class TblVehicleRecordServiceImpl implements TblVehicleRecordService {
    @Resource
    private TblVehicleRecordDao tblVehicleRecordDao;

    @Override
    public List findTblVehicleRecordVo() {
        return tblVehicleRecordDao.findTblVehicleRecordVo();
    }

    @Override
    public List findTblVehicleRecordVo(String tollgateCode, Long maxRecordId, Date startTime, Date endTime) {
        return tblVehicleRecordDao.findTblVehicleRecordVo(tollgateCode, maxRecordId, startTime, endTime);
    }

}
