package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 违法过车(吉林白山)
 * Created by wangrb on 2015/10/16.
 */
@Entity
@Table(name = "tbl_vehicle_alarm")
public class TblVehicleAlarm implements Serializable {

    private BigInteger alarmId; //    报警信息编号
    private BigInteger recordId; //    车辆信息编号
    private Integer alarmType; //    告警原因类别
    private BigInteger disposeId; //    布控信息编号
    private String disposeType; //    布控类型
    private String tollgateCode; //    卡口编号
    private Integer laneIndex; //    车道编号
    private String laneDir; //    车道方向
    private Date passTime; //    经过时刻
    private Integer plateNumber; //    号牌数量
    private String plateCode; //    号牌号码
    private String plateColor; //    号牌颜色
    private String plateType; //    号牌种类
    private String backendPlateType; //    号牌种类
    private String vehicleBrand; //    车辆品牌
    private Integer vehicleColordepth; //    车身颜色深浅
    private String vehicleColor; //    车身颜色
    private String vehicleFigure; //    车辆外形
    private String vehicleType; //    车辆类型
    private Integer vehicleSpeed; //    车辆速度
    private Integer limitSpeed; //    执法限速
    private String driveStatus; //    行驶状态,即违法类型
    private Date alarmTime; //    报警时刻
    private Integer picNumber; //    图像数量
    private String pic1Name; //    图像1名称
    private String pic2Name; //    图像2名称
    private String pic3Name; //    图像3名称
    private String pic4Name; //    图像4名称
    private String platePic; //    车牌图像
    private String relateVideoAddr; //    关联录像地址
    private Integer dealTag; //    处理标记
    private String sectionCode; //    区间编号
    private String tollgateCode2; //    卡口编号2
    private Integer laneIndex2; //    车道编号2
    private String laneDir2; //    车道方向2
    private Date passTime2; //    通过时间2
    private String placeCode; //    地点编码
    private String equipmentType; //    采集类型
    private String deptCode; //    采集机关编码
    private Date updateTime; //    更新时间
    private Integer combineFlag; //    合并标识

    @Id
    @Column(name = "alarm_id", unique = true, nullable = false)
    public BigInteger getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(BigInteger alarmId) {
        this.alarmId = alarmId;
    }

    @Column(name = "record_id")
    public BigInteger getRecordId() {
        return recordId;
    }

    public void setRecordId(BigInteger recordId) {
        this.recordId = recordId;
    }

    @Column(name = "alarm_type")
    public Integer getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(Integer alarmType) {
        this.alarmType = alarmType;
    }

    @Column(name = "dispose_id")
    public BigInteger getDisposeId() {
        return disposeId;
    }

    public void setDisposeId(BigInteger disposeId) {
        this.disposeId = disposeId;
    }

    @Column(name = "dispose_type")
    public String getDisposeType() {
        return disposeType;
    }

    public void setDisposeType(String disposeType) {
        this.disposeType = disposeType;
    }

    @Column(name = "tollgate_code")
    public String getTollgateCode() {
        return tollgateCode;
    }

    public void setTollgateCode(String tollgateCode) {
        this.tollgateCode = tollgateCode;
    }

    @Column(name = "lane_index")
    public Integer getLaneIndex() {
        return laneIndex;
    }

    public void setLaneIndex(Integer laneIndex) {
        this.laneIndex = laneIndex;
    }

    @Column(name = "lane_dir")
    public String getLaneDir() {
        return laneDir;
    }

    public void setLaneDir(String laneDir) {
        this.laneDir = laneDir;
    }

    @Column(name = "pass_time")
    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    @Column(name = "plate_number")
    public Integer getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(Integer plateNumber) {
        this.plateNumber = plateNumber;
    }

    @Column(name = "plate_code")
    public String getPlateCode() {
        return plateCode;
    }

    public void setPlateCode(String plateCode) {
        this.plateCode = plateCode;
    }

    @Column(name = "plate_color")
    public String getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(String plateColor) {
        this.plateColor = plateColor;
    }

    @Column(name = "plate_type")
    public String getPlateType() {
        return plateType;
    }

    public void setPlateType(String plateType) {
        this.plateType = plateType;
    }

