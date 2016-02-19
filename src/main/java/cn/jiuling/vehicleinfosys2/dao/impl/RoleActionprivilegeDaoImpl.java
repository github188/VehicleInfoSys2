package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.RoleActionprivilegeDao;
import cn.jiuling.vehicleinfosys2.model.RoleActionprivilege;
import org.springframework.stereotype.Repository;

@Repository("RoleActionprivilegeDao")
public class RoleActionprivilegeDaoImpl extends BaseDaoImpl<RoleActionprivilege> implements RoleActionprivilegeDao {
	
	@Override
	public void deleteByRoleId(Integer roleId) {
		String queryString = "delete RoleActionprivilege r where r.roleId=?";
		super.getHibernateTemplate().bulkUpdate(queryString, roleId);
		super.getHibernateTemplate().flush();
	}
}
