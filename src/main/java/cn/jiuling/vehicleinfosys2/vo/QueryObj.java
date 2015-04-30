package cn.jiuling.vehicleinfosys2.vo;

import java.util.ArrayList;
import java.util.List;

public class QueryObj {
	private String query;
	private List list;

	public QueryObj() {
		super();
		this.query = "";
		this.list = new ArrayList();
	}

	public QueryObj(String query, List list) {
		super();
		this.query = query;
		this.list = list;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
}
