package cn.jiuling.vehicleinfosys2.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.jiuling.vehicleinfosys2.dao.ResourceDao;
import cn.jiuling.vehicleinfosys2.model.Datasource;
import cn.jiuling.vehicleinfosys2.vo.Constant;
import cn.jiuling.vehicleinfosys2.vo.Pager;

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

}
