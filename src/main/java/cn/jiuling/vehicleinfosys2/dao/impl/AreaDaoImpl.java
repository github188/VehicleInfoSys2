package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.AreaDao;
import cn.jiuling.vehicleinfosys2.model.Area;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("areaDao")
public class AreaDaoImpl extends BaseDaoImpl<Area> implements AreaDao {

	public List getAreaTree(Integer id) {
		String queryString;
		String query;

		if (null == id) {
			id = 0;
		}
		query = id + "=d1.parentId and d1.id>d1.parentId ";

		queryString = "select new cn.jiuling.vehicleinfosys2.vo.Tree(d1.id ,d1.name, " +
				"sum(case when d2.parentId=d1.id then 1 else 0 end) as state)" +
				" from Area d1,Area d2 where " + query
				+ " and d1.isEnable=1 and d2.isEnable=1 group by d1.id";
		List list = super.getHibernateTemplate().find(queryString);
		return list;
	}

    @Override
    public List<Area> getAncestor(String ancestorId) {
        String queryString;
        String query;

        query = " a.id in("+ancestorId+")";
        queryString = "select new cn.jiuling.vehicleinfosys2.model.Area(a.name, a.isEnable) from Area a where"+query;

        List<Area> list = super.getHibernateTemplate().find(queryString);
        return list;
    }

    @Override
	public List getAreaTreeEnable(Integer id) {
		String queryString;
		String query;

		if (null == id) {
			id = 0;
		}
		query = id + "=d1.parentId and d1.id>d1.parentId ";

		queryString = "select new cn.jiuling.vehicleinfosys2.vo.Tree(d1.id ,d1.name, " +
				"sum(case when d2.parentId=d1.id then 1 else 0 end) as state)" +
				" from Area d1,Area d2 where " + query
				+ " and d1.isEnable=1 and d2.isEnable=1 group by d1.id";
		List list = super.getHibernateTemplate().find(queryString);
		return list;
	}

	public void updateChildren(Integer id, String parentName) {
		String queryString = "update Area a set a.parentName=? where a.parentId=?";
		super.getHibernateTemplate().bulkUpdate(queryString, new Object[] { parentName, id });
	}

	public Pager findAreaChildren(Integer id, Integer page, Integer rows) {
		String hql = " from Area d where d.ancestorId like ? escape '/'";
		Object[] paramArr = new Object[] { "%/," + id + "/,%" };
		Long total = 0L;
		if (page!=null && rows!=null &&page > 0 && rows > 0) {
			total = count("select count(*)" + hql, paramArr);
		}
		List list = find("select d" + hql, paramArr, page, rows);
		return new Pager(total, list);
	}

	public void disableChildren(Integer id) {
		String queryString = "update Area d set d.isEnable=0 where d.ancestorId like ? escape '/'";
		super.getHibernateTemplate().bulkUpdate(queryString, "%/," + id + "/,%");
	}

}
