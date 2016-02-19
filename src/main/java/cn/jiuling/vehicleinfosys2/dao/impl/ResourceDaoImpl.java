package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.ResourceDao;
import cn.jiuling.vehicleinfosys2.framework.spring.support.CustomerContextHolder;
import cn.jiuling.vehicleinfosys2.model.Datasource;
import cn.jiuling.vehicleinfosys2.vo.Constant;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("resourceDao")
public class ResourceDaoImpl extends BaseDaoImpl<Datasource> implements ResourceDao {
	@Override
	public Pager listByIds(Integer[] ids, Integer page, Integer rows) {
		StringBuilder sb = new StringBuilder("from Datasource r where r.type=" + Constant.DATASOURCE_TYPE_VIDEO + " and r.cameraId in (");
		for (int i = 0; i < ids.length; i++) {
			if (i != 0)
				sb.append(",");
			sb.append(ids[i]);
		}
		sb.append(")");
		Long total = count("select count(*) " + sb.toString(), null);
		List list = super.find(sb.toString(), null, page, rows);
		Pager p = new Pager(total, list);
		return p;
	}

	@Override
	public Pager list(Integer[] ids, String location, Integer page, Integer rows) {
		StringBuilder sb = new StringBuilder("from Datasource r,Camera c where r.cameraId=c.id and r.type=" + Constant.DATASOURCE_TYPE_VIDEO);
		if (null != ids && ids.length > 0) {
			sb.append(" and r.cameraId in (");
			for (int i = 0; i < ids.length; i++) {
				if (i != 0)
					sb.append(",");
				sb.append(ids[i]);
			}
			sb.append(")");
		}

		if (!StringUtils.isEmpty(location)) {
			sb.append(" and c.location like '%" + location + "%'");
		}
		Long total = count("select count(r.id) " + sb.toString(), null);
		List list = super.find("select r " + sb.toString(), null, page, rows);
		Pager p = new Pager(total, list);
		return p;
	}

	@Override
	public void delete(Datasource d, Boolean isAllDelete) {
		if(isAllDelete) {
			String str = "delete d,t,v,r from datasource d" +
					" left join task t on t.dataSourceId=d.id" +
					" left join vlpr_task v on v.TaskID=t.vlprTaskId" +
					" left join vlpr_result r on r.TaskID=v.TaskID" +
					" where d.id=?";

			Object [] params = {d.getId()};
			updateOrDelete(str, params);
		} else {
			super.delete(d);
		}
	}

	@Override
	public String findBigPicFromTheLast(Integer id) {
		StringBuilder sb = new StringBuilder("from Datasource d where d.type=1 and d.cameraId="+id);
		List list = super.find(sb.toString(), null, 1, 1);
		String url = null;
		if(list != null && list.size()>0) {
			Datasource d=(Datasource) list.get(0);
			url = d.getBigUrl();
		}
		return url;
	}

	@Override
    public Pager findByParentId(final Integer parentId, Integer page, Integer rows) {

		StringBuilder sb = new StringBuilder("from Datasource d where d.parentId=" + parentId);

		/*
		 *   统计总数量
		 */
		Long total = count("select count(*) " + sb.toString(), null);

		List list = super.find("select d "+sb.toString(), null, page, rows);
		return new Pager(total, list);

    }

	@Override
	public Pager fromRemoteDB(final Integer page, final Integer rows) {
		Pager pager = super.getHibernateTemplate().execute(new HibernateCallback<Pager>() {
			@Override
			public Pager doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder sb = new StringBuilder("select * from Server_Info s where s.server_id=1");

				SQLQuery ds = session.createSQLQuery(sb.toString());
				List list = ds.list();

				return new Pager((long)(list.size()),list);
			}
		});

		//查找完成清除oracle数据源
		CustomerContextHolder.clearCustomerType();
		return pager;
	}
	
	/**
	 * 获取最后插入id
	 * @return Integer
	 */
	@Override
	public BigInteger getLastInsertId(){	
		BigInteger lastId = super.getHibernateTemplate().execute(new HibernateCallback<BigInteger>() {
			@Override
			public BigInteger doInHibernate(Session session) throws HibernateException, SQLException {
				String hql = "select LAST_INSERT_ID()";
				SQLQuery cs = session.createSQLQuery(hql);
				BigInteger ld = (BigInteger) cs.uniqueResult();
				
				return ld;
			}
		});
		return lastId;	
	}
}
