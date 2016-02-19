package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.ActionprivilegeDao;
import cn.jiuling.vehicleinfosys2.model.Actionprivilege;
import org.springframework.stereotype.Repository;

import java.util.List;

@SuppressWarnings("unchecked")
@Repository("ActionprivilegeDao")
public class ActionprivilegeDaoImpl extends BaseDaoImpl<Actionprivilege> implements ActionprivilegeDao {

	@Override
	public List<Actionprivilege> getUserActionprivilegeList(Integer userId) {
		String queryString = "select a from Actionprivilege a,UserActionprivilege ua where a.actionId=ua.privilegeId and ua.userId=?";
		return super.getHibernateTemplate().find(queryString, userId);
	}

	public List<Actionprivilege> getUserRoleActionprivilegeList(Integer userId) {
		String queryString = "select distinct(a) from Actionprivilege a,UserRole  ur,RoleActionprivilege ra where ur.userId=? and ur.roleId=ra.roleId and ra.privilegeId=a.actionId";
		return super.getHibernateTemplate().find(queryString, userId);
	}

	@Override
	public List<Actionprivilege> getRoleActionprivilegeList(Integer roleId) {
		String queryString = "select a from Actionprivilege a,RoleActionprivilege ra where a.actionId=ra.privilegeId and ra.roleId=?";
		return super.getHibernateTemplate().find(queryString, roleId);
	}

	@Override
	public List<Actionprivilege> getRoleActionprivilegeList(Integer []roleIds) {
		StringBuilder sql = new StringBuilder("select a from Actionprivilege a,RoleActionprivilege ra where a.actionId=ra.privilegeId and ra.roleId in (");
		for(int i = 0; i < roleIds.length; i++){
			sql.append(roleIds[i]+",");
		}
		sql.deleteCharAt(sql.lastIndexOf(","));
		sql.append(")");
		return super.getHibernateTemplate().find(sql.toString(), null);
	}
}
