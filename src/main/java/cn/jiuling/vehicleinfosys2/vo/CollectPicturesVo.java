package cn.jiuling.vehicleinfosys2.vo;

import cn.jiuling.vehicleinfosys2.model.VlprCollectPictures;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 采集图片任务信息
 * @author wangrb
 * @date 2015-11-30
 */
public class CollectPicturesVo extends VlprCollectPictures {

    private String cameraNames; //所选监控点名称

    public CollectPicturesVo() {
    }

    public String getCameraNames() {
        return cameraNames;
    }

    public void setCameraNames(String cameraNames) {
        this.cameraNames = cameraNames;
    }
}