package cn.jiuling.vehicleinfosys2.vo;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2015/8/11.
 */
public class TravelTogetherVehicleQuery {

    private String plate;
    private Timestamp startTime;
    private Timestamp endTime;

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
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
