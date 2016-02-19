package cn.jiuling.vehicleinfosys2.model;

import org.postgresql.util.PGobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 卡口信息(吉林白山)
 * Created by wangrb on 2015/10/16.
 */
@Entity
@Table(name = "tbl_tollgate")
public class TblTollgate extends PGobject implements Serializable {

    private String tollgateCode; //    卡口编码
    private String tmsCode; //    卡口代理服务器编码
    private String longitude; //    经度
    private String latitude; //    纬度
    @Id
    @Column(name = "tollgate_code", unique = true, nullable = false)
    public String getTollgateCode() {
        return tollgateCode;
    }

    public void setTollgateCode(String tollgateCode) {
        this.tollgateCode = tollgateCode;
    }

    @Column(name = "tms_code")
    public String getTmsCode() {
        return tmsCode;
    }

    public void setTmsCode(String tmsCode) {
        this.tmsCode = tmsCode;
    }

    @Column(name = "longitude")
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Column(name = "latitude")
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
