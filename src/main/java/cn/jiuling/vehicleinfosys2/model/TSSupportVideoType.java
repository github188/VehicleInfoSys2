package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by Administrator on 2015/9/25.
 */
@Entity
@Table(name = "tbl_tssupportvideotype")
public class TSSupportVideoType implements Serializable{

    private BigInteger typeID;
    private String typeName;


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "typeID",unique = true, nullable = false)
    public BigInteger getTypeID() {
        return typeID;
    }

    public void setTypeID(BigInteger typeID) {
        this.typeID = typeID;
    }

    @Column(name = "typeName",nullable = false)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
