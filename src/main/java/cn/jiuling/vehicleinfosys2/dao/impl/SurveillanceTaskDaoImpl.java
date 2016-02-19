package cn.jiuling.vehicleinfosys2.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.jiuling.vehicleinfosys2.dao.SurveillanceTaskDao;
import cn.jiuling.vehicleinfosys2.model.SurveillanceTask;
import cn.jiuling.vehicleinfosys2.vo.Constant;

@Repository("surveillanceTaskDao")
public class SurveillanceTaskDaoImpl extends BaseDaoImpl<SurveillanceTask> implements SurveillanceTaskDao {


	@Override
    public void delete(SurveillanceTask s, Boolean isAllDelete) {
		if(isAllDelete) {
			String str = "delete s,r from surveillance_task s" +
					" left join vlpr_result r on r.license=s.plate" +
					" where s.id=?";
			
			Object [] params = {s.getId()};
			updateOrDelete(str, params);
		} else {
			super.delete(s);
		}
    }

	@Override
    public void stopSurveillanceTask(Integer taskId) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String queryString = "update SurveillanceTask st set st.status=" + Constant.SURVEILLANCE_STATUS_END 
								+ ",st.endTime='" + timestamp
								+ "' where st.id=?";
		super.getHibernateTemplate().bulkUpdate(queryString, taskId);
    }

	@Override
	public List findSurveillancingPlate() {
	    String queryString = "select st.plate from SurveillanceTask st where st.status=?";
	    List list = super.getHibernateTemplate().find(queryString, Constant.SURVEILLANCE_STATUS_START);
	    return list;
    }

	@Override
	public List findSurveillancingTask() {
		String queryString = "from SurveillanceTask st where st.status in (1,4,5)";
		List list = super.getHibernateTemplate().find(queryString,null);
		return list;
	}

	@Override
	public List findByPlate(String plates, Timestamp timestamp) {
		String queryString = "from VlprResult v where v.license like '% + plates + %' and v.resultTime>=?";
		List list = super.getHibernateTemplate().find(queryString, timestamp);
		return list;
	}

	@Override
	public List findByTask(List list, Timestamp timestamp) {
		for (Object o : list) {
			SurveillanceTask st = (SurveillanceTask)o;
			ResultQuery rq = new ResultQuery();

			rq.setCameraIds(st.getCamera());
			rq.setPlate(st.getPlate());
			rq.setPlateType(ResultVo.getPlatetypes()[st.getPlateType()]);
			rq.setCarColor(st.getCarcolor());
			rq.setVehicleKind(st.getVehicleKind());
			rq.setVehicleBrand(st.getVehicleBrand());
			rq.setVehicleStyle(st.getVehicleStyle());
			rq.setTag(st.getTag());
			rq.setPaper(st.getPaper());
			rq.setSun(st.getSun());
			rq.setDrop(st.getDrop());

		}
		return null;
	}
	
	@Override
    public void updateSurveillanceTaskStatus(Integer taskId,Integer status) {
		String queryString = "update SurveillanceTask st set st.status=" + status + " where st.id=?";
		super.getHibernateTemplate().bulkUpdate(queryString, taskId);
    }
	
	/**
	  * （布控报警页面）布控任务查询查询
	  * @param surveillanceTask
	  * @param page
	  * @param rows
	  * @return
	  */
 	 @Override
	 public Pager queryPracticeTaskList(SurveillanceTask surveillanceTask, Integer page, Integer rows){
 		Pager pager = new Pager(); 
 		List list =this.find(" from SurveillanceTask st where st.status in (1,2,4,5) order by st.doTime asc ", null, page, rows);
 		Long tc = this.count(" select count(*) from SurveillanceTask st where st.status in (1,2,4,5) ", null);
 		Long count=0L;
 		if(tc!=null){
 			pager.setTotal(tc);
 		}else{
 			pager.setTotal(0L);
 		}		
 		pager.setRows(list);
		return pager;
	 }

}
