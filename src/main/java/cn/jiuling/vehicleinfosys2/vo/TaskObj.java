package cn.jiuling.vehicleinfosys2.vo;

/**
 * Created by Administrator on 2015/5/21 0021.
 * areaType: "1"
 * dropFrame: "0"
 * followarea: "9,3|186,6|122,84|45,201|2,260|1,1"
 * height: 385
 * id: 6
 * width: 600
 * zoomWidth: "1"
 * zoomHeight: "1"
 */

public class TaskObj {
    private Integer id;
    private Short areaType;
    private Integer dropFrame;
    private String followarea;
    private Integer width;
    private Integer height;
    private Integer zoomWidth;
    private Integer zoomHeight;
    private Integer minimumWidth;
    private Integer maximumHeight;
    private Short type;
    private Short detectMode;
    private Short vedioDetectMode;
    private String dateFormatTemp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getAreaType() {
        return areaType;
    }

    public void setAreaType(Short areaType) {
        this.areaType = areaType;
    }

    public Integer getDropFrame() {
        return dropFrame;
    }

    public void setDropFrame(Integer dropFrame) {
        this.dropFrame = dropFrame;
    }

    public String getFollowarea() {
        return followarea;
    }

    public void setFollowarea(String followarea) {
        this.followarea = followarea;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getZoomWidth() {
        return zoomWidth;
    }

    public void setZoomWidth(Integer zoomWidth) {
        this.zoomWidth = zoomWidth;
    }

    public Integer getZoomHeight() {
        return zoomHeight;
    }

    public void setZoomHeight(Integer zoomHeight) {
        this.zoomHeight = zoomHeight;
    }

    public Integer getMinimumWidth() {
        return minimumWidth;
    }

    public void setMinimumWidth(Integer minimumWidth) {
        this.minimumWidth = minimumWidth;
    }

    public Integer getMaximumHeight() {
        return maximumHeight;
    }

    public void setMaximumHeight(Integer maximumHeight) {
        this.maximumHeight = maximumHeight;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getDetectMode() {
        return detectMode;
    }

    public void setDetectMode(Short detectMode) {
        this.detectMode = detectMode;
    }

	public Short getVedioDetectMode() {
		return vedioDetectMode;
	}

	public void setVedioDetectMode(Short vedioDetectMode) {
		this.vedioDetectMode = vedioDetectMode;
	}

    public String getDateFormatTemp() { return dateFormatTemp; }

    public void setDateFormatTemp(String dateFormatTemp) { this.dateFormatTemp = dateFormatTemp; }
}
