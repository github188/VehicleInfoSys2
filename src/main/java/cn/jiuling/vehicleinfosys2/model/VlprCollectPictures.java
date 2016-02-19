package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 采集图片任务信息
 * @author wangrb
 * @date 2015-11-6
 */
@Entity
@Table(name = "vlpr_collect_pictures")
public class VlprCollectPictures implements java.io.Serializable {

    private Long id;
    private String cameraName; //监控点名称
    private Timestamp startTime;  //（下载图片选定）起始时间
    private Timestamp endTime;  //(下载图片选定)结束时间
    private Short status;   //状态（0、等待中 1、下载中 2、已完成 3、暂停中 4、已终止）
    private Integer downloadCount;  //采集图片的数量
    private Long maxRecordId; //过车id（用于控制采集图片操作）
    private Timestamp passTime; //通车时间（采集进程时间）
    private Timestamp createTime;

    public VlprCollectPictures() {
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "download_count", length = 11)
    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Column(name = "start_time", length = 19)
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time", length = 19)
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @Column(name = "status", nullable = false)
    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Column(name = "pass_time", length = 19)
    public Timestamp getPassTime() {
        return passTime;
    }

    public void setPassTime(Timestamp passTime) {
        this.passTime = passTime;
    }

    @Column(name = "create_time", nullable = false, length = 19)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "camera_name", length = 100)
    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    @Column(name = "max_record_id", length = 20)
    public Long getMaxRecordId() {
        return maxRecordId;
    }

    public void setMaxRecordId(Long maxRecordId) {
        this.maxRecordId = maxRecordId;
    }
}