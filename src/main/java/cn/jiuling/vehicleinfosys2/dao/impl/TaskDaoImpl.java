package cn.jiuling.vehicleinfosys2.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
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
        String s = "from Task t,Camera c,VlprTask vt ";
        String s1 = " where t.cameraId=c.id and vt.taskId=t.vlprTaskId ";

        StringBuilder sb = new StringBuilder(s).append(s1);

		List paramList = new ArrayList();

        String offlinevideoName = q.getOfflineVideoName();
        if (!StringUtils.isEmpty(offlinevideoName)) {
            //按视频名查找
            sb = new StringBuilder(s).append(",Datasource d ").append(s1).append(" and t.dataSourceId=d.id and t.dataSourceId is not null");

            offlinevideoName = offlinevideoName.replaceAll("\\*", "%").replaceAll("\\?", "_");
            StringBuilder sb2 = new StringBuilder();
            if(offlinevideoName.contains(",")) {
                String[] str = offlinevideoName.split(",");
                int length = str.length;
                for (int i = 0; i < length; i++) {
                    sb2.append("'" + str[i] + "',");
                }
                sb2.deleteCharAt(sb2.lastIndexOf(","));
            }
            if (sb2.length() > 0) {
                offlinevideoName = sb2.toString();
                sb.append(" and d.name in (" + offlinevideoName + ")");
            } else {
                sb.append(" and d.name in ('" + offlinevideoName + "')");
            }
        }

		String ids = q.getIds();
		if (!StringUtils.isEmpty(ids)) {
			sb.append(" and c.id in (" + ids + ")");
		}
		Integer dataSourceId = q.getDataSourceId();
		if (!StringUtils.isEmpty(dataSourceId)) {
			sb.append(" and t.dataSourceId=" + dataSourceId);
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
			sb.append(" and t.endTime <=?");
			paramList.add(end);
		}

		sb.append(" order by startTime desc");

		Object[] paramArr = paramList.toArray();
		Long count = count("select count(t.id) " + sb.toString(), paramArr);
		// String name, Short type, Timestamp startTime, Timestamp endTime,
		// Short status, String cameraName
		List list = super.find("select new cn.jiuling.vehicleinfosys2.vo.TaskVo(" +
				"t.id,t.name,t.type,vt.progress,t.startTime,t.endTime,t.status,c.name" +
				") " + sb.toString(), paramArr, page, rows);

		Pager p = new Pager();
		p.setRows(list);
		p.setTotal(count);
		return p;
	}

	@Override
    public List<Object[]> queryByIds(final List ids) {
		
		List<Object[]> task_result=super.getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
			@Override
            public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
				String queryString = "select t.id,count(v.serialNumber) from task t" +
				" left join vlpr_task vt on t.vlprTaskId=vt.TaskID "+				
				" left join vlpr_result v on vt.TaskID=v.TaskID"+
				" where t.id in (:alist) group by t.id";
				
				SQLQuery cs = session.createSQLQuery(queryString);
				cs.setParameterList("alist",ids);
				
	            return cs.list();
            }
		});
		return task_result;
    }
}
