package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.TblVehicleRecordDao;
import cn.jiuling.vehicleinfosys2.model.TblVehicleRecord;
import cn.jiuling.vehicleinfosys2.util.DateUtils;
import cn.jiuling.vehicleinfosys2.util.JdbcUtil;
import cn.jiuling.vehicleinfosys2.vo.TblVehicleRecordVo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("tblVehicleRecordDao")
public class TblVehicleRecordDaoImpl extends BaseDaoImpl<TblVehicleRecord> implements TblVehicleRecordDao {

    private static Logger log = Logger.getLogger(TblVehicleRecordDaoImpl.class);
    /**
     * 记录最新一张下载图片的车辆信息编号（过滤已下载过的图片信息)
     */
    public static BigInteger maxRecordId = new BigInteger("0");

    /**
     * 开始进行下载图片的初始时间
     */
    public static String downloadImgStartTime;

    @Override
    public List<TblVehicleRecordVo> findTblVehicleRecordVo() {
        List listVO = new ArrayList();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        if(downloadImgStartTime == null){
            downloadImgStartTime = DateUtils.formateTime(new Date());
        }
        try {
            conn = JdbcUtil.getInstance().getConnection();
            st = conn.createStatement();
            StringBuilder sql = new StringBuilder("select tvr.record_id,tvr.tollgate_code,tvr.pass_time,tpd.dev_addr,tvr.pic1_name" +
                    " from tbl_vehicle_record tvr,tbl_tollgate tt,tbl_phy_dev tpd" +
                    " where tvr.tollgate_code=tt.tollgate_code" +
                    " and tt.tms_code=tpd.dev_code" +
                    " and tvr.record_id > " + maxRecordId );
            sql.append(" and tvr.pass_time >= to_timestamp('" + downloadImgStartTime + "', 'YYYY-MM-DD HH24:MI:SS')");
            sql.append(" order by tvr.record_id desc");
            rs = st.executeQuery(sql.toString());
            while (rs.next()) {
                TblVehicleRecordVo tvrVo = new TblVehicleRecordVo();
                tvrVo.setRecordId(new BigInteger(rs.getString(1)));
                tvrVo.setTollgateCode(rs.getString(2));
                tvrVo.setPassTime(rs.getTimestamp(3));
                tvrVo.setDevAddr(rs.getString(4));
                tvrVo.setPic1Name(rs.getString(5));
                listVO.add(tvrVo);
            }
        }catch (Exception e){
            log.error("==================TblVehicleRecordDaoImpl中读取报错======================");
        }finally {
            JdbcUtil.getInstance().closeConnection(conn, st, rs);
        }

        if (listVO.size() == 0) {
            log.info(DateUtils.formateTime(new Date()) + ":=================当前时间图片服务器没有新图片！===========================");
        } else {
            //获取已下载图片的最大RecordId
            TblVehicleRecordVo tvrVo = (TblVehicleRecordVo) listVO.get(0);
            maxRecordId = tvrVo.getRecordId();
        }
        return listVO;
    }

    @Override
    public List<TblVehicleRecordVo> findTblVehicleRecordVo(String tollgateCode,Long maxRecordId,Date startTime, Date endTime) {
        List listVO = new ArrayList();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtil.getInstance().getConnection();
            st = conn.createStatement();
            StringBuilder sql = new StringBuilder("select tvr.record_id,tvr.tollgate_code,tvr.pass_time,tpd.dev_addr,tvr.pic1_name" +
                    " from tbl_vehicle_record tvr,tbl_tollgate tt,tbl_phy_dev tpd" +
                    " where tvr.tollgate_code=tt.tollgate_code" +
                    " and tt.tms_code=tpd.dev_code");
            if(maxRecordId!=null && maxRecordId>0){
                sql.append(" and tvr.record_id > " + maxRecordId );
            }
            if(startTime!=null){
                sql.append(" and tvr.pass_time >= '"+DateUtils.formateTime(startTime)+"'");
            }
            if(endTime!= null){
                sql.append(" and tvr.pass_time <= '" + DateUtils.formateTime(endTime)+"'");
            }
            if(tollgateCode != null && !"".equals(tollgateCode)){
                sql.append(" and tvr.tollgate_code = '"+tollgateCode+"'");
            }

            sql.append(" order by tvr.record_id limit 20"); //控制每次最多取20条
            rs = st.executeQuery(sql.toString());
            while (rs.next()) {
                TblVehicleRecordVo tvrVo = new TblVehicleRecordVo();
                tvrVo.setRecordId(new BigInteger(rs.getString(1)));
                tvrVo.setTollgateCode(rs.getString(2));
                tvrVo.setPassTime(rs.getTimestamp(3));
                tvrVo.setDevAddr(rs.getString(4));
                tvrVo.setPic1Name(rs.getString(5));
                listVO.add(tvrVo);
            }
        }catch (Exception e){
            log.error("==================TblVehicleRecordDaoImpl中读取报错======================");
        }finally {
            JdbcUtil.getInstance().closeConnection(conn, st, rs);
        }

        if (listVO.size() == 0) {
            log.info(DateUtils.formateTime(new Date()) + ":=================当前时间图片服务器没有新图片！===========================");
        }
        return listVO;
    }

}
