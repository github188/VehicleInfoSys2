package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.Bayonet_VehiclePassDao;
import cn.jiuling.vehicleinfosys2.model.Bayonet_VehiclePass;
import cn.jiuling.vehicleinfosys2.util.DateUtils;
import cn.jiuling.vehicleinfosys2.vo.Server_VehiclePassVo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/8/3.
 */
@Repository("bayonet_VehiclePassDao")
public class Bayonet_VehiclePassDaoImpl extends BaseDaoImpl<Bayonet_VehiclePass> implements Bayonet_VehiclePassDao {

    private static Logger log = Logger.getLogger(Bayonet_VehiclePassDaoImpl.class);

    /**
     * 刚开始执行任务先用当前时间作为筛选条件
     */
    public static Integer vehicleLsh = 0;

    @Override
    public List<Bayonet_VehiclePass> findByNow() {
        StringBuilder sb = new StringBuilder("from Bayonet_VehiclePass b where b.passTime > to_date( '" + DateUtils.formateTime(new Date()) + "','yyyy-MM-dd HH24:mi:ss') order by b.passTime desc");
        List list = super.getHibernateTemplate().find(sb.toString());
        return list;
    }

    @Override
    public List findServerVehicleVO() {
        // Integer vehicleLsh, Integer crossLsh, String deviceIndex, String vehicleIndex, String directIndex,
        // String plateInfo, Integer plateType, Date passTime, Integer vehicleSpeed, Integer vehicleLen,
        // Integer plateColor, String picFeature, String picFullView, String picFTPPath, Integer downLoadFlag,
        // String picLocalPath, Integer drivewayNumber, Integer vehicleType, Integer vehicleColor,
        // Integer vehicleState, Integer vehColorDepth, String recordID, Integer sendFlag,
        // Integer vrplsh, Integer server_id, String name, Integer control_unit_id, Integer server_type,
        // String ip_addr, String index_code, Integer hpp_port, Integer share_flag,
        // Integer net_zone_id, Integer serverRes1, String serverRes2
        StringBuilder sb = new StringBuilder("select new cn.jiuling.vehicleinfosys2.vo.Server_VehiclePassVo(b.vehicleLsh,b.crossLsh,b.deviceIndex,b.vehicleIndex,b.directIndex," +
                "b.plateInfo,b.plateType,b.passTime,b.vehicleSpeed,b.vehicleLen," +
                "b.plateColor,b.picFeature,b.picFullView,b.picFTPPath,b.downLoadFlag," +
                "b.picLocalPath,b.drivewayNumber,b.vehicleType,b.vehicleColor," +
                "b.vehicleState,b.vehColorDepth,b.recordID,b.sendFlag," +
                "b.vrplsh,s.server_id,s.name,s.control_unit_id,s.server_type, " +
                "s.ip_addr,s.index_code,s.hpp_port,s.share_flag," +
                "s.net_zone_id,s.serverRes1,s.serverRes2) from Server_Info s,Bayonet_VehiclePass b " +
                "where 1=1 and b.vrplsh=s.server_id and b.passTime >=to_timestamp('" + DateUtils.formateOracleTime(new Date()) + "','yyyy-MM-dd HH24:mi:ss:ff') and b.vehicleLsh > " + vehicleLsh + " order by b.vehicleLsh desc");
        List listVO = super.getHibernateTemplate().find(sb.toString());
        if (listVO.size() == 0) {
            log.info(DateUtils.formateTime(new Date()) + ":=================当前时间图片服务器没有新图片！===========================");
        } else {
            Server_VehiclePassVo sv = (Server_VehiclePassVo) listVO.get(0);
            //将筛选条件更改为最后过车时间
            vehicleLsh = sv.getVehicleLsh();
        }
        return listVO;
    }
}
