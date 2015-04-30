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

import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.model.VlprResult;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;

@Repository("vlprResultDao")
public class VlprResultDaoImpl extends BaseDaoImpl<VlprResult> implements VlprResultDao {
	@Override
	public Pager findByTaskId(final Integer taskId, Integer page, Integer rows) {
		final int firstResult = (page - 1) * rows;
		final int maxResults = rows;
		Pager pager = super.getHibernateTemplate().execute(new HibernateCallback<Pager>() {
			@Override
			public Pager doInHibernate(Session session) throws HibernateException, SQLException {

				String sql = "select v.license,v.plateType,v.plateColor,v.confidence,v.licenseAttribution,v.imageUrl,"
						+ " v.carColor,v.resultTime,c.location,"
						+ " v.direction,c.longitude,c.latitude,vt.filePath as filePath,"
						+ " v.frame_index,t.type as taskType ,d.type ,v.serialNumber," 
						+ " v.vehicleKind,v.vehicleBrand,v.vehicleStyle"
						+ " from vlpr_result v"
						+ " left join vlpr_task vt on vt.TaskID=v.TaskID"
						+ " left join task t on t.vlprTaskId=vt.TaskID"
						+ " left join camera c on c.id=t.cameraId"
						+ " left join datasource d on d.id=t.dataSourceId"
						+ " where t.id=" + taskId;

				SQLQuery ds = session.createSQLQuery(sql + " order by v.serialNumber desc");
				ds.setFirstResult(firstResult);
				ds.setMaxResults(maxResults);
				List list = ds.list();
				/*
				       统计总数量
				 */
				String countSql = "select count(*) from " + sql.split("from")[1];
				SQLQuery cs = session.createSQLQuery(countSql);
				BigInteger count = (BigInteger) cs.uniqueResult();

				List<ResultVo> rList = new ArrayList();
				if (list != null && list.size() > 0) {
					rList = new ArrayList<ResultVo>();
					for (int i = 0; i < list.size(); i++) {
						Object o[] = (Object[]) list.get(i);
						ResultVo r = new ResultVo(o);
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
	public Pager query(final ResultQuery rq, Integer page, Integer rows) {
		final int firstResult = (page - 1) * rows;
		final int maxResults = rows;
		Pager pager = super.getHibernateTemplate().execute(new HibernateCallback<Pager>() {
			@Override
			public Pager doInHibernate(Session session) throws HibernateException, SQLException {

				StringBuilder sbJoin = new StringBuilder("left join vlpr_task vt on vt.TaskID=v.TaskID " +
						"left join task t on t.vlprTaskId=vt.TaskID " +
						"left join camera c on c.id=t.cameraId " +
						"left join datasource d on d.id=t.dataSourceId " +
						"where 1=1 ");
				
				StringBuilder sb = new StringBuilder("select v.license,v.plateType,v.plateColor," +
						"v.confidence,v.licenseAttribution,v.imageUrl," +
						"v.carColor,v.resultTime,c.location, v.direction," +
						"c.longitude,c.latitude,vt.filePath as filePath," +
						"v.frame_index,t.type as taskType ,d.type ,v.serialNumber," +
						"v.vehicleKind,v.vehicleBrand,v.vehicleStyle " +
						"from vlpr_result v ").append(sbJoin);
				
				StringBuilder subSelect = new StringBuilder("SELECT v.SerialNumber FROM vlpr_result v ").append(sbJoin);
				
				StringBuilder sbOrder = new StringBuilder(" ORDER BY v.SerialNumber DESC ");
				
				String cameraIds = rq.getCameraIds().trim();
				if (!StringUtils.isEmpty(cameraIds)) {
					sb.append(" and c.id in (" + cameraIds + ")");
					subSelect.append(" and c.id in (" + cameraIds + ")");
				}
				List paramList = new ArrayList();
				List subParamList = new ArrayList();
				Timestamp start = rq.getStartTime();
				if (null != start) {
					sb.append(" and v.ResultTime>=?");
					subSelect.append(" and v.ResultTime>=?");
					paramList.add(start);
				}
				Timestamp end = rq.getEndTime();
				if (null != end) {
					sb.append(" and v.ResultTime<=?");
					subSelect.append(" and v.ResultTime<=?");
					paramList.add(end);
				}
				String plate = rq.getPlate().trim();
				if (!StringUtils.isEmpty(plate)) {
					plate = plate.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.License like '%" + plate + "%'");
					subSelect.append(" and v.License like '%" + plate + "%'");
				}
				String location = rq.getLocation().trim();
				if (!StringUtils.isEmpty(location)) {
					location = location.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and c.location like '%" + location + "%'");
					subSelect.append(" and c.location like '%" + location + "%'");
				}
				String plateColor = rq.getPlateColor().trim();
				if (!StringUtils.isEmpty(plateColor)) {
					plateColor = plateColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateColor like '%" + plateColor + "%'");
					subSelect.append(" and v.PlateColor like '%" + plateColor + "%'");
				}
				String carColor = rq.getCarColor().trim();
				if (!StringUtils.isEmpty(carColor)) {
					carColor = carColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.CarColor like '%" + carColor + "%'");
					subSelect.append(" and v.CarColor like '%" + carColor + "%'");
				}
				String direction = rq.getDirection();
				if (!StringUtils.isEmpty(direction)) {
					sb.append(" and v.Direction =?");
					subSelect.append(" and v.Direction =?");
					paramList.add(direction);
				}
				String vehicleKind = rq.getVehicleKind();
				if (!StringUtils.isEmpty(vehicleKind)) {
					vehicleKind = vehicleKind.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleKind like '" + vehicleKind + "'");
					subSelect.append(" and v.VehicleKind like '" + vehicleKind + "'");
				}
				String vehicleBrand = rq.getVehicleBrand();
				if (!StringUtils.isEmpty(vehicleBrand)) {
					vehicleBrand = vehicleBrand.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleBrand like '%" + vehicleBrand + "%'");
					subSelect.append(" and v.VehicleBrand like '%" + vehicleBrand + "%'");
				}
				String vehicleStyle = rq.getVehicleStyle();
				if (!StringUtils.isEmpty(vehicleStyle)) {
					vehicleStyle = vehicleStyle.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleStyle like '%" + vehicleStyle + "%'");
					subSelect.append(" and v.VehicleStyle like '%" + vehicleStyle + "%'");
				}
				Object[] paramArr = paramList.toArray();

				String sql = sb.toString();
				/*
				   统计总数量
				*/
				String countSql = "select count(*) from " + sql.split("from")[1];
				SQLQuery cs = session.createSQLQuery(countSql);
				if (paramArr.length > 0) {
					for (int i = 0; i < paramArr.length; i++) {
						cs.setParameter(i, paramArr[i]);
						
					}
				}
				
				long count = ((BigInteger) cs.uniqueResult()).longValue();
				int _firstResult = firstResult;
				
				StringBuilder sbAnd = new StringBuilder(" AND serialNumber<=");
				int equalIndex = sbAnd.length()-1;
				
				String order = "desc";
				int limitNum = firstResult;
				int offset = (int) (count - firstResult);
				
				if(offset < 0) {
					offset = 0;
				}
					
				if (offset < firstResult) {
					sbAnd=sbAnd.deleteCharAt(equalIndex);
					order = "asc";
					limitNum = offset;
				}
				
				StringBuilder subOrder = new StringBuilder(" ORDER BY v.SerialNumber " + order + " LIMIT " + limitNum + ",1");
				_firstResult = 0;

				String queryString = sb.append(sbAnd + "(" + subSelect.append(subOrder)+")" + sbOrder).toString();
				SQLQuery ds = session.createSQLQuery(queryString);
				
				
				if (paramArr.length > 0) {
					for (int i = 0; i < paramArr.length; i++) {
						ds.setParameter(i, paramArr[i]);
						ds.setParameter(paramArr.length+i, paramArr[i]);
					}
				}
				ds.setFirstResult(_firstResult);
				ds.setMaxResults(maxResults);
				List list = ds.list();

				List<ResultVo> rList = new ArrayList();
				if (list != null && list.size() > 0) {
					rList = new ArrayList<ResultVo>();
					for (int i = 0; i < list.size(); i++) {
						Object o[] = (Object[]) list.get(i);
						ResultVo r = new ResultVo(o);
						rList.add(r);
					}
				}

				Pager p = new Pager();
				p.setRows(rList);
				p.setTotal(count);
				return p;
			}
		});
		return pager;
	}

	@Override
	public ResultVo findResult(final Long id) {
		/*
		 * String license, Short plateType, String plateColor, Short confidence, String licenseAttribution, String imageUrl, String carColor, Date resultTime,
			String location, Short direction, Double longitude, Double latitude, String path, Long frame_index, Short taskType, Integer dataSourceId
		 */
		List list = super.getHibernateTemplate().executeFind(new HibernateCallback() {
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				int firstResult = 0;
				int maxResults = 1;
				String sql = "select v.license,v.plateType,v.plateColor,v.confidence,v.licenseAttribution,v.imageUrl,"
						+ " v.carColor,v.resultTime,c.location,"
						+ " v.direction,c.longitude,c.latitude,vt.filePath as filePath,v.frame_index,t.type as taskType ,d.type ,v.serialNumber," 
						+ " v.vehicleKind,v.vehicleBrand,v.vehicleStyle"
						+ " from vlpr_result v,vlpr_task vt,camera c,task t"
						+ " left join datasource d"
						+ " on d.id=t.dataSourceId"
						+ " where v.serialNumber=" + id + " and v.TaskID=vt.TaskID and vt.TaskID=t.vlprTaskId and t.cameraId =c.id";

				SQLQuery ds = session.createSQLQuery(sql);
				ds.setFirstResult(firstResult);
				ds.setMaxResults(maxResults);
				List list = ds.list();
				/*
				       统计总数量
				  String countSql = "select count(*) from " +sql.split("from")[1];
				  SQLQuery cs =session.createSQLQuery(sql);
				  BigInteger count = (BigInteger)
				  cs.uniqueResult();
				  */
				return list;
			}
		});

		if (list.size() > 0) {
			Object o[] = (Object[]) list.get(0);
			ResultVo r = new ResultVo(o);
			return r;
		}
		return null;
	}
}
