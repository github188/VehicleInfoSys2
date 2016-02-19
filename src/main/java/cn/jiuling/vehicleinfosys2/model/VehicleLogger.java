package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 日志类
 */
@Entity
@Table(name="vehicle_log")
public class VehicleLogger implements Serializable {

    private Integer id;//id
    private String account;//账号
    private Timestamp createdate;//操作时间
    private String operation;//操作类型(主要是"添加"、"修改"、"删除")
    private String logInfo;//日志内容
    private String operObj;//操作对象
    private String input;//输入
    private String output;//输出
    private String operResult;//操作结果（成功/失败）
    private String guessIP;//客户端ip

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Column(name = "createdate")
    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    @Column(name = "operation")
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Column(name = "logInfo")
    public String getLogInfo() {
        return logInfo;
    }

    public void setLogInfo(String logInfo) {
        this.logInfo = logInfo;
    }

    @Column(name = "operObj")
    public String getOperObj() {
        return operObj;
    }

    public void setOperObj(String operObj) {
        this.operObj = operObj;
    }

    @Column(name = "input")
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Column(name = "output")
    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    @Column(name = "operResult")
    public String getOperResult() {
        return operResult;
    }

    public void setOperResult(String operResult) {
        this.operResult = operResult;
    }

    @Column(name = "guessIP")
    public String getGuessIP() {
        return guessIP;
    }

    public void setGuessIP(String guessIP) {
        this.guessIP = guessIP;
    }
}
