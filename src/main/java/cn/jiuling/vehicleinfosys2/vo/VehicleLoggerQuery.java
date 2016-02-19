package cn.jiuling.vehicleinfosys2.vo;

import cn.jiuling.vehicleinfosys2.model.VehicleLogger;

import java.sql.Timestamp;

/**
 * Created by Administrator on 2015/12/4.
 */
public class VehicleLoggerQuery {

    private String account;
    private String operation;
    private Timestamp startTime;
    private Timestamp endTime;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
