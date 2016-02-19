package cn.jiuling.vehicleinfosys2.vo;

/**
 * 包含任务进度的pager
 * Created by Administrator on 2015/6/10.
 */
public class PagerWithProgress extends Pager{
    private Short progress;
    private Short status;

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getProgress() {
        return progress;
    }

    public void setProgress(Short progress) {
        this.progress = progress;
    }
}