    @Column(name = "backend_plate_type")
    public String getBackendPlateType() {
        return backendPlateType;
    }

    public void setBackendPlateType(String backendPlateType) {
        this.backendPlateType = backendPlateType;
    }

    @Column(name = "vehicle_brand")
    public String getVehicleBrand() {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand) {
        this.vehicleBrand = vehicleBrand;
    }

    @Column(name = "vehicle_colordepth")
    public Integer getVehicleColordepth() {
        return vehicleColordepth;
    }

    public void setVehicleColordepth(Integer vehicleColordepth) {
        this.vehicleColordepth = vehicleColordepth;
    }

    @Column(name = "vehicle_color")
    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    @Column(name = "vehicle_figure")
    public String getVehicleFigure() {
        return vehicleFigure;
    }

    public void setVehicleFigure(String vehicleFigure) {
        this.vehicleFigure = vehicleFigure;
    }

    @Column(name = "vehicle_type")
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Column(name = "vehicle_speed")
    public Integer getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(Integer vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    @Column(name = "limit_speed")
    public Integer getLimitSpeed() {
        return limitSpeed;
    }

    public void setLimitSpeed(Integer limitSpeed) {
        this.limitSpeed = limitSpeed;
    }

    @Column(name = "drive_status")
    public String getDriveStatus() {
        return driveStatus;
    }

    public void setDriveStatus(String driveStatus) {
        this.driveStatus = driveStatus;
    }

    @Column(name = "alarm_time")
    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    @Column(name = "pic_number")
    public Integer getPicNumber() {
        return picNumber;
    }

    public void setPicNumber(Integer picNumber) {
        this.picNumber = picNumber;
    }

    @Column(name = "pic1_name")
    public String getPic1Name() {
        return pic1Name;
    }

    public void setPic1Name(String pic1Name) {
        this.pic1Name = pic1Name;
    }

    @Column(name = "pic2_name")
    public String getPic2Name() {
        return pic2Name;
    }

    public void setPic2Name(String pic2Name) {
        this.pic2Name = pic2Name;
    }

    @Column(name = "pic3_name")
    public String getPic3Name() {
        return pic3Name;
    }

    public void setPic3Name(String pic3Name) {
        this.pic3Name = pic3Name;
    }

    @Column(name = "pic4_name")
    public String getPic4Name() {
        return pic4Name;
    }

    public void setPic4Name(String pic4Name) {
        this.pic4Name = pic4Name;
    }

    @Column(name = "plate_pic")
    public String getPlatePic() {
        return platePic;
    }

    public void setPlatePic(String platePic) {
        this.platePic = platePic;
    }

    @Column(name = "relate_video_addr")
    public String getRelateVideoAddr() {
        return relateVideoAddr;
    }

    public void setRelateVideoAddr(String relateVideoAddr) {
        this.relateVideoAddr = relateVideoAddr;
    }

    @Column(name = "deal_tag")
    public Integer getDealTag() {
        return dealTag;
    }

    public void setDealTag(Integer dealTag) {
        this.dealTag = dealTag;
    }

    @Column(name = "section_code")
    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    @Column(name = "tollgate_code2")
    public String getTollgateCode2() {
        return tollgateCode2;
    }

    public void setTollgateCode2(String tollgateCode2) {
        this.tollgateCode2 = tollgateCode2;
    }

    @Column(name = "lane_index2")
    public Integer getLaneIndex2() {
        return laneIndex2;
    }

    public void setLaneIndex2(Integer laneIndex2) {
        this.laneIndex2 = laneIndex2;
    }

    @Column(name = "lane_dir2")
    public String getLaneDir2() {
        return laneDir2;
    }

    public void setLaneDir2(String laneDir2) {
        this.laneDir2 = laneDir2;
    }

    @Column(name = "pass_time2")
    public Date getPassTime2() {
        return passTime2;
    }

    public void setPassTime2(Date passTime2) {
        this.passTime2 = passTime2;
    }

    @Column(name = "place_code")
    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    @Column(name = "equipment_type")
    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    @Column(name = "dept_code")
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Column(name = "update_time")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "combine_flag")
    public Integer getCombineFlag() {
        return combineFlag;
    }

    public void setCombineFlag(Integer combineFlag) {
        this.combineFlag = combineFlag;
    }

}
