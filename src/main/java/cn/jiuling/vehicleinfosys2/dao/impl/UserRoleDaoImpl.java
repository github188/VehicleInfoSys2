package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.UserRoleDao;
import cn.jiuling.vehicleinfosys2.model.UserRole;
import org.springframework.stereotype.Repository;

@Repository("userRoleDao")
public class UserRoleDaoImpl extends BaseDaoImpl<UserRole> implements UserRoleDao {

	@Override
	public void delRoleByUserId(Integer userId) {
		String queryString = "delete UserRole u where u.userId=?";
		super.getHibernateTemplate().bulkUpdate(queryString, userId);
		super.getHibernateTemplate().flush();
	}

}
