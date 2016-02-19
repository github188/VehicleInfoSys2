package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.RoleDao;
import cn.jiuling.vehicleinfosys2.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {
	public List<Role> getUserRoleList(Integer userId) {
		String queryString = "select r from UserRole ur,Role r where ur.userId=? and ur.roleId=r.id and r.status=1";
		return super.getHibernateTemplate().find(queryString, userId);
	}

    public List<Role> getRole() {
        String queryString = "select r from Role r where r.status=1";
        return super.getHibernateTemplate().find(queryString);
    }

    public void delete(Integer id) {
        String sql = " delete from UserRole u where u.roleId = ?";
        super.getHibernateTemplate().bulkUpdate(sql, id);
    }

}
