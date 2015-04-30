package cn.jiuling.vehicleinfosys2.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jiuling.vehicleinfosys2.dao.ServerDao;
import cn.jiuling.vehicleinfosys2.model.Serverinfo;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@Repository("serverDao")
public class ServerDaompl extends BaseDaoImpl<Serverinfo> implements ServerDao {

	@Override
	public Pager list(Integer page, Integer rows, String sortName, String order) {
		String queryString = " from Serverinfo s order by s." + sortName + " " + order;
		List list = super.find("select new cn.jiuling.vehicleinfosys2.vo.ServerinfoVo(s)"+queryString, null, page, rows);
		Long total = super.count("select count(*)" + queryString, null);
		Pager p = new Pager(total, list);
		return p;
	}

}
