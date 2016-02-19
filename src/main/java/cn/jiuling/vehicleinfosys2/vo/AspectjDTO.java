package cn.jiuling.vehicleinfosys2.vo;

/**
 * Created by Administrator on 2015/12/7.
 */
public class AspectjDTO {

    /**
     * 操作描述
     * @return
     */
    private String remark;
    /**
     * 操作类型
     * @return
     */
    private String operType;

    /**
     * 操作对象
     * @return
     */
    private String operObj;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperType() {
        return operType;
    }

    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperObj() {
        return operObj;
    }

    public void setOperObj(String operObj) {
        this.operObj = operObj;
    }
}
