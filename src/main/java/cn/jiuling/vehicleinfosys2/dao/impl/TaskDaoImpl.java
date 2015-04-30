package cn.jiuling.vehicleinfosys2.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.jiuling.vehicleinfosys2.dao.TaskDao;
import cn.jiuling.vehicleinfosys2.model.Task;
import cn.jiuling.vehicleinfosys2.vo.Constant;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.TaskQuery;

@Repository("taskDao")
public class TaskDaoImpl extends BaseDaoImpl<Task> implements TaskDao {

	@Override
	public void stopAllTask(Integer cameraId) {
		String queryString = "update Task t set t.status=" + Constant.TASK_STATUS_FINISH + " where t.status=" + Constant.TASK_STATUS_UNFINISHED
				+ " and t.type=" + Constant.TASK_TYPE_ONLINE + " and t.cameraId=?";
		super.getHibernateTemplate().bulkUpdate(queryString, cameraId);
	}

	@Override
	public void stopSingleTask(Long vlprTaskId) {
		String queryString = "update VlprTask t set t.flag=" + Constant.VLPRTASK_FLAG_INVALID + " where t.taskId=?";

		super.getHibernateTemplate().bulkUpdate(queryString, vlprTaskId);

	}

	@Override
	public Pager query(TaskQuery q, Integer page, Integer rows) {
		StringBuilder sb = new StringBuilder("from Task t,Camera c where t.cameraId=c.id");
		List paramList = new ArrayList();

		String ids = q.getIds();
		if (!StringUtils.isEmpty(ids)) {
			sb.append(" and c.id in (" + ids + ")");
		}
		String name = q.getName();
		if (!StringUtils.isEmpty(name)) {
			sb.append(" and t.name like '%" + name + "%'");
		}

		Short type = q.getType();
		if (null != type) {
			sb.append(" and t.type=?");
			paramList.add(type);
		}
		Short status = q.getStatus();
		if (null != status) {
			sb.append(" and t.status=?");
			paramList.add(status);
		}

		Timestamp start = q.getStartTime();
		if (null != start) {
			sb.append(" and t.startTime >=?");
			paramList.add(start);
		}
		Timestamp end = q.getEndTime();
		if (null != end) {
			sb.append(" and t.startTime <=?");
			paramList.add(end);
		}
		
		sb.append(" order by startTime desc");

		Object[] paramArr = paramList.toArray();
		Long count = count("select count(t.id) " + sb.toString(), paramArr);
		// String name, Short type, Timestamp startTime, Timestamp endTime,
		// Short status, String cameraName
		List list = super.find("select new cn.jiuling.vehicleinfosys2.vo.TaskVo(" +
				"t.id,t.name,t.type,t.startTime,t.endTime,t.status,c.name" +
				") " + sb.toString(), paramArr, page, rows);
		Pager p = new Pager();
		p.setRows(list);
		p.setTotal(count);
		return p;
	}
}
