package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "vlpr_vfm_result")
public class VlprVfmResult implements java.io.Serializable {

    private Long id;
    private Long vfmTaskID;
    private Long serialNumber;
    private float vfmScore;
    private Short vfmLeft;
    private Short vfmTop;
    private Short vfmRight;
    private Short vfmBottom;
    private Timestamp insertTime;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getID() {
        return this.id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    @Column(name = "vfmTaskID", length = 20)
    public Long getVfmTaskID() {
        return vfmTaskID;
    }

    public void setVfmTaskID(Long vfmTaskID) {
        this.vfmTaskID = vfmTaskID;
    }

    @Column(name = "SerialNumber")
    public Long getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }


    @Column(name = "vfmScore")
    public float getVfmScore() {
        return this.vfmScore;
    }

    public void setVfmScore(float vfmScore) {
        this.vfmScore = vfmScore;
    }

    @Column(name = "vfmLeft")
    public Short getVfmLeft() {
        return this.vfmLeft;
    }

    public void setVfmLeft(Short vfmLeft) {
        this.vfmLeft = vfmLeft;
    }

    @Column(name = "vfmTop")
    public Short getVfmTop() {
        return this.vfmTop;
    }

    public void setVfmTop(Short vfmTop) {
        this.vfmTop = vfmTop;
    }

    @Column(name = "vfmRight")
    public Short getVfmRight() {
        return this.vfmRight;
    }

    public void setVfmRight(Short vfmRight) {
        this.vfmRight = vfmRight;
    }

    @Column(name = "vfmBottom")
    public Short getVfmBottom() {
        return this.vfmBottom;
    }

    public void setVfmBottom(Short vfmBottom) {
        this.vfmBottom = vfmBottom;
    }

    @Column(name = "insertTime")
    public Timestamp getInsertTime() {
        return this.insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }

}