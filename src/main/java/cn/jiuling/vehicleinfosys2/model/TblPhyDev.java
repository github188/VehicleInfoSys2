package cn.jiuling.vehicleinfosys2.model;

import org.postgresql.util.PGobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 物理设备(吉林白山)
 * Created by wangrb on 2015/10/16.
 */
@Entity
@Table(name = "tbl_phy_dev")
public class TblPhyDev extends PGobject implements Serializable {

    private String devCode; //设备编码
    private String devAddr; //设备地址

    @Id
    @Column(name = "dev_code", unique = true, nullable = false)
    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    @Column(name = "dev_addr")
    public String getDevAddr() {
        return devAddr;
    }

    public void setDevAddr(String devAddr) {
        this.devAddr = devAddr;
    }


}
