package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 海康 服务器信息表
 * Created by Administrator on 2015/7/31.
 */
@Entity
@Table(name = "Server_Info")
public class Server_Info implements Serializable {
    // 服务器ID
    private Integer server_id;
    //名称
    private String name;
    //组织机构ID
    private Integer control_unit_id;
    //服务器类型
    private Integer server_type;
    //IP地址
    private String ip_addr;

    private String index_code;//编号

    private Integer hpp_port;//端口
    private Integer share_flag;
    private Integer net_zone_id;//网域ID
    private Integer serverRes1;//预留字段1
    private String serverRes2;//预留字段2

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "Server_id", unique = true, nullable = false)
    public Integer getServer_id() {
        return server_id;
    }

    public void setServer_id(Integer server_id) {
        this.server_id = server_id;
    }

    @Column(name="Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="Control_unit_id")
    public Integer getControl_unit_id() {
        return control_unit_id;
    }


    public void setControl_unit_id(Integer control_unit_id) {
        this.control_unit_id = control_unit_id;
    }

    @Column(name="Server_type")
    public Integer getServer_type() {
        return server_type;
    }


    public void setServer_type(Integer server_type) {
        this.server_type = server_type;
    }

    @Column(name="Ip_addr")
    public String getIp_addr() {
        return ip_addr;
    }

    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
    }

    @Column(name="Index_code")
    public String getIndex_code() {
        return index_code;
    }

    public void setIndex_code(String index_code) {
        this.index_code = index_code;
    }

    @Column(name="Hpp_port")
    public Integer getHpp_port() {
        return hpp_port;
    }

    public void setHpp_port(Integer hpp_port) {
        this.hpp_port = hpp_port;
    }

    @Column(name="Share_flag")
    public Integer getShare_flag() {
        return share_flag;
    }

    public void setShare_flag(Integer share_flag) {
        this.share_flag = share_flag;
    }

    @Column(name="Net_zone_id")
    public Integer getNet_zone_id() {
        return net_zone_id;
    }

    public void setNet_zone_id(Integer net_zone_id) {
        this.net_zone_id = net_zone_id;
    }

    @Column(name="ServerRes1")
    public Integer getServerRes1() {
        return serverRes1;
    }

    public void setServerRes1(Integer serverRes1) {
        this.serverRes1 = serverRes1;
    }

    @Column(name="ServerRes2")
    public String getServerRes2() {
        return serverRes2;
    }

    public void setServerRes2(String serverRes2) {
        this.serverRes2 = serverRes2;
    }
}
