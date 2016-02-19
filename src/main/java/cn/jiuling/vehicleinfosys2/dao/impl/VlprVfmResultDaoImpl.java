package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.VlprVfmResultDao;
import cn.jiuling.vehicleinfosys2.model.VlprVfmResult;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.SearchCarByImageVo;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository("vlprVfmResultDao")
public class VlprVfmResultDaoImpl extends BaseDaoImpl<VlprVfmResult> implements VlprVfmResultDao {
	@Override
	public Pager querylist(final Integer taskID, Integer page, Integer rows){
		final int firstResult = (page - 1) * rows;
		final int maxResults = rows;
		Pager pager = super.getHibernateTemplate().execute(new HibernateCallback<Pager>() {
			@Override
			public Pager doInHibernate(Session session) throws HibernateException, SQLException {

				String sql = "select v.SerialNumber, v.TaskID, v.License, v.LicenseAttribution, v.PlateColor, v.PlateType,"
						+ " v.Confidence, v.Bright, v.Direction, v.LocationLeft, v.LocationTop, v.LocationRight,"
						+ " v.LocationBottom, v.CostTime, v.CarBright, v.CarColor, v.CarLogo, v.ImagePath,"
						+ " v.ImageURL, v.ResultTime, v.CreateTime, v.frame_index, v.carspeed, v.LabelInfoData,"
						+ " v.vehicleKind, v.vehicleBrand, v.vehicleSeries, v.vehicleStyle, v.tag, v.paper,"
						+ " v.sun, v.`drop`, v.vehicleLeft, v.vehicleTop, v.vehicleRight, v.vehicleBootom,"
						+ " v.vehicleConfidence, vr.id, vr.vfmTaskID, vr.vfmLeft, vr.vfmTop,"
						+ " vr.vfmRight, vr.vfmBottom, vr.insertTime, vr.vfmScore, c.name, vvt.progress"
						+ " from vlpr_vfm_result vr"
						+ " left join vlpr_result v on v.SerialNumber = vr.SerialNumber"
						+ " left join camera c on c.id = v.cameraId"
						+ " left join vlpr_vfm_task vvt on vvt.taskID = vr.vfmTaskID"
						+ " where vr.vfmScore >= 70 and vr.vfmTaskID = " + taskID;

				SQLQuery ds = session.createSQLQuery(sql + " order by vr.vfmScore desc");
				ds.setFirstResult(firstResult);
				ds.setMaxResults(maxResults);
				List list = ds.list();
				/*
				       统计总数量
				 */
				String countSql = "select count(*) from " + sql.split("from")[1];
				SQLQuery cs = session.createSQLQuery(countSql);
				BigInteger count = (BigInteger) cs.uniqueResult();

				List<SearchCarByImageVo> rList = new ArrayList();
				if (list != null && list.size() > 0) {
					rList = new ArrayList<SearchCarByImageVo>();
					for (int i = 0; i < list.size(); i++) {
						Object o[] = (Object[]) list.get(i);
						SearchCarByImageVo r = new SearchCarByImageVo(o);
						rList.add(r);
					}
				}

				Pager p = new Pager();
				p.setRows(rList);
				p.setTotal(count.longValue());
				return p;
			}
		});
		return pager;
	}
	
	@Override
	public Pager queryMunityList(final Integer taskID){
		Pager pager = super.getHibernateTemplate().execute(new HibernateCallback<Pager>() {
			@Override
			public Pager doInHibernate(Session session) throws HibernateException, SQLException {

				String sql = "select v.SerialNumber, v.TaskID, v.License, v.LicenseAttribution, v.PlateColor, v.PlateType,"
						+ " v.Confidence, v.Bright, v.Direction, v.LocationLeft, v.LocationTop, v.LocationRight,"
						+ " v.LocationBottom, v.CostTime, v.CarBright, v.CarColor, v.CarLogo, v.ImagePath,"
						+ " v.ImageURL, v.ResultTime, v.CreateTime, v.frame_index, v.carspeed, v.LabelInfoData,"
						+ " v.vehicleKind, v.vehicleBrand, v.vehicleSeries, v.vehicleStyle, v.tag, v.paper,"
						+ " v.sun, v.`drop`, v.vehicleLeft, v.vehicleTop, v.vehicleRight, v.vehicleBootom,"
						+ " v.vehicleConfidence, vr.id, vr.vfmTaskID, vr.vfmLeft, vr.vfmTop,"
						+ " vr.vfmRight, vr.vfmBottom, vr.insertTime, vr.vfmScore, c.location, vvt.progress"
						+ " from vlpr_vfm_result vr"
						+ " left join vlpr_result v on v.SerialNumber = vr.SerialNumber"
						+ " left join camera c on c.id = v.cameraId"
						+ " left join vlpr_vfm_task vvt on vvt.taskID = vr.vfmTaskID"
						+ " where vr.vfmScore >= 70 and vr.vfmTaskID = " + taskID;

				SQLQuery ds = session.createSQLQuery(sql + " order by vr.vfmScore desc");
				List list = ds.list();
				/*
				       统计总数量
				 */
				String countSql = "select count(*) from " + sql.split("from")[1];
				SQLQuery cs = session.createSQLQuery(countSql);
				BigInteger count = (BigInteger) cs.uniqueResult();

				List<SearchCarByImageVo> rList = new ArrayList();
				if (list != null && list.size() > 0) {
					rList = new ArrayList<SearchCarByImageVo>();
					for (int i = 0; i < list.size(); i++) {
						Object o[] = (Object[]) list.get(i);
						SearchCarByImageVo r = new SearchCarByImageVo(o);
						rList.add(r);
					}
				}

				Pager p = new Pager();
				p.setRows(rList);
				p.setTotal(count.longValue());
				return p;
			}
		});
		return pager;
	}
	
	@Override
	public Integer queryMunityListCount(final Integer taskID){
		Integer count = super.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {

				String sql = "select count(0) "
						+ " from vlpr_vfm_result vr"
						+ " left join vlpr_result v on v.SerialNumber = vr.SerialNumber"
						+ " left join camera c on c.id = v.cameraId"
						+ " left join vlpr_vfm_task vvt on vvt.taskID = vr.vfmTaskID"
						+ " where vr.vfmScore >= 70 and vr.vfmTaskID = " + taskID;
				
				/*
				       统计总数量
				 */
				SQLQuery cs = session.createSQLQuery(sql);
				BigInteger count = (BigInteger) cs.uniqueResult();

				return count.intValue();
			}
		});
		return count;
	}

	@Override
	public Pager originalquerylist(Integer taskID, Integer page, Integer rows){
		StringBuilder sb = new StringBuilder(" from VlprSpeciallyResult v where v.serialNumber = " + taskID);
		Long total = count("select count(*) " + sb.toString(), null);
		List list = super.find(sb.toString(), null, page, rows);
		Pager p = new Pager(total, list);
		return p;
	}

	@Override
	public Pager originalquerylisttask(Integer taskID, Integer page, Integer rows){
		StringBuilder sb = new StringBuilder(" from VlprTask v where v.taskId = " + taskID);
		Long total = count("select count(*) " + sb.toString(), null);
		List list = super.find(sb.toString(), null, page, rows);
		Pager p = new Pager(total, list);
		return p;
	}

	@Override
	public Pager originalquerylisttaskid(Integer taskID, Integer page, Integer rows){
		StringBuilder sb = new StringBuilder(" from VlprVfmTask v where v.taskID = " + taskID);
		Long total = count("select count(*) " + sb.toString(), null);
		List list = super.find(sb.toString(), null, page, rows);
		Pager p = new Pager(total, list);
		return p;
	}
}
