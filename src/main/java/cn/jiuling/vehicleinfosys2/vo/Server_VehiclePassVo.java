package cn.jiuling.vehicleinfosys2.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2015/8/3.
 */
public class Server_VehiclePassVo implements Serializable {

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

    // 服务器ID
    private Integer server_id;
    //名称
    private String name;
    //组织机构ID
    private Integer control_unit_id;
    //服务器类型
    private Integer server_type;
    //IP地址
    private String ip_addr;

    private String index_code;//编号

    private Integer hpp_port;//端口
    private Integer share_flag;
    private Integer net_zone_id;//网域ID
    private Integer serverRes1;//预留字段1
    private String serverRes2;//预留字段2

    public Server_VehiclePassVo() {
    }

    public Server_VehiclePassVo(Integer vehicleLsh, Integer crossLsh, String deviceIndex, String vehicleIndex, String directIndex, String plateInfo, Integer plateType, Date passTime, Integer vehicleSpeed, Integer vehicleLen, Integer plateColor, String picFeature, String picFullView, String picFTPPath, Integer downLoadFlag, String picLocalPath, Integer drivewayNumber, Integer vehicleType, Integer vehicleColor, Integer vehicleState, Integer vehColorDepth, String recordID, Integer sendFlag, Integer vrplsh, Integer server_id, String name, Integer control_unit_id, Integer server_type, String ip_addr, String index_code, Integer hpp_port, Integer share_flag, Integer net_zone_id, Integer serverRes1, String serverRes2) {
        this.vehicleLsh = vehicleLsh;
        this.crossLsh = crossLsh;
        this.deviceIndex = deviceIndex;
        this.vehicleIndex = vehicleIndex;
        this.directIndex = directIndex;
        this.plateInfo = plateInfo;
        this.plateType = plateType;
        this.passTime = passTime;
        this.vehicleSpeed = vehicleSpeed;
        this.vehicleLen = vehicleLen;
        this.plateColor = plateColor;
        this.picFeature = picFeature;
        this.picFullView = picFullView;
        this.picFTPPath = picFTPPath;
        this.downLoadFlag = downLoadFlag;
        this.picLocalPath = picLocalPath;
        this.drivewayNumber = drivewayNumber;
        this.vehicleType = vehicleType;
        this.vehicleColor = vehicleColor;
        this.vehicleState = vehicleState;
        this.vehColorDepth = vehColorDepth;
        this.recordID = recordID;
        this.sendFlag = sendFlag;
        this.vrplsh = vrplsh;
        this.server_id = server_id;
        this.name = name;
        this.control_unit_id = control_unit_id;
        this.server_type = server_type;
        this.ip_addr = ip_addr;
        this.index_code = index_code;
        this.hpp_port = hpp_port;
        this.share_flag = share_flag;
        this.net_zone_id = net_zone_id;
        this.serverRes1 = serverRes1;
        this.serverRes2 = serverRes2;
    }

    public Integer getVehicleLsh() {
        return vehicleLsh;
    }

    public void setVehicleLsh(Integer vehicleLsh) {
        this.vehicleLsh = vehicleLsh;
    }

    public Integer getCrossLsh() {
        return crossLsh;
    }

    public void setCrossLsh(Integer crossLsh) {
        this.crossLsh = crossLsh;
    }

    public String getDeviceIndex() {
        return deviceIndex;
    }

    public void setDeviceIndex(String deviceIndex) {
        this.deviceIndex = deviceIndex;
    }

    public String getVehicleIndex() {
        return vehicleIndex;
    }

    public void setVehicleIndex(String vehicleIndex) {
        this.vehicleIndex = vehicleIndex;
    }

    public String getDirectIndex() {
        return directIndex;
    }

    public void setDirectIndex(String directIndex) {
        this.directIndex = directIndex;
    }

    public String getPlateInfo() {
        return plateInfo;
    }

    public void setPlateInfo(String plateInfo) {
        this.plateInfo = plateInfo;
    }

    public Integer getPlateType() {
        return plateType;
    }

    public void setPlateType(Integer plateType) {
        this.plateType = plateType;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public Integer getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(Integer vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public Integer getVehicleLen() {
        return vehicleLen;
    }

    public void setVehicleLen(Integer vehicleLen) {
        this.vehicleLen = vehicleLen;
    }

    public Integer getPlateColor() {
        return plateColor;
    }

    public void setPlateColor(Integer plateColor) {
        this.plateColor = plateColor;
    }

    public String getPicFeature() {
        return picFeature;
    }

    public void setPicFeature(String picFeature) {
        this.picFeature = picFeature;
    }

    public String getPicFullView() {
        return picFullView;
    }

    public void setPicFullView(String picFullView) {
        this.picFullView = picFullView;
    }

    public String getPicFTPPath() {
        return picFTPPath;
    }

    public void setPicFTPPath(String picFTPPath) {
        this.picFTPPath = picFTPPath;
    }

    public Integer getDownLoadFlag() {
        return downLoadFlag;
    }

    public void setDownLoadFlag(Integer downLoadFlag) {
        this.downLoadFlag = downLoadFlag;
    }

    public String getPicLocalPath() {
        return picLocalPath;
    }

    public void setPicLocalPath(String picLocalPath) {
        this.picLocalPath = picLocalPath;
    }

    public Integer getDrivewayNumber() {
        return drivewayNumber;
    }

    public void setDrivewayNumber(Integer drivewayNumber) {
        this.drivewayNumber = drivewayNumber;
    }

    public Integer getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Integer vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(Integer vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public Integer getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(Integer vehicleState) {
        this.vehicleState = vehicleState;
    }

    public Integer getVehColorDepth() {
        return vehColorDepth;
    }

    public void setVehColorDepth(Integer vehColorDepth) {
        this.vehColorDepth = vehColorDepth;
    }

    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public Integer getSendFlag() {
        return sendFlag;
    }

    public void setSendFlag(Integer sendFlag) {
        this.sendFlag = sendFlag;
    }

    public Integer getVrplsh() {
        return vrplsh;
    }

    public void setVrplsh(Integer vrplsh) {
        this.vrplsh = vrplsh;
    }

    public Integer getServer_id() {
        return server_id;
    }

    public void setServer_id(Integer server_id) {
        this.server_id = server_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getControl_unit_id() {
        return control_unit_id;
    }

    public void setControl_unit_id(Integer control_unit_id) {
        this.control_unit_id = control_unit_id;
    }

    public Integer getServer_type() {
        return server_type;
    }

    public void setServer_type(Integer server_type) {
        this.server_type = server_type;
    }

    public String getIp_addr() {
        return ip_addr;
    }

    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
    }

    public String getIndex_code() {
        return index_code;
    }

    public void setIndex_code(String index_code) {
        this.index_code = index_code;
    }

    public Integer getHpp_port() {
        return hpp_port;
    }

    public void setHpp_port(Integer hpp_port) {
        this.hpp_port = hpp_port;
    }

    public Integer getShare_flag() {
        return share_flag;
    }

    public void setShare_flag(Integer share_flag) {
        this.share_flag = share_flag;
    }

    public Integer getNet_zone_id() {
        return net_zone_id;
    }

    public void setNet_zone_id(Integer net_zone_id) {
        this.net_zone_id = net_zone_id;
    }

    public Integer getServerRes1() {
        return serverRes1;
    }

    public void setServerRes1(Integer serverRes1) {
        this.serverRes1 = serverRes1;
    }

    public String getServerRes2() {
        return serverRes2;
    }

    public void setServerRes2(String serverRes2) {
        this.serverRes2 = serverRes2;
    }
}
