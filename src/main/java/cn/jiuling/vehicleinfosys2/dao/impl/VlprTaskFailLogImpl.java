package cn.jiuling.vehicleinfosys2.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jiuling.vehicleinfosys2.dao.VlprTaskFailLogDao;
import cn.jiuling.vehicleinfosys2.model.VlprTaskFailLog;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@Repository("vlprTaskFailLogDao")
public class VlprTaskFailLogImpl extends BaseDaoImpl<VlprTaskFailLog> implements VlprTaskFailLogDao {

	@Override
	public Pager getFailLogPager(Integer id, int page, int rows) {

		StringBuilder sb = new StringBuilder("from VlprTaskFailLog l, VlprTask vt,Task t where l.vlprTaskId=vt.taskId and vt.taskId=t.vlprTaskId ");

		List paramList = new ArrayList();

		if (id != null && id > 0) {
			sb.append(" and l.id>? ");
			paramList.add(id);
		}
		Object[] paramArr = paramList.toArray();

		List list = super.find("select new cn.jiuling.vehicleinfosys2.vo.FailLogVo(l.id,l.vlprTaskId,l.failTime,l.description,t.name) " + sb.toString()
				+ " order by l.id desc",
				paramArr, page, rows);
		Long count = super.count("select count(*) " + sb.toString(), paramArr);
		Pager p = new Pager(count, list);
		return p;
	}
}
