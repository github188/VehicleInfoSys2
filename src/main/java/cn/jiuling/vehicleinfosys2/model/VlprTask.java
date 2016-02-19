package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "vlpr_task")
public class VlprTask implements java.io.Serializable {

    private Long taskId;
    private String filePath;
    private Short progress;
    private Long videoId;
    private Short status; // 0：等待处理 1：正在处理 2：处理成功 3：处理失败
    private Short flag;
    private Timestamp createTime;
    private Short retryCount;
    private Short roiX0;
    private Short roiY0;
    private Short roiX1;
    private Short roiY1;
    private Short roiCx;
    private Short roiCy;
    private String recognitionSlaveIp;
    private String coverUrl;
    private Integer dropFrame;
    private Integer zoomWidth;
    private Integer zoomHeight;
    private Integer minimumWidth;
    private Integer maximumHeight;
    private Short detectMode;
    private Short vedioDetectMode;
    private String vlprResultPath;
    private String picstreamHisPath;
    private Set<VlprResult> vlprResults = new HashSet<VlprResult>();
    private String dateFormatTemp;
    private Integer vlprTaskType;

    public VlprTask() {
    }

    public VlprTask(Long taskId) {
        this.taskId = taskId;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "TaskID", unique = true, nullable = false)
    public Long getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @Column(name = "FilePath", nullable = false, length = 1024)
    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Column(name = "Progress", nullable = false)
    public Short getProgress() {
        return this.progress;
    }

    public void setProgress(Short progress) {
        this.progress = progress;
    }

    @Column(name = "VideoID", nullable = false)
    public Long getVideoId() {
        return this.videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    @Column(name = "Status", nullable = false)
    public Short getStatus() {
        return this.status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    @Column(name = "Flag", nullable = false)
    public Short getFlag() {
        return this.flag;
    }

    public void setFlag(Short flag) {
        this.flag = flag;
    }

    @Column(name = "CreateTime", nullable = false, length = 19)
    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Column(name = "retryCount", nullable = false)
    public Short getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(Short retryCount) {
        this.retryCount = retryCount;
    }

    @Column(name = "roi_x0")
    public Short getRoiX0() {
        return this.roiX0;
    }

    public void setRoiX0(Short roiX0) {
        this.roiX0 = roiX0;
    }

    @Column(name = "roi_y0")
    public Short getRoiY0() {
        return this.roiY0;
    }

    public void setRoiY0(Short roiY0) {
        this.roiY0 = roiY0;
    }

    @Column(name = "roi_x1")
    public Short getRoiX1() {
        return this.roiX1;
    }

    public void setRoiX1(Short roiX1) {
        this.roiX1 = roiX1;
    }

    @Column(name = "roi_y1")
    public Short getRoiY1() {
        return this.roiY1;
    }

    public void setRoiY1(Short roiY1) {
        this.roiY1 = roiY1;
    }

    @Column(name = "roi_cx")
    public Short getRoiCx() {
        return this.roiCx;
    }

    public void setRoiCx(Short roiCx) {
        this.roiCx = roiCx;
    }

    @Column(name = "roi_cy")
    public Short getRoiCy() {
        return this.roiCy;
    }

    public void setRoiCy(Short roiCy) {
        this.roiCy = roiCy;
    }

    @Column(name = "RecognitionSlaveIP", nullable = false, length = 1024)
    public String getRecognitionSlaveIp() {
        return this.recognitionSlaveIp;
    }

    public void setRecognitionSlaveIp(String recognitionSlaveIp) {
        this.recognitionSlaveIp = recognitionSlaveIp;
    }

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "vlprTask")
    public Set<VlprResult> getVlprResults() {
        return vlprResults;
    }

    public void setVlprResults(Set<VlprResult> vlprResults) {
        this.vlprResults = vlprResults;
    }

    @Column(name = "drop_frame")
    public Integer getDropFrame() {
        return dropFrame;
    }

    public void setDropFrame(Integer dropFrame) {
        this.dropFrame = dropFrame;
    }

    @Column(name = "zoom_width")
    public Integer getZoomWidth() {
        return zoomWidth;
    }

    public void setZoomWidth(Integer zoomWidth) {
        this.zoomWidth = zoomWidth;
    }
    @Column(name = "cover_url")
    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    @Column(name = "zoom_height")
    public Integer getZoomHeight() {
        return zoomHeight;
    }

    public void setZoomHeight(Integer zoomHeight) {
        this.zoomHeight = zoomHeight;
    }

    @Column(name = "minimum_width")
    public Integer getMinimumWidth() {
        return minimumWidth;
    }

    public void setMinimumWidth(Integer minimumWidth) {
        this.minimumWidth = minimumWidth;
    }

    @Column(name = "maximum_height")
    public Integer getMaximumHeight() {
        return maximumHeight;
    }

    public void setMaximumHeight(Integer maximumHeight) {
        this.maximumHeight = maximumHeight;
    }

    @Column(name = "detectmode")
    public Short getDetectMode() {
        return detectMode;
    }

    public void setDetectMode(Short detectMode) {
        this.detectMode = detectMode;
    }
    
    @Column(name = "vedioDetectMode")
    public Short getVedioDetectMode() {
		return vedioDetectMode;
	}

	public void setVedioDetectMode(Short vedioDetectMode) {
		this.vedioDetectMode = vedioDetectMode;
	}

	@Column(name="vlpr_result_path")
    public String getVlprResultPath() {
        return vlprResultPath;
    }

    public void setVlprResultPath(String vlprResultPath) {
        this.vlprResultPath = vlprResultPath;
    }

    @Column(name = "picstream_his_path")
    public String getPicstreamHisPath() {
        return picstreamHisPath;
    }

    public void setPicstreamHisPath(String picstreamHisPath) {
        this.picstreamHisPath = picstreamHisPath;
    }

    @Column(name = "dateformat_temp")
    public String getDateFormatTemp() {
        return dateFormatTemp;
    }

    public void setDateFormatTemp(String dateFormatTemp) {
        this.dateFormatTemp = dateFormatTemp;
    }
    
    @Column(name = "vlpr_task_type")
	public Integer getVlprTaskType() {
		return vlprTaskType;
	}

	public void setVlprTaskType(Integer vlprTaskType) {
		this.vlprTaskType = vlprTaskType;
	}
    
}