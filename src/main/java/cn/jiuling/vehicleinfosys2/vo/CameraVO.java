package cn.jiuling.vehicleinfosys2.vo;

import cn.jiuling.vehicleinfosys2.model.Camera;

import java.util.List;

/**
 * Created by Administrator on 2015/10/8.
 */
public class CameraVO extends Camera {
    Pager p;
    Camera theCam;
    List<Tree> areaTree;
    private String cameraGroupName;

    public String getCameraGroupName() {
        return cameraGroupName;
    }

    public void setCameraGroupName(String cameraGroupName) {
        this.cameraGroupName = cameraGroupName;
    }

    public Camera getTheCam() {
        return theCam;
    }

    public void setTheCam(Camera theCam) {
        this.theCam = theCam;
    }

    public Pager getP() {
        return p;
    }

    public void setP(Pager p) {
        this.p = p;
    }

    public List<Tree> getAreaTree() {
        return areaTree;
    }

    public void setAreaTree(List<Tree> areaTree) {
        this.areaTree = areaTree;
    }
}
