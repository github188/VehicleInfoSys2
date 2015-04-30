package cn.jiuling.vehicleinfosys2.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jiuling.vehicleinfosys2.dao.DefencerangeDao;
import cn.jiuling.vehicleinfosys2.model.Defencerange;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@Repository("defencerangeDao")
public class DefencerangeDaoImpl extends BaseDaoImpl<Defencerange> implements DefencerangeDao {

	@Override
	public Pager listByUser(User user, Integer page, Integer rows) {
		String queryString = " from Defencerange d where d.userId=?";
		Object[] paramArr = new Object[] { user.getId() };
		List list = super.find(queryString, paramArr, page, rows);
		Long total = super.count("select count(d.id)" + queryString, paramArr);
		Pager p = new Pager(total, list);
		return p;
	}
}
