package cn.jiuling.vehicleinfosys2.vo;

import java.util.List;

public class Pager {
	private Long total;
	private List rows;

	public Pager() {
		super();
	}

	public Pager(Long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
