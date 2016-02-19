package cn.jiuling.vehicleinfosys2.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.jiuling.vehicleinfosys2.dao.VlprTaskDao;
import cn.jiuling.vehicleinfosys2.model.VlprTask;
import cn.jiuling.vehicleinfosys2.vo.Constant;

@Repository("vlprTaskDao")
public class VlprTaskDaoImpl extends BaseDaoImpl<VlprTask> implements
        VlprTaskDao {

    @Override
    public VlprTask findStartedRealTimeTask(Integer cameraId) {
        List list = findStartedRealTimeTasks(cameraId);
        if (list.size() > 0) {
            return (VlprTask) list.get(0);
        }
        return null;
    }

    @Override
    public List findStartedRealTimeTasks(Integer cameraId) {
        String queryString = "select v from VlprTask v,Task t where v.taskId=t.vlprTaskId and t.cameraId=? and v.flag=" + Constant.VLPRTASK_FLAG_VALID
                + " and t.type=" + Constant.TASK_TYPE_ONLINE + " and ( v.status=" + Constant.VLPRTASK_STATUS_WAIT + " or v.status=" + Constant.VLPRTASK_STATUS_PROCESSING
                + ") order by t.createTime desc";
        List list = getHibernateTemplate().find(queryString, cameraId);
        return list;
    }

    @Override
    public VlprTask findStartedRealImageTask(Integer cameraId) {
        List list = findStartedRealImageTasks(cameraId);
        if (list.size() > 0) {
            return (VlprTask) list.get(0);
        }
        return null;
    }

    @Override
    public List findStartedRealImageTasks(Integer cameraId) {
        String queryString = "select v from VlprTask v,Task t where v.taskId=t.vlprTaskId and t.cameraId=? and v.flag=" + Constant.VLPRTASK_FLAG_VALID
                + " and t.type=" + Constant.TASK_TYPE_REALIMAGE + " and ( v.status=" + Constant.VLPRTASK_STATUS_WAIT + " or v.status=" + Constant.VLPRTASK_STATUS_PROCESSING
                + ") order by t.createTime desc";
        List list = getHibernateTemplate().find(queryString, cameraId);
        return list;
    }


    @Override
    public void stopAllTask(Integer cameraId) {
        final String queryString = "update t2 set t2.flag=" + Constant.VLPRTASK_FLAG_INVALID
                + " form Task t,Vlpr_Task t2 where t.vlprTaskId=t2.taskId and  t.status="
                + Constant.TASK_STATUS_UNFINISHED
                + " and t.type=" + Constant.TASK_TYPE_ONLINE + " and t.cameraId=?";

        final Integer cId = cameraId;
        log.info(queryString);
        super.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                SQLQuery sql = session.createSQLQuery(queryString);
                sql.setInteger(0, cId);
                sql.executeUpdate();
                return null;
            }
        });
    }
}
