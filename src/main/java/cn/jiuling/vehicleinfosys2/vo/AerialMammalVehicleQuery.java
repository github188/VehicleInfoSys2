package cn.jiuling.vehicleinfosys2.vo;

import java.sql.Timestamp;

public class AerialMammalVehicleQuery {

    private String nightStartTime;
    private String nightEndTime;
	private Integer appearNum;
    private Timestamp startTime;
    private Timestamp endTime;

    public String getNightStartTime() {
        return nightStartTime;
    }

    public void setNightStartTime(String nightStartTime) {
        this.nightStartTime = nightStartTime;
    }

    public String getNightEndTime() {
        return nightEndTime;
    }

    public void setNightEndTime(String nightEndTime) {
        this.nightEndTime = nightEndTime;
    }

    public Integer getAppearNum() {
        return appearNum;
    }

    public void setAppearNum(Integer appearNum) {
        this.appearNum = appearNum;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
