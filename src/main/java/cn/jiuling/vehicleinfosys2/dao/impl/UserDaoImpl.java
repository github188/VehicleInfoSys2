package cn.jiuling.vehicleinfosys2.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.jiuling.vehicleinfosys2.dao.UserDao;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.UserInfo;
import cn.jiuling.vehicleinfosys2.vo.UserVo;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public Pager listUser(Integer page, Integer rows) {
        final int firstResult = (page - 1) * rows;
        final int maxResults = rows;
        Pager pager = super.getHibernateTemplate().execute(new HibernateCallback<Pager>() {
            @Override
            public Pager doInHibernate(Session session) throws HibernateException, SQLException {

                String sql = "select u.id, u.loginName, u.realName, u.tel, u.dsc, u.departmentId, d.name, u.isValid, u.departmentAdministratorId"
                        +" from user u"
                        +" left join dept d on d.id = u.departmentId";

                SQLQuery ds = session.createSQLQuery(sql);
                ds.setFirstResult(firstResult);
                ds.setMaxResults(maxResults);
                List list = ds.list();
				/*
				       统计总数量
				 */
                String countSql = "select count(*) from " + sql.split("from")[1];
                SQLQuery cs = session.createSQLQuery(countSql);
                long count = ((BigInteger) cs.uniqueResult()).longValue();

                List<UserVo> rList = new ArrayList();

                int size = list.size();
                if (list != null && size > 0) {
                    rList = new ArrayList<UserVo>();
                    for (int i = 0; i < list.size(); i++) {
                        Object o[] = (Object[]) list.get(i);
                        UserVo r = new UserVo(o);
                        rList.add(r);
                    }
                }

                Pager p = new Pager();
                p.setRows(rList);
                p.setTotal(count);
                return p;
            }
        });
        return pager;
	}
	
	public UserInfo getUserInfo(final String loginName) {
		UserInfo result = super.getHibernateTemplate().execute(new HibernateCallback<UserInfo>() {
            @Override
            public UserInfo doInHibernate(Session session) throws HibernateException, SQLException {

                String sql = "select u.id, u.loginName, u.realName, u.tel, u.departmentId, d.name "
                        +" from user u"
                        +" left join dept d on d.id = u.departmentId"
                        +" where u.loginName=?";

                SQLQuery ds = session.createSQLQuery(sql);
                ds.setParameter(0, loginName);
                List list = ds.list();

                UserInfo result = new UserInfo();

                int size = list.size();
                if (list != null && size > 0) {
                	result = new UserInfo((Object[])list.get(0));
                }
                
                return result;
            }
        });
        return result;
	}

	public void delUser(String ids) {
		String queryString = "delete from User u where u.id in(" + ids + ")";
		super.getHibernateTemplate().bulkUpdate(queryString);
	}
}
