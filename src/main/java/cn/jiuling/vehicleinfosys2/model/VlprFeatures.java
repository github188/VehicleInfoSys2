package cn.jiuling.vehicleinfosys2.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "vlpr_features")
public class VlprFeatures implements java.io.Serializable {
	
	private Long id;
    private Long serialNumber;
    private String featureName;
    private String position;
    private String reliability;
    
    
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
  
    @Column(name = "SerialNumber")
    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }
    @Column(name = "featureName")
    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    @Column(name = "reliability")
    public String getReliability() {
        return reliability;
    }

    public void setReliability(String reliability) {
        this.reliability = reliability;
    }
}