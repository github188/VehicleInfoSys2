package cn.jiuling.vehicleinfosys2.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 布控任务定位表
 * @author daixiaowei
 *
 */
@Entity
@Table(name = "surveillance_position")
public class SurveillancePosition {
	private Integer id;
	private Long threadPostion;
	
	@Id
    @Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Basic
    @Column(name = "threadPostion",nullable = false)
	public Long getThreadPostion() {
		return threadPostion;
	}
	public void setThreadPostion(Long threadPostion) {
		this.threadPostion = threadPostion;
	}
}
