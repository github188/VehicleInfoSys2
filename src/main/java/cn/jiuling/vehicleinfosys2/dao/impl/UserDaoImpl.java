package cn.jiuling.vehicleinfosys2.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jiuling.vehicleinfosys2.dao.UserDao;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public Pager listUser(Integer page, Integer rows) {
		// Integer id, String name, String loginName, String tel, String dsc
		String queryString = " from User u";
		List list = find("select new cn.jiuling.vehicleinfosys2.vo.UserVo(u.id,u.name,u.loginName,u.tel,u.dsc)" + queryString, null, page, rows);
		Long count = count("select count(u.id)" + queryString, null);
		Pager p = new Pager();
		p.setRows(list);
		p.setTotal(count);
		return p;
	}

	public void delUser(String ids) {
		String queryString = "delete from User u where u.id in(" + ids + ")";
		super.getHibernateTemplate().bulkUpdate(queryString);
	}
}
