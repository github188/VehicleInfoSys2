package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.DeptDao;
import cn.jiuling.vehicleinfosys2.model.Dept;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("deptDao")
public class DeptDaoImpl extends BaseDaoImpl<Dept> implements DeptDao {

	public List getDeptRootTree() {
		String queryString;
		queryString = "select new cn.jiuling.caselib.vo.Tree(d1.id ,d1.name, " +
					"case when count(d2.id)>1 then 'closed' else 'open' end as state)" +
					" from Dept d1,Dept d2 where d1.id=d1.parentId "
				+ " and d1.id=d2.parentId and d1.isEnable=1 and d2.isEnable=1 group by d1.id";

		List list = super.getHibernateTemplate().find(queryString);
		return list;
	}

	public List getDeptTree(Integer id) {
		String queryString;
		String query;

		if (null == id) {
			id = 0;
		}
		query = id + "=d1.parentId and d1.id>d1.parentId ";

		queryString = "select new cn.jiuling.vehicleinfosys2.vo.Tree(d1.id ,d1.name, " +
				"sum(case when d2.parentId=d1.id then 1 else 0 end) as state)" +
				" from Dept d1,Dept d2 where " + query
				+ " and d1.isEnable=1 and d2.isEnable=1 group by d1.id";
		List list = super.getHibernateTemplate().find(queryString);
		return list;
	}

	public void updateChildDept(Integer parentId, String parentName) {
		String queryString = "update Dept d set d.parentName=? where d.parentId=?";
		super.getHibernateTemplate().bulkUpdate(queryString, new Object[] { parentName, parentId });
	}

	public Pager findDeptChildren(Integer deptId, Integer page, Integer rows) {
		String hql = " from Dept d where d.ancestorId like ? escape '/'";
		Object[] paramArr = new Object[] { "%/," + deptId + "/,%" };
		// 统计数量
		Long total = 0L;
		if (page > 0 && rows > 0) {
			total = count("select count(*)" + hql, paramArr);
		}
		// 取得记录数
		List list = find("select d" + hql, paramArr, page, rows);
		return new Pager(total, list);
	}

	public void disableChildren(Integer id) {
		String queryString = "update Dept d set d.isEnable=0 where d.ancestorId like ? escape '/'";
		super.getHibernateTemplate().bulkUpdate(queryString, "%/," + id + "/,%");
	}
}
