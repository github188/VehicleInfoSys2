package cn.jiuling.vehicleinfosys2.vo;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * 过车信息Vo(吉林白山)
 * Created by wangrb on 2015/10/16.
 */
public class TblVehicleRecordVo implements Serializable {

    private BigInteger recordId; //车辆信息编号
    private Date passTime; //通过时间
    private String pic1Name; //图像1的url
    private String tollgateCode; //卡口编号
    private String devAddr; //设备地址

    public BigInteger getRecordId() {
        return recordId;
    }

    public void setRecordId(BigInteger recordId) {
        this.recordId = recordId;
    }

    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    public String getPic1Name() {
        return pic1Name;
    }

    public void setPic1Name(String pic1Name) {
        this.pic1Name = pic1Name;
    }


    public String getDevAddr() {
        return devAddr;
    }

    public void setDevAddr(String devAddr) {
        this.devAddr = devAddr;
    }

    public String getTollgateCode() {
        return tollgateCode;
    }

    public void setTollgateCode(String tollgateCode) {
        this.tollgateCode = tollgateCode;
    }

    public TblVehicleRecordVo() {
    }

    public TblVehicleRecordVo(BigInteger recordId, String tollgateCode, Date passTime, String pic1Name, String devAddr) {
        this.recordId = recordId;
        this.tollgateCode = tollgateCode;
        this.passTime = passTime;
        this.pic1Name = pic1Name;
        this.devAddr = devAddr;
    }
}
