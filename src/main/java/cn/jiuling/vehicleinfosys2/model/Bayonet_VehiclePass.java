package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 海康 所有过车信息表
 * <p/>
 * Created by Administrator on 2015/7/31.
 */
@Entity
@Table(name = "bayonet_vehiclepass")
public class Bayonet_VehiclePass implements Serializable {
    //流水记录号
    private Integer vehicleLsh;
    //路口流水号
    private Integer crossLsh;
    //路口编号
    private String deviceIndex;

    // 通过路口的车辆序号
    private String vehicleIndex;
    //车辆通过的车道方向编号
    private String directIndex;
    //车牌号码
    private String plateInfo;
    //车牌类型
    private Integer plateType;
    //经过时间
    private Date passTime;
    //车辆速度
    private Integer vehicleSpeed;
    //车外廓长
    private Integer vehicleLen;
    //车牌颜色
    private Integer plateColor;
    //特征图像
    private String picFeature;
    //全景图像
    private String picFullView;
    // FTP路径
    private String picFTPPath;
    //是否下载标志
    private Integer downLoadFlag;
    //车牌小图片URL
    private String picLocalPath;
    //车道编号
    private Integer drivewayNumber;
    // 车辆类型
    private Integer vehicleType;
    // 车辆颜色
    private Integer vehicleColor;
    // 行驶状态
    private Integer vehicleState;
    //车身颜色深浅
    private Integer vehColorDepth;
    //用于级联
    private String recordID;
    //用于级联
    private Integer sendFlag;
    //图片服务器流
    private Integer vrplsh;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "vehicleLsh", unique = true, nullable = false)
    public Integer getVehicleLsh() {
        return vehicleLsh;
    }

    public void setVehicleLsh(Integer vehicleLsh) {
        this.vehicleLsh = vehicleLsh;
    }

    @Column(name = "crossLsh")
    public Integer getCrossLsh() {
        return crossLsh;
    }

    public void setCrossLsh(Integer crossLsh) {
        this.crossLsh = crossLsh;
    }

    @Column(name = "deviceIndex")
    public String getDeviceIndex() {
        return deviceIndex;
    }

    public void setDeviceIndex(String deviceIndex) {
        this.deviceIndex = deviceIndex;
    }

    @Column(name = "vehicleIndex")
    public String getVehicleIndex() {
        return vehicleIndex;
    }

    public void setVehicleIndex(String vehicleIndex) {
        this.vehicleIndex = vehicleIndex;
    }

    @Column(name = "directIndex")
    public String getDirectIndex() {
        return directIndex;
    }

    public void setDirectIndex(String directIndex) {
        this.directIndex = directIndex;
    }

    @Column(name = "plateInfo")
    public String getPlateInfo() {
        return plateInfo;
    }

    public void setPlateInfo(String plateInfo) {
        this.plateInfo = plateInfo;
    }

    @Column(name = "plateType")
    public Integer getPlateType() {
        return plateType;
    }

    public void setPlateType(Integer plateType) {
        this.plateType = plateType;
    }

    @Column(name = "passTime")
    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    @Column(name = "vehicleSpeed")
    public Integer getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(Integer vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    @Column(name = "vehicleLen")
    public Integer getVehicleLen() {
        return vehicleLen;
    }

    public void setVehicleLen(Integer vehicleLen) {
        this.vehicleLen = vehicleLen;
    }

    @Column(name = "plateColor")
    public Integer getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(Integer plateColor) {
        this.plateColor = plateColor;
    }

    @Column(name = "picFeature")
    public String getPicFeature() {
        return picFeature;
    }

    public void setPicFeature(String picFeature) {
        this.picFeature = picFeature;
    }

    @Column(name = "picFullView")
    public String getPicFullView() {
        return picFullView;
    }

    public void setPicFullView(String picFullView) {
        this.picFullView = picFullView;
    }

    @Column(name = "picFTPPath")
    public String getPicFTPPath() {
        return picFTPPath;
    }

    public void setPicFTPPath(String picFTPPath) {
        this.picFTPPath = picFTPPath;
    }

    @Column(name = "downLoadFlag")
    public Integer getDownLoadFlag() {
        return downLoadFlag;
    }

    public void setDownLoadFlag(Integer downLoadFlag) {
        this.downLoadFlag = downLoadFlag;
    }

    @Column(name = "picLocalPath")
    public String getPicLocalPath() {
        return picLocalPath;
    }

    public void setPicLocalPath(String picLocalPath) {
        this.picLocalPath = picLocalPath;
    }

    @Column(name = "drivewayNumber")
    public Integer getDrivewayNumber() {
        return drivewayNumber;
    }

    public void setDrivewayNumber(Integer drivewayNumber) {
        this.drivewayNumber = drivewayNumber;
    }

    @Column(name = "vehicleType")
    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Column(name = "vehicleColor")
    public Integer getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(Integer vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    @Column(name = "vehicleState")
    public Integer getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(Integer vehicleState) {
        this.vehicleState = vehicleState;
    }

    @Column(name = "vehColorDepth")
    public Integer getVehColorDepth() {
        return vehColorDepth;
    }

    public void setVehColorDepth(Integer vehColorDepth) {
        this.vehColorDepth = vehColorDepth;
    }

    @Column(name = "recordID")
    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    @Column(name = "sendFlag")
    public Integer getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(Integer sendFlag) {
        this.sendFlag = sendFlag;
    }

    @Column(name = "vrplsh")
    public Integer getVrplsh() {
        return vrplsh;
    }

    public void setVrplsh(Integer vrplsh) {
        this.vrplsh = vrplsh;
    }
}
