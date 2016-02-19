package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.UserActionprivilegeDao;
import cn.jiuling.vehicleinfosys2.model.UserActionprivilege;
import org.springframework.stereotype.Repository;

@Repository("userActionprivilegeDao")
public class UserActionprivilegeDaoImpl extends BaseDaoImpl<UserActionprivilege> implements UserActionprivilegeDao {

	public void delActionprivilegeByUserId(Integer userId) {
		String queryString = "delete UserActionprivilege u where u.userId=?";
		super.getHibernateTemplate().bulkUpdate(queryString, userId);
		super.getHibernateTemplate().flush();
	}
}
