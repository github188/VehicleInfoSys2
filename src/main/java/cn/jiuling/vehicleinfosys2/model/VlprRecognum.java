package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 识别数信息
 * @author wangrb
 * @date 2015-12-1
 */
@Entity
@Table(name = "vlpr_recognum")
public class VlprRecognum implements java.io.Serializable {

    private Integer id;
    private Date recogDate;  //日期
    private Integer recogNum;
    private Integer recogCnt; //当天总处理量

    public VlprRecognum() {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "RecogDate")
    public Date getRecogDate() {
        return recogDate;
    }

    public void setRecogDate(Date recogDate) {
        this.recogDate = recogDate;
    }

    @Column(name = "RecogNum", length = 11)
    public Integer getRecogNum() {
        return recogNum;
    }

    public void setRecogNum(Integer recogNum) {
        this.recogNum = recogNum;
    }

    @Column(name = "RecogCnt", length = 11)
    public Integer getRecogCnt() {
        return recogCnt;
    }

    public void setRecogCnt(Integer recogCnt) {
        this.recogCnt = recogCnt;
    }
}