package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	private Short priority;
	private String recognitionSlaveIp;
	private Set<VlprResult> vlprResults = new HashSet<VlprResult>();

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

	@Column(name = "priority")
	public Short getPriority() {
		return priority;
	}

	public void setPriority(Short priority) {
		this.priority = priority;
	}

}