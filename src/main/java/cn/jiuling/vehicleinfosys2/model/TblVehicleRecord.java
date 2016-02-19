package cn.jiuling.vehicleinfosys2.model;

import org.postgresql.util.PGobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * 过车信息(吉林白山)
 * Created by wangrb on 2015/10/16.
 */
@Entity
@Table(name = "tbl_vehicle_record")
public class TblVehicleRecord extends PGobject implements Serializable {

    private BigInteger recordId; //    车辆信息编号
    private String tollgateCode; //    卡口编号
    private Date passTime; //    通过时间
    private String pic1Name; //    图像1名称

    @Id
    @Column(name = "record_id", unique = true, nullable = false)
    public BigInteger getRecordId() {
        return recordId;
    }

    public void setRecordId(BigInteger recordId) {
        this.recordId = recordId;
    }

    @Column(name = "tollgate_code")
    public String getTollgateCode() {
        return tollgateCode;
    }

    public void setTollgateCode(String tollgateCode) {
        this.tollgateCode = tollgateCode;
    }

    @Column(name = "pass_time")
    public Date getPassTime() {
        return passTime;
    }

    public void setPassTime(Date passTime) {
        this.passTime = passTime;
    }

    @Column(name = "pic1_name")
    public String getPic1Name() {
        return pic1Name;
    }

    public void setPic1Name(String pic1Name) {
        this.pic1Name = pic1Name;
    }

}
