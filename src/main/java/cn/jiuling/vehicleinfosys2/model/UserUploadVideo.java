package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2015/9/25.
 */
@Entity
@Table(name = "useruploadvideo")
public class UserUploadVideo implements Serializable {

    private BigInteger id;
    private BigInteger userid;
    private String srcURL;
    private String destURL;
    private Integer status;
    private Integer lastErrCode;
    private String lastErrMsg;
    private Integer progress;
    private Integer retryCount;
    private Integer lastTryVideoVendorType;
    private String resolution;
    private Integer duration;
    private Integer space;
    private Integer frameRate;
    private Integer videoType;
    private byte[] videoConfig;
    private Integer cameraId;
    private String videoName;
    private Integer isAddedToDataSource;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "UserUploadVideoId", unique = true, nullable = false)
    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    @Column(name = "userid", nullable = false)
    public BigInteger getUserid() {
        return userid;
    }

    public void setUserid(BigInteger userid) {
        this.userid = userid;
    }

    @Column(name = "srcURL", nullable = false)
    public String getSrcURL() {
        return srcURL;
    }

    public void setSrcURL(String srcURL) {
        this.srcURL = srcURL;
    }


    @Column(name = "destURL", nullable = false)
    public String getDestURL() {
        return destURL;
    }

    public void setDestURL(String destURL) {
        this.destURL = destURL;
    }

    @Column(name = "status", nullable = false)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "last_err_code", nullable = false)
    public Integer getLastErrCode() {
        return lastErrCode;
    }

    public void setLastErrCode(Integer lastErrCode) {
        this.lastErrCode = lastErrCode;
    }

    @Column(name = "last_err_msg")
    public String getLastErrMsg() {
        return lastErrMsg;
    }

    public void setLastErrMsg(String lastErrMsg) {
        this.lastErrMsg = lastErrMsg;
    }

    @Column(name = "progress", nullable = false)
    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    @Column(name = "retry_count", nullable = false)
    public Integer getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    @Column(name = "last_try_video_vendor_type", nullable = false)
    public Integer getLastTryVideoVendorType() {
        return lastTryVideoVendorType;
    }

    public void setLastTryVideoVendorType(Integer lastTryVideoVendorType) {
        this.lastTryVideoVendorType = lastTryVideoVendorType;
    }

    @Column(name = "resolution", nullable = false)
    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    @Column(name = "duration", nullable = false)
    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Column(name = "space", nullable = false)
    public Integer getSpace() {
        return space;
    }

    public void setSpace(Integer space) {
        this.space = space;
    }

    @Column(name = "frame_rate", nullable = false)
    public Integer getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Integer frameRate) {
        this.frameRate = frameRate;
    }

    @Column(name = "video_type", nullable = false)
    public Integer getVideoType() {
        return videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    @Column(name = "video_config")
    public byte[] getVideoConfig() {
        return videoConfig;
    }

    public void setVideoConfig(byte[] videoConfig) {
        this.videoConfig = videoConfig;
    }

    @Column(name = "cameraId", nullable = false)
    public Integer getCameraId() {
        return cameraId;
    }

    public void setCameraId(Integer cameraId) {
        this.cameraId = cameraId;
    }

    @Column(name = "videoName", nullable = false)
    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    @Column(name = "isAddedToDataSource", nullable = false)
    public Integer getIsAddedToDataSource() {
        return isAddedToDataSource;
    }

    public void setIsAddedToDataSource(Integer isAddedToDataSource) {
        this.isAddedToDataSource = isAddedToDataSource;
    }
}
