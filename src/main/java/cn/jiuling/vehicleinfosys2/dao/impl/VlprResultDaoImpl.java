package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.model.VlprResult;
import cn.jiuling.vehicleinfosys2.util.DateUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

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
						+ " v.vehicleKind,v.vehicleBrand,v.vehicleStyle,"
						+ "v.locationLeft,v.locationTop,v.locationRight,v.locationBottom, "
						+ "v.tag, v.paper, v.drop, v.sun, v.vehicleSeries, c.id, c.ip, v.carspeed,v.carLogo,v.ImagePath,v.vehicleLeft,v.vehicleTop,v.vehicleRight,v.vehicleBootom "
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
                long count = ((BigInteger) cs.uniqueResult()).longValue();

                //查询特征物识别结果表
                String featuresSelect = "select featureName,position,reliability from vlpr_features where SerialNumber = ?";
                //特征物识别查询
                SQLQuery featuresQuery = session.createSQLQuery(featuresSelect);

                List<ResultVo> rList = new ArrayList();

                int size = list.size();
                if (list != null && size > 0) {
                    rList = new ArrayList<ResultVo>();
                    for (int i = 0; i < size; i++) {
                        Object o[] = (Object[]) list.get(i);
                        ResultVo r = new ResultVo(o);

                        /**
                         * 插入特征物识别信息
                         */
                        //获取SerialNumber
                        long serialNumber = ((BigInteger) o[16]).longValue();
                        featuresQuery.setParameter(0, serialNumber);
                        //查询
                        List featuresList = featuresQuery.list();

                        //向ResultVo对象中填充特征物识别信息
                        fillFeatureInfo(r,featuresList);

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
	public Pager findMunityByTaskId(final Integer taskId) {
		Pager pager = super.getHibernateTemplate().execute(new HibernateCallback<Pager>() {
			@Override
			public Pager doInHibernate(Session session) throws HibernateException, SQLException {

				String sql = "select v.license,v.plateType,v.plateColor,v.confidence,v.licenseAttribution,v.imageUrl,"
						+ " v.carColor,v.resultTime,c.location,"
						+ " v.direction,c.longitude,c.latitude,vt.filePath as filePath,"
						+ " v.frame_index,t.type as taskType ,d.type ,v.serialNumber," 
						+ " v.vehicleKind,v.vehicleBrand,v.vehicleStyle,"
						+ "v.locationLeft,v.locationTop,v.locationRight,v.locationBottom, "
						+ "v.tag, v.paper, v.drop, v.sun, v.vehicleSeries, c.id, c.ip, v.carspeed,v.carLogo,v.ImagePath,v.vehicleLeft,v.vehicleTop,v.vehicleRight,v.vehicleBootom "
						+ " from vlpr_result v"
						+ " left join vlpr_task vt on vt.TaskID=v.TaskID"
						+ " left join task t on t.vlprTaskId=vt.TaskID"
						+ " left join camera c on c.id=t.cameraId"
						+ " left join datasource d on d.id=t.dataSourceId"
						+ " where t.id=" + taskId;

				SQLQuery ds = session.createSQLQuery(sql + " order by v.serialNumber desc");
				List list = ds.list();
				/*
				       统计总数量
				 */
				String countSql = "select count(*) from " + sql.split("from")[1];
				SQLQuery cs = session.createSQLQuery(countSql);
				BigInteger count = (BigInteger) cs.uniqueResult();

				List<ResultVo> rList = new ArrayList();
                int size = list.size();
                if (list != null && size > 0) {
					rList = new ArrayList<ResultVo>();
					for (int i = 0; i < size; i++) {
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
		final int firstResult = page == null ? 0 :(page - 1) * rows;
		final int maxResults = rows == null ? 0 : rows;
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
						"v.carColor,v.resultTime,c.name,v.direction," +
						"c.longitude,c.latitude,vt.filePath as filePath," +
						"v.frame_index,t.type as taskType ,d.type ,v.serialNumber," +
						"v.vehicleKind,v.vehicleBrand,v.vehicleStyle," +
						"v.locationLeft,v.locationTop,v.locationRight,v.locationBottom," +
						"v.tag, v.paper, v.drop, v.sun , v.vehicleSeries, c.id, c.ip, v.carspeed,v.carLogo,v.ImagePath,v.vehicleLeft,v.vehicleTop,v.vehicleRight,v.vehicleBootom   " +
						"from vlpr_result v ").append(sbJoin);
				
				//查询特征物识别结果表
				String featuresSelect = "select featureName,position,reliability from vlpr_features where SerialNumber = ?";
				//特征物识别查询
				SQLQuery featuresQuery = session.createSQLQuery(featuresSelect);
				
				StringBuilder subSelect = new StringBuilder("SELECT v.SerialNumber FROM vlpr_result v ").append(sbJoin);
				
				StringBuilder sbOrder = new StringBuilder(" ORDER BY v.SerialNumber DESC ");

				String cameraIds = rq.getCameraIds();

				if (!StringUtils.isEmpty(cameraIds)) {
                    cameraIds = cameraIds.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and c.id in (" + cameraIds + ")");
					subSelect.append(" and c.id in (" + cameraIds + ")");
				}

				String characteristic = rq.getCharacteristic();
				if (!StringUtils.isEmpty(characteristic)) {
					String[] characteristics = characteristic.split(",");
					for (String character : characteristics) {
						sb.append(" and v."+character+"=1 ");
						subSelect.append(" and v."+character+"=1 ");
                    }
                }
				
				List paramList = new ArrayList();
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
				String plate = rq.getPlate();
				if(rq.isAccurate()){
					if (!StringUtils.isEmpty(plate)) {						
						sb.append(" and v.License like '" + plate + "%'");
						subSelect.append(" and v.License like '" + plate + "%'");
					}
				}else{
					if (!StringUtils.isEmpty(plate)) {
						plate = plate.replaceAll("\\*", "%").replaceAll("\\?", "_");
						sb.append(" and v.License like '%" + plate + "%'");
						subSelect.append(" and v.License like '%" + plate + "%'");
					}
				}				
				String location = rq.getLocation();
				if (!StringUtils.isEmpty(location)) {
					location = location.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and c.location like '%" + location + "%'");
					subSelect.append(" and c.location like '%" + location + "%'");
				}
				String plateColor = rq.getPlateColor();
				if (!StringUtils.isEmpty(plateColor)) {
					plateColor = plateColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateColor like '%" + plateColor + "%'");
					subSelect.append(" and v.PlateColor like '%" + plateColor + "%'");
				}
				String plateType = rq.getPlateType();
				if (!StringUtils.isEmpty(plateType)) {
					plateType = plateType.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateType =" + plateType);
					subSelect.append(" and v.PlateType =" + plateType);
				}
				String carColor = rq.getCarColor();
				if (!StringUtils.isEmpty(carColor)) {
					carColor = carColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					String[] carColorNames = carColor.split(",");
					StringBuffer carColorNamePara = new StringBuffer();

					for(int i=0;i<carColorNames.length;i++){
						if(i==carColorNames.length-1){
							carColorNamePara.append("'"+carColorNames[i]+"'");
						}else{
							carColorNamePara.append("'"+carColorNames[i]+"',");
						}
					}
					sb.append(" and v.CarColor in (" + carColorNamePara.toString() + ")");
					subSelect.append(" and v.CarColor in (" + carColorNamePara.toString() + ")");
				}
				String direction = rq.getDirection();
				if (!StringUtils.isEmpty(direction)) {
					sb.append(" and v.Direction =?");
					subSelect.append(" and v.Direction =?");
					paramList.add(direction);
				}
				String vehicleKind = rq.getVehicleKind();
				if (!StringUtils.isEmpty(vehicleKind)) {
					if(vehicleKind.equals("未识别")){
						sb.append(" and v.VehicleKind = ''");
						subSelect.append(" and v.VehicleKind = ''");
					}else{
						vehicleKind = vehicleKind.replaceAll("\\*", "%").replaceAll("\\?", "_");
						sb.append(" and v.VehicleKind like '" + vehicleKind + "'");
						subSelect.append(" and v.VehicleKind like '" + vehicleKind + "'");
					}
				}
				String vehicleBrand = rq.getVehicleBrand();
				if (!StringUtils.isEmpty(vehicleBrand)) {
					vehicleBrand = vehicleBrand.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleBrand like '%" + vehicleBrand + "%'");
					subSelect.append(" and v.VehicleBrand like '%" + vehicleBrand + "%'");
				}
				String vehicleSeries = rq.getVehicleSeries();
				if (!StringUtils.isEmpty(vehicleSeries)) {
					vehicleSeries = vehicleSeries.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleSeries like '%" + vehicleSeries + "%'");
					subSelect.append(" and v.VehicleSeries like '%" + vehicleSeries + "%'");
				}
				String vehicleStyle = rq.getVehicleStyle();
				if (!StringUtils.isEmpty(vehicleStyle)) {
					vehicleStyle = vehicleStyle.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleStyle like '%" + vehicleStyle + "%'");
					subSelect.append(" and v.VehicleStyle like '%" + vehicleStyle + "%'");
				}
				String license = rq.getLicense();
				if (!StringUtils.isEmpty(license)) {
					license = license.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.License like '%" + license + "%'");
					subSelect.append(" and v.License like '%" + license + "%'");
				}
				Short tag = rq.getTag();
				if (tag != null) {
					sb.append(" and v.tag =?");
					subSelect.append(" and v.tag =?");
					paramList.add(tag);
				}
				Short paper = rq.getPaper();
				if (paper != null) {
					sb.append(" and v.paper =?");
					subSelect.append(" and v.paper =?");
					paramList.add(paper);
				}
				Short sun = rq.getSun();
				if (sun != null) {
					sb.append(" and v.sun =?");
					subSelect.append(" and v.sun =?");
					paramList.add(sun);
				}
				Short drop = rq.getDrop();
				if (drop != null) {
					sb.append(" and v.drop =?");
					subSelect.append(" and v.drop =?");
					paramList.add(drop);
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
                if(firstResult > 0) {
                    ds.setFirstResult(_firstResult);
                }
                if(maxResults > 0) {
                    ds.setMaxResults(maxResults);
                }
				List list = ds.list();

				List<ResultVo> rList = new ArrayList();

                int size = list.size();
                if (list != null && size > 0) {
					rList = new ArrayList<ResultVo>();
					for (int i = 0; i < size; i++) {
						Object o[] = (Object[]) list.get(i);
						ResultVo r = new ResultVo(o);
						
						/**
						 * 插入特征物识别信息
						 */
						//获取SerialNumber
						long serialNumber = ((BigInteger) o[16]).longValue();						
						featuresQuery.setParameter(0, serialNumber);
						//查询
						List featuresList = featuresQuery.list();
						
						//向ResultVo对象中填充特征物识别信息
						fillFeatureInfo(r,featuresList);						
												
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
	public Pager queryMunityResult(final ResultQuery rq) {

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
						"v.carColor,v.resultTime,c.location,v.direction," +
						"c.longitude,c.latitude,vt.filePath as filePath," +
						"v.frame_index,t.type as taskType ,d.type ,v.serialNumber," +
						"v.vehicleKind,v.vehicleBrand,v.vehicleStyle," +
						"v.locationLeft,v.locationTop,v.locationRight,v.locationBottom," +
						"v.tag, v.paper, v.drop, v.sun , v.vehicleSeries, c.id, c.ip, v.carspeed,v.carLogo,v.ImagePath,v.vehicleLeft,v.vehicleTop,v.vehicleRight,v.vehicleBootom  " +
						"from vlpr_result v ").append(sbJoin);

				//查询特征物识别结果表
				String featuresSelect = "select featureName,position,reliability from vlpr_features where SerialNumber = ?";
				//特征物识别查询
				SQLQuery featuresQuery = session.createSQLQuery(featuresSelect);

				String cameraIds = rq.getCameraIds();

				if (!StringUtils.isEmpty(cameraIds)) {
                    cameraIds = cameraIds.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and c.id in (" + cameraIds + ")");
				}

				String characteristic = rq.getCharacteristic();
				if (!StringUtils.isEmpty(characteristic)) {
					String[] characteristics = characteristic.split(",");
					for (String character : characteristics) {
						sb.append(" and v."+character+"=1 ");
					}
				}

				List paramList = new ArrayList();
				Timestamp start = rq.getStartTime();
				if (null != start) {
					sb.append(" and v.ResultTime>?");
					paramList.add(start);
				}
				Timestamp end = rq.getEndTime();
				if (null != end) {
					sb.append(" and v.ResultTime<=?");
					paramList.add(end);
				}
				String plate = rq.getPlate();
				if (!StringUtils.isEmpty(plate)) {
					plate = plate.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.License like '%" + plate + "%'");
				}
				String location = rq.getLocation();
				if (!StringUtils.isEmpty(location)) {
					location = location.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and c.location like '%" + location + "%'");
				}
				String plateColor = rq.getPlateColor();
				if (!StringUtils.isEmpty(plateColor)) {
					plateColor = plateColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateColor like '%" + plateColor + "%'");
				}
				String plateType = rq.getPlateType();
				if (!StringUtils.isEmpty(plateType)) {
					String plateTypes = null;
					if(plateType.equals("未知车牌")){
						plateTypes = "0";
					}else if(plateType.equals("蓝牌")){
						plateTypes = "1";
					}else if(plateType.equals("黑牌")){
						plateTypes = "2";
					}else if(plateType.equals("单排黄牌")){
						plateTypes = "3";
					}else if(plateType.equals("双排黄牌(大车尾牌，农用车)")){
						plateTypes = "4";
					}else if(plateType.equals("警车车牌")){
						plateTypes = "5";
					}else if(plateType.equals("武警车牌")){
						plateTypes = "6";
					}else if(plateType.equals("个性化车牌")){
						plateTypes = "7";
					}else if(plateType.equals("单排军车")){
						plateTypes = "8";
					}else if(plateType.equals("双排军车")){
						plateTypes = "9";
					}else if(plateType.equals("使馆牌")){
						plateTypes = "10";
					}else if(plateType.equals("香港牌")){
						plateTypes = "11";
					}else if(plateType.equals("拖拉机")){
						plateTypes = "12";
					}else if(plateType.equals("澳门牌")){
						plateTypes = "13";
					}else if(plateType.equals("厂内牌")){
						plateTypes = "14";
					}
					plateTypes = plateTypes.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateType =" + plateTypes);
				}
				String carColor = rq.getCarColor();
				if (!StringUtils.isEmpty(carColor)) {
					carColor = carColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					String[] carColorNames = carColor.split(",");
					StringBuffer carColorNamePara = new StringBuffer();

					for(int i=0;i<carColorNames.length;i++){
						if(i==carColorNames.length-1){
							carColorNamePara.append("'"+carColorNames[i]+"'");
						}else{
							carColorNamePara.append("'"+carColorNames[i]+"',");
						}
					}
					sb.append(" and v.CarColor in (" + carColorNamePara.toString() + ")");
				}
				String direction = rq.getDirection();
				if (!StringUtils.isEmpty(direction)) {
					sb.append(" and v.Direction =?");
					paramList.add(direction);
				}
				String vehicleKind = rq.getVehicleKind();
				if (!StringUtils.isEmpty(vehicleKind)) {
					if(vehicleKind.equals("未识别")){
						sb.append(" and v.VehicleKind = ''");
					}else{
						vehicleKind = vehicleKind.replaceAll("\\*", "%").replaceAll("\\?", "_");
						sb.append(" and v.VehicleKind like '" + vehicleKind + "'");
					}
				}
				String vehicleBrand = rq.getVehicleBrand();
				if (!StringUtils.isEmpty(vehicleBrand)) {
					vehicleBrand = vehicleBrand.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleBrand like '%" + vehicleBrand + "%'");
				}
				String vehicleSeries = rq.getVehicleSeries();
				if (!StringUtils.isEmpty(vehicleSeries)) {
					vehicleSeries = vehicleSeries.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleSeries like '%" + vehicleSeries + "%'");
				}
				String vehicleStyle = rq.getVehicleStyle();
				if (!StringUtils.isEmpty(vehicleStyle)) {
					vehicleStyle = vehicleStyle.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleStyle like '%" + vehicleStyle + "%'");
				}
				Long serialNum = rq.getSerialNumber();
				if(serialNum != null){
					sb.append(" and v.serialNumber > " + serialNum +" ");
				}
				Short tag = rq.getTag();
				if (tag != null) {
					sb.append(" and v.tag =?");
					paramList.add(tag);
				}
				Short paper = rq.getPaper();
				if (paper != null) {
					sb.append(" and v.paper =?");
					paramList.add(paper);
				}
				Short sun = rq.getSun();
				if (sun != null) {
					sb.append(" and v.sun =?");
					paramList.add(sun);
				}
				Short drop = rq.getDrop();
				if (drop != null) {
					sb.append(" and v.drop =?");
					paramList.add(drop);
				}
				Long taskId = rq.getTaskId();
				if(taskId != null){
					sb.append(" and v.TaskID =?");
					paramList.add(taskId);
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

				SQLQuery ds = session.createSQLQuery(sb.toString() + " order by v.ResultTime DESC");

				if (paramArr.length > 0) {
					for (int i = 0; i < paramArr.length; i++) {
						ds.setParameter(i, paramArr[i]);

					}
				}

				List list = ds.list();

				List<ResultVo> rList = new ArrayList();
                int size = list.size();
                if (list != null && size > 0) {
					rList = new ArrayList<ResultVo>();
					for (int i = 0; i < size; i++) {
						Object o[] = (Object[]) list.get(i);
						ResultVo r = new ResultVo(o);

						/**
						 * 插入特征物识别信息
						 */
						//获取SerialNumber
						long serialNumber = ((BigInteger) o[16]).longValue();
						featuresQuery.setParameter(0, serialNumber);
						//查询
						List featuresList = featuresQuery.list();

						//向ResultVo对象中填充特征物识别信息
						fillFeatureInfo(r,featuresList);

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
	
	/**
	 * 查询数据条目数
	 * @param rq
	 * @return Integer
	 */
	@Override
	public Integer queryMunityResultCount(final ResultQuery rq) {

		Integer count = super.getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {

				StringBuilder sbJoin = new StringBuilder("left join vlpr_task vt on vt.TaskID=v.TaskID " +
						"left join task t on t.vlprTaskId=vt.TaskID " +
						"left join camera c on c.id=t.cameraId " +
						"left join datasource d on d.id=t.dataSourceId " +
						"where 1=1 ");

				StringBuilder sb = new StringBuilder("select v.license,v.plateType,v.plateColor," +
						"v.confidence,v.licenseAttribution,v.imageUrl," +
						"v.carColor,v.resultTime,c.location,v.direction," +
						"c.longitude,c.latitude,vt.filePath as filePath," +
						"v.frame_index,t.type as taskType ,d.type ,v.serialNumber," +
						"v.vehicleKind,v.vehicleBrand,v.vehicleStyle," +
						"v.locationLeft,v.locationTop,v.locationRight,v.locationBottom," +
						"v.tag, v.paper, v.drop, v.sun , v.vehicleSeries, c.id, c.ip, v.carspeed,v.carLogo,v.ImagePath,v.vehicleLeft,v.vehicleTop,v.vehicleRight,v.vehicleBootom  " +
						"from vlpr_result v ").append(sbJoin);

				String cameraIds = rq.getCameraIds();

                if (!StringUtils.isEmpty(cameraIds)) {
                    cameraIds = cameraIds.replaceAll("\\*", "%").replaceAll("\\?", "_");
                    sb.append(" and c.id in (" + cameraIds + ")");
                }

				String characteristic = rq.getCharacteristic();
				if (!StringUtils.isEmpty(characteristic)) {
					String[] characteristics = characteristic.split(",");
					for (String character : characteristics) {
						sb.append(" and v."+character+"=1 ");
					}
				}

				List paramList = new ArrayList();
				Timestamp start = rq.getStartTime();
				if (null != start) {
					sb.append(" and v.ResultTime>?");
					paramList.add(start);
				}
				Timestamp end = rq.getEndTime();
				if (null != end) {
					sb.append(" and v.ResultTime<=?");
					paramList.add(end);
				}
				String plate = rq.getPlate();
				if (!StringUtils.isEmpty(plate)) {
					plate = plate.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.License like '%" + plate + "%'");
				}
				String location = rq.getLocation();
				if (!StringUtils.isEmpty(location)) {
					location = location.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and c.location like '%" + location + "%'");
				}
				String plateColor = rq.getPlateColor();
				if (!StringUtils.isEmpty(plateColor)) {
					plateColor = plateColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateColor like '%" + plateColor + "%'");
				}
				String plateType = rq.getPlateType();
				if (!StringUtils.isEmpty(plateType)) {
					String plateTypes = null;
					if(plateType.equals("未知车牌")){
						plateTypes = "0";
					}else if(plateType.equals("蓝牌")){
						plateTypes = "1";
					}else if(plateType.equals("黑牌")){
						plateTypes = "2";
					}else if(plateType.equals("单排黄牌")){
						plateTypes = "3";
					}else if(plateType.equals("双排黄牌(大车尾牌，农用车)")){
						plateTypes = "4";
					}else if(plateType.equals("警车车牌")){
						plateTypes = "5";
					}else if(plateType.equals("武警车牌")){
						plateTypes = "6";
					}else if(plateType.equals("个性化车牌")){
						plateTypes = "7";
					}else if(plateType.equals("单排军车")){
						plateTypes = "8";
					}else if(plateType.equals("双排军车")){
						plateTypes = "9";
					}else if(plateType.equals("使馆牌")){
						plateTypes = "10";
					}else if(plateType.equals("香港牌")){
						plateTypes = "11";
					}else if(plateType.equals("拖拉机")){
						plateTypes = "12";
					}else if(plateType.equals("澳门牌")){
						plateTypes = "13";
					}else if(plateType.equals("厂内牌")){
						plateTypes = "14";
					}
					plateTypes = plateTypes.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateType =" + plateTypes);
				}
				String carColor = rq.getCarColor();
				if (!StringUtils.isEmpty(carColor)) {
					carColor = carColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					String[] carColorNames = carColor.split(",");
					StringBuffer carColorNamePara = new StringBuffer();

					for(int i=0;i<carColorNames.length;i++){
						if(i==carColorNames.length-1){
							carColorNamePara.append("'"+carColorNames[i]+"'");
						}else{
							carColorNamePara.append("'"+carColorNames[i]+"',");
						}
					}
					sb.append(" and v.CarColor in (" + carColorNamePara.toString() + ")");
				}
				String direction = rq.getDirection();
				if (!StringUtils.isEmpty(direction)) {
					sb.append(" and v.Direction =?");
					paramList.add(direction);
				}
				String vehicleKind = rq.getVehicleKind();
				if (!StringUtils.isEmpty(vehicleKind)) {
					if(vehicleKind.equals("未识别")){
						sb.append(" and v.VehicleKind = ''");
					}else{
						vehicleKind = vehicleKind.replaceAll("\\*", "%").replaceAll("\\?", "_");
						sb.append(" and v.VehicleKind like '" + vehicleKind + "'");
					}
				}
				String vehicleBrand = rq.getVehicleBrand();
				if (!StringUtils.isEmpty(vehicleBrand)) {
					vehicleBrand = vehicleBrand.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleBrand like '%" + vehicleBrand + "%'");
				}
				String vehicleSeries = rq.getVehicleSeries();
				if (!StringUtils.isEmpty(vehicleSeries)) {
					vehicleSeries = vehicleSeries.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleSeries like '%" + vehicleSeries + "%'");
				}
				String vehicleStyle = rq.getVehicleStyle();
				if (!StringUtils.isEmpty(vehicleStyle)) {
					vehicleStyle = vehicleStyle.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleStyle like '%" + vehicleStyle + "%'");
				}
				Long serialNum = rq.getSerialNumber();
				if(serialNum != null){
					sb.append(" and v.serialNumber > " + serialNum +" ");
				}
				Short tag = rq.getTag();
				if (tag != null) {
					sb.append(" and v.tag =?");
					paramList.add(tag);
				}
				Short paper = rq.getPaper();
				if (paper != null) {
					sb.append(" and v.paper =?");
					paramList.add(paper);
				}
				Short sun = rq.getSun();
				if (sun != null) {
					sb.append(" and v.sun =?");
					paramList.add(sun);
				}
				Short drop = rq.getDrop();
				if (drop != null) {
					sb.append(" and v.drop =?");
					paramList.add(drop);
				}
				Long taskId = rq.getTaskId();
				if(taskId != null){
					sb.append(" and v.TaskID =?");
					paramList.add(taskId);
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

				long ct = ((BigInteger) cs.uniqueResult()).longValue();
				
				return (int)ct;
				
			}
		});
		return count;
	}

    @Override
	public Pager queryMunityResult1(final ResultQuery rq) {

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
						"v.carColor,v.resultTime,c.location,v.direction," +
						"c.longitude,c.latitude,vt.filePath as filePath," +
						"v.frame_index,t.type as taskType ,d.type ,v.serialNumber," +
						"v.vehicleKind,v.vehicleBrand,v.vehicleStyle," +
						"v.locationLeft,v.locationTop,v.locationRight,v.locationBottom," +
						"v.tag, v.paper, v.drop, v.sun , v.vehicleSeries, c.id, c.ip, v.carspeed,v.carLogo,v.ImagePath,v.vehicleLeft,v.vehicleTop,v.vehicleRight,v.vehicleBootom  " +
						"from vlpr_result v ").append(sbJoin);

				//查询特征物识别结果表
				String featuresSelect = "select featureName,position,reliability from vlpr_features where SerialNumber = ?";
				//特征物识别查询
				SQLQuery featuresQuery = session.createSQLQuery(featuresSelect);

				String cameraIds = rq.getCameraIds();

				if (!StringUtils.isEmpty(cameraIds)) {
                    cameraIds = cameraIds.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and c.id in (" + cameraIds + ")");
				}

				String characteristic = rq.getCharacteristic();
				if (!StringUtils.isEmpty(characteristic)) {
					String[] characteristics = characteristic.split(",");
					for (String character : characteristics) {
						sb.append(" and v."+character+"=1 ");
					}
				}

				List paramList = new ArrayList();
				Timestamp start = rq.getStartTime();
				if (null != start) {
					sb.append(" and v.ResultTime>?");
					paramList.add(start);
				}
				Timestamp end = rq.getEndTime();
				if (null != end) {
					sb.append(" and v.ResultTime<=?");
					paramList.add(end);
				}
				String plate = rq.getPlate();
				if (!StringUtils.isEmpty(plate)) {
					plate = plate.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.License like '%" + plate + "%'");
				}
				String location = rq.getLocation();
				if (!StringUtils.isEmpty(location)) {
					location = location.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and c.location='" + location + "'");
				}
				String plateColor = rq.getPlateColor();
				if (!StringUtils.isEmpty(plateColor)) {
					plateColor = plateColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateColor like '%" + plateColor + "%'");
				}
				String plateType = rq.getPlateType();
				if (!StringUtils.isEmpty(plateType)) {
					plateType = plateType.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateType like '%" + plateType + "%'");
				}
				String carColor = rq.getCarColor();
				if (!StringUtils.isEmpty(carColor)) {
					carColor = carColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					String[] carColorNames = carColor.split(",");
					StringBuffer carColorNamePara = new StringBuffer();

					for(int i=0;i<carColorNames.length;i++){
						if(i==carColorNames.length-1){
							carColorNamePara.append("'"+carColorNames[i]+"'");
						}else{
							carColorNamePara.append("'"+carColorNames[i]+"',");
						}
					}
					sb.append(" and v.CarColor in (" + carColorNamePara.toString() + ")");
				}
				String direction = rq.getDirection();
				if (!StringUtils.isEmpty(direction)) {
					sb.append(" and v.Direction =?");
					paramList.add(direction);
				}
				String vehicleKind = rq.getVehicleKind();
				if (!StringUtils.isEmpty(vehicleKind)) {
					if(vehicleKind.equals("未识别")){
						sb.append(" and v.VehicleKind = ''");
					}else{
						vehicleKind = vehicleKind.replaceAll("\\*", "%").replaceAll("\\?", "_");
						sb.append(" and v.VehicleKind like '" + vehicleKind + "'");
					}
				}
				String vehicleBrand = rq.getVehicleBrand();
				if (!StringUtils.isEmpty(vehicleBrand)) {
					vehicleBrand = vehicleBrand.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleBrand like '%" + vehicleBrand + "%'");
				}
				String vehicleSeries = rq.getVehicleSeries();
				if (!StringUtils.isEmpty(vehicleSeries)) {
					vehicleSeries = vehicleSeries.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleSeries like '%" + vehicleSeries + "%'");
				}
				String vehicleStyle = rq.getVehicleStyle();
				if (!StringUtils.isEmpty(vehicleStyle)) {
					vehicleStyle = vehicleStyle.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleStyle like '%" + vehicleStyle + "%'");
				}
				Long serialNum = rq.getSerialNumber();
				if(serialNum != null){
					sb.append(" and v.serialNumber > " + serialNum +" ");
				}
				Short tag = rq.getTag();
				if (tag != null) {
					sb.append(" and v.tag =?");
					paramList.add(tag);
				}
				Short paper = rq.getPaper();
				if (paper != null) {
					sb.append(" and v.paper =?");
					paramList.add(paper);
				}
				Short sun = rq.getSun();
				if (sun != null) {
					sb.append(" and v.sun =?");
					paramList.add(sun);
				}
				Short drop = rq.getDrop();
				if (drop != null) {
					sb.append(" and v.drop =?");
					paramList.add(drop);
				}
				Long taskId = rq.getTaskId();
				if(taskId != null){
					sb.append(" and v.TaskID =?");
					paramList.add(taskId);
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

				SQLQuery ds = session.createSQLQuery(sb.toString() + " ORDER BY v.ResultTime ASC");

				if (paramArr.length > 0) {
					for (int i = 0; i < paramArr.length; i++) {
						ds.setParameter(i, paramArr[i]);

					}
				}

				List list = ds.list();

				List<ResultVo> rList = new ArrayList();
                int size = list.size();
                if (list != null && size > 0) {
					rList = new ArrayList<ResultVo>();
					for (int i = 0; i < size; i++) {
						Object o[] = (Object[]) list.get(i);
						ResultVo r = new ResultVo(o);

						/**
						 * 插入特征物识别信息
						 */
						//获取SerialNumber
						long serialNumber = ((BigInteger) o[16]).longValue();
						featuresQuery.setParameter(0, serialNumber);
						//查询
						List featuresList = featuresQuery.list();

						//向ResultVo对象中填充特征物识别信息
						fillFeatureInfo(r,featuresList);

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
	public Pager queryMunityResultByFrequentlyPass(final ResultQuery rq) {

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
						"v.carColor,v.resultTime,c.location,v.direction," +
						"c.longitude,c.latitude,vt.filePath as filePath," +
						"v.frame_index,t.type as taskType ,d.type ,v.serialNumber," +
						"v.vehicleKind,v.vehicleBrand,v.vehicleStyle," +
						"v.locationLeft,v.locationTop,v.locationRight,v.locationBottom," +
						"v.tag, v.paper, v.drop, v.sun , v.vehicleSeries, c.id, c.ip, v.carspeed,v.carLogo,v.ImagePath,v.vehicleLeft,v.vehicleTop,v.vehicleRight,v.vehicleBootom  " +
						"from vlpr_result v ").append(sbJoin);

				//查询特征物识别结果表
				String featuresSelect = "select featureName,position,reliability from vlpr_features where SerialNumber = ?";
				//特征物识别查询
				SQLQuery featuresQuery = session.createSQLQuery(featuresSelect);

				String cameraIds = rq.getCameraIds();

				if (!StringUtils.isEmpty(cameraIds)) {
                    cameraIds = cameraIds.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and c.id in (" + cameraIds+ ")");
				}

				String characteristic = rq.getCharacteristic();
				if (!StringUtils.isEmpty(characteristic)) {
					String[] characteristics = characteristic.split(",");
					for (String character : characteristics) {
						sb.append(" and v."+character+"=1 ");
					}
				}

				List paramList = new ArrayList();
				Timestamp start = rq.getStartTime();
				if (null != start) {
					sb.append(" and v.ResultTime>?");
					paramList.add(start);
				}
				Timestamp end = rq.getEndTime();
				if (null != end) {
					sb.append(" and v.ResultTime<=?");
					paramList.add(end);
				}
				String plate = rq.getPlate();
				if (!StringUtils.isEmpty(plate)) {
					plate = plate.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.License like '%" + plate + "%'");
				}
				String location = rq.getLocation();
				if (!StringUtils.isEmpty(location)) {
					location = location.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and c.location like '%" + location + "%'");
				}
				String plateColor = rq.getPlateColor();
				if (!StringUtils.isEmpty(plateColor)) {
					plateColor = plateColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateColor like '%" + plateColor + "%'");
				}
				String plateType = rq.getPlateType();
				if (!StringUtils.isEmpty(plateType)) {
					String plateTypes = null;
					if(plateType.equals("未知车牌")){
						plateTypes = "0";
					}else if(plateType.equals("蓝牌")){
						plateTypes = "1";
					}else if(plateType.equals("黑牌")){
						plateTypes = "2";
					}else if(plateType.equals("单排黄牌")){
						plateTypes = "3";
					}else if(plateType.equals("双排黄牌(大车尾牌，农用车)")){
						plateTypes = "4";
					}else if(plateType.equals("警车车牌")){
						plateTypes = "5";
					}else if(plateType.equals("武警车牌")){
						plateTypes = "6";
					}else if(plateType.equals("个性化车牌")){
						plateTypes = "7";
					}else if(plateType.equals("单排军车")){
						plateTypes = "8";
					}else if(plateType.equals("双排军车")){
						plateTypes = "9";
					}else if(plateType.equals("使馆牌")){
						plateTypes = "10";
					}else if(plateType.equals("香港牌")){
						plateTypes = "11";
					}else if(plateType.equals("拖拉机")){
						plateTypes = "12";
					}else if(plateType.equals("澳门牌")){
						plateTypes = "13";
					}else if(plateType.equals("厂内牌")){
						plateTypes = "14";
					}
					plateTypes = plateTypes.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateType =" + plateTypes);
				}
				String carColor = rq.getCarColor();
				if (!StringUtils.isEmpty(carColor)) {
					carColor = carColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					String[] carColorNames = carColor.split(",");
					StringBuffer carColorNamePara = new StringBuffer();

					for(int i=0;i<carColorNames.length;i++){
						if(i==carColorNames.length-1){
							carColorNamePara.append("'"+carColorNames[i]+"'");
						}else{
							carColorNamePara.append("'"+carColorNames[i]+"',");
						}
					}
					sb.append(" and v.CarColor in (" + carColorNamePara.toString() + ")");
				}
				String direction = rq.getDirection();
				if (!StringUtils.isEmpty(direction)) {
					sb.append(" and v.Direction =?");
					paramList.add(direction);
				}
				String vehicleKind = rq.getVehicleKind();
				if (!StringUtils.isEmpty(vehicleKind)) {
					if(vehicleKind.equals("未识别")){
						sb.append(" and v.VehicleKind = ''");
					}else{
						vehicleKind = vehicleKind.replaceAll("\\*", "%").replaceAll("\\?", "_");
						sb.append(" and v.VehicleKind like '" + vehicleKind + "'");
					}
				}
				String vehicleBrand = rq.getVehicleBrand();
				if (!StringUtils.isEmpty(vehicleBrand)) {
					vehicleBrand = vehicleBrand.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleBrand like '%" + vehicleBrand + "%'");
				}
				String vehicleSeries = rq.getVehicleSeries();
				if (!StringUtils.isEmpty(vehicleSeries)) {
					vehicleSeries = vehicleSeries.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleSeries like '%" + vehicleSeries + "%'");
				}
				String vehicleStyle = rq.getVehicleStyle();
				if (!StringUtils.isEmpty(vehicleStyle)) {
					vehicleStyle = vehicleStyle.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleStyle like '%" + vehicleStyle + "%'");
				}
				Long serialNum = rq.getSerialNumber();
				if(serialNum != null){
					sb.append(" and v.serialNumber > " + serialNum +" ");
				}
				Short tag = rq.getTag();
				if (tag != null) {
					sb.append(" and v.tag =?");
					paramList.add(tag);
				}
				Short paper = rq.getPaper();
				if (paper != null) {
					sb.append(" and v.paper =?");
					paramList.add(paper);
				}
				Short sun = rq.getSun();
				if (sun != null) {
					sb.append(" and v.sun =?");
					paramList.add(sun);
				}
				Short drop = rq.getDrop();
				if (drop != null) {
					sb.append(" and v.drop =?");
					paramList.add(drop);
				}
				Long taskId = rq.getTaskId();
				if(taskId != null){
					sb.append(" and v.TaskID =?");
					paramList.add(taskId);
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

				SQLQuery ds = session.createSQLQuery(sb.toString());

				if (paramArr.length > 0) {
					for (int i = 0; i < paramArr.length; i++) {
						ds.setParameter(i, paramArr[i]);

					}
				}

				List list = ds.list();

				List<ResultVo> rList = new ArrayList();
                int size = list.size();
                if (list != null && size > 0) {
					rList = new ArrayList<ResultVo>();
					for (int i = 0; i < size; i++) {
						Object o[] = (Object[]) list.get(i);
						ResultVo r = new ResultVo(o);

						/**
						 * 插入特征物识别信息
						 */
						//获取SerialNumber
						long serialNumber = ((BigInteger) o[16]).longValue();
						featuresQuery.setParameter(0, serialNumber);
						//查询
						List featuresList = featuresQuery.list();

						//向ResultVo对象中填充特征物识别信息
						fillFeatureInfo(r,featuresList);

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
                        + " v.vehicleKind,v.vehicleBrand,v.vehicleStyle,"
                        + " v.locationLeft,v.locationTop,v.locationRight,v.locationBottom, "
                        + " v.tag, v.paper, v.drop, v.sun, v.vehicleSeries, c.id, c.ip, v.carspeed,v.carLogo,v.ImagePath,v.vehicleLeft,v.vehicleTop,v.vehicleRight,v.vehicleBootom   "
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

        int size = list.size();
        if (size > 0) {
			Object o[] = (Object[]) list.get(0);
			ResultVo r = new ResultVo(o);
			return r;
		}
		return null;
	}
    
    @Override
   	public Pager queryResults(final String ids) {
    	Pager pager = super.getHibernateTemplate().execute(new HibernateCallback<Pager>() {
            @Override
            public Pager doInHibernate(Session session) throws HibernateException, SQLException {
                String sql = "select v.license,v.plateType,v.plateColor,v.confidence,v.licenseAttribution,v.imageUrl,"
                        + " v.carColor,v.resultTime,c.location,"
                        + " v.direction,c.longitude,c.latitude,vt.filePath as filePath,v.frame_index,t.type as taskType ,d.type ,v.serialNumber,"
                        + " v.vehicleKind,v.vehicleBrand,v.vehicleStyle,"
                        + " v.locationLeft,v.locationTop,v.locationRight,v.locationBottom, "
                        + " v.tag, v.paper, v.drop, v.sun, v.vehicleSeries, c.id, c.ip, v.carspeed,v.carLogo,v.ImagePath,v.vehicleLeft,v.vehicleTop,v.vehicleRight,v.vehicleBootom   "
                        + " from vlpr_result v,vlpr_task vt,camera c,task t"
                        + " left join datasource d"
                        + " on d.id=t.dataSourceId"
                        + " where v.serialNumber in (" + ids + ") and v.TaskID=vt.TaskID and vt.TaskID=t.vlprTaskId and t.cameraId =c.id";

                SQLQuery ds = session.createSQLQuery(sql);
                List list = ds.list();

                List<ResultVo> rList = new ArrayList();
                int size = list.size();
                if (list != null && size > 0) {
                    rList = new ArrayList<ResultVo>();
                    for (int i = 0; i < size; i++) {
                        Object o[] = (Object[]) list.get(i);
                        ResultVo r = new ResultVo(o);
                        rList.add(r);
                    }
                }

                Pager p = new Pager();
                p.setRows(rList);
                p.setTotal((long) rList.size());
                return p;

            }
        });

    	return pager;
   		
   	}
	
	/**
	 * 向ResultVo对象中填充特征物识别信息
	 * 
	 * @param vo vo类
	 * @param featureList 特征物识别结果
	 */
	private void fillFeatureInfo(ResultVo vo,List featureList){
		for(int i=0;i<featureList.size();i++){
			Object o[] = (Object[])featureList.get(i);
			
			//特征物名
			String featureName = (String) o[0];
			//特征物位置信息
			String position = (String) o[1];
			//可信度
			String reliability = (String) o[2];
			
			if(StringUtils.isEmpty(position)){
				position = "[]";
			}
			if(StringUtils.isEmpty(reliability)){
				reliability = "[]";
			}
			
			//年检标签
			if("tag".equals(featureName)){
				vo.setTagRectAndScore("{\"rect\":"+position+",\"score\":"+reliability+"}");				
			}
			//纸巾盒
			if("paper".equals(featureName)){
				vo.setPaperRectAndScore("{\"rect\":"+position+",\"score\":"+reliability+"}");				
			}
			//遮阳板
			if("sun".equals(featureName)){
				vo.setSunRectAndScore("{\"rect\":"+position+",\"score\":"+reliability+"}");				
			}
			//坠饰
			if("drop".equals(featureName)){
				vo.setDropRectAndScore("{\"rect\":"+position+",\"score\":"+reliability+"}");				
			}
			//车前挡风玻璃窗口
			if("window".equals(featureName)){
				vo.setWindowRectAndScore("{\"rect\":"+position+",\"score\":"+reliability+"}");				
			}
		}
	}
	
	/**
	 * 查询最大serialNumber
	 */
	@Override
	public Long queryMaxSeriNumber(){
		
		String querString = "select MAX(serialNumber) from VlprResult";
		Long maxSerialNumber = (Long)super.getHibernateTemplate().find(querString).get(0);
		
		return maxSerialNumber;
	}

    @Override
    public Long queryMaxSeriNumber(final Long serialNumber,final Integer rows) {
        Long maxSerialNumber = super.getHibernateTemplate().execute(new HibernateCallback<Long>() {
            @Override
            public Long doInHibernate(Session session) throws HibernateException, SQLException {
                String queryString = "select max(a.serialNumber) from (select v.serialNumber from vlpr_result v where v.serialNumber>? limit ?) a";

                SQLQuery cs = session.createSQLQuery(queryString);
                cs.setParameter(0, serialNumber);
                cs.setParameter(1, rows);
                List list = cs.list();
                int size = list.size();
                BigInteger bigInteger = (BigInteger) list.get(size - 1);
                Long maxSerialNumber = bigInteger==null ? null : bigInteger.longValue();

                return maxSerialNumber;
            }
        });
        return maxSerialNumber;
    }

    /**
	 * 获取seriNumber之间的数据
	 */
	@Override
	public List queryResultBetweenBySeriNumber(Long maxSeriNumber,Long minSeriNumber){
		String querString = " from VlprResult where serialNumber > ? and serialNumber <= ?";
		List list = super.getHibernateTemplate().find(querString, minSeriNumber,maxSeriNumber);
		
		return list;
	}
	
	/**
	 * 根据seriNumber查询监控点名称
	 */
	public String queryCameraNameBySeriNumber(final Long seriNumber){
		String result = super.getHibernateTemplate().execute(new HibernateCallback<String>() {

            @Override
            public String doInHibernate(Session session) throws HibernateException, SQLException {
                String cameraName = "";
                String queryString = "select c.name from vlpr_result vr left join camera c on c.id=vr.cameraId where vr.SerialNumber = ?";

                SQLQuery cs = session.createSQLQuery(queryString);
                cs.setParameter(0, seriNumber);
                cameraName = (String) cs.uniqueResult();

                return cameraName;
            }
        });
		return result;
	}

    @Override
    public List queryByTravelTogetherVehicle(final ResultQuery rq) {
        List result = super.getHibernateTemplate().execute(new HibernateCallback<List>() {
            @Override
            public List doInHibernate(Session session) throws HibernateException, SQLException {
                Timestamp startTime = rq.getStartTime();
                Timestamp endTime = rq.getEndTime();
                String cameraIds = rq.getCameraIds();
                String plate = rq.getPlate();

                StringBuffer queryString = new StringBuffer();

                queryString.append(" select v.License from vlpr_result v ");
                queryString.append(" where 1=1 and v.resultTime>='" + startTime + "' and v.resultTime<='" + endTime + "' and  ");
                queryString.append(" v.cameraId in (" + cameraIds + ") and ");
                queryString.append(" v.License <> '" + plate + "' ");

                SQLQuery cs = session.createSQLQuery(queryString.toString());

                return cs.list();
            }
        });
        return result;
    }

    public static Long serialNumber = 0l;
    public static Date now = new Date(new Date().getTime() - 1000);
    @Override
    public List queryByNow() {
        List list = super.getHibernateTemplate().execute(new HibernateCallback<List>() {
            @Override
            public List doInHibernate(Session session) throws HibernateException, SQLException {
                String sql = "select v.license,v.plateType,v.plateColor,v.confidence,v.licenseAttribution,v.imageUrl,"
                        + " v.carColor,v.resultTime,c.location,"
                        + " v.direction,c.longitude,c.latitude,vt.filePath as filePath,"
                        + " v.frame_index,t.type as taskType ,d.type ,v.serialNumber,"
                        + " v.vehicleKind,v.vehicleBrand,v.vehicleStyle,"
                        + "v.locationLeft,v.locationTop,v.locationRight,v.locationBottom, "
                        + "v.tag, v.paper, v.drop, v.sun, v.vehicleSeries, c.id, c.ip, v.carspeed,v.carLogo,v.ImagePath,v.vehicleLeft,v.vehicleTop,v.vehicleRight,v.vehicleBootom  "
                        + " from vlpr_result v"
                        + " left join vlpr_task vt on vt.TaskID=v.TaskID"
                        + " left join task t on t.vlprTaskId=vt.TaskID"
                        + " left join camera c on c.id=t.cameraId"
                        + " left join datasource d on d.id=t.dataSourceId"
                        + " where 1=1 and v.serialNumber > " + serialNumber + " and v.resultTime > '" + DateUtils.formateTime(now) +"' ORDER by v.serialNumber desc";

                SQLQuery ds = session.createSQLQuery(sql);
                List listVO = ds.list();
                List<ResultVo> rList = new ArrayList();

                if (listVO != null && listVO.size() > 0) {
                    Object o1[] = (Object[]) listVO.get(0);
                    ResultVo rv = new ResultVo(o1);
                    serialNumber = rv.getSerialNumber();
                    now = new Timestamp( rv.getResultTime().getTime()-1000);
                    rList = new ArrayList<ResultVo>();
                    for (int i = 0; i < listVO.size(); i++) {
                        Object o[] = (Object[]) listVO.get(i);
                        ResultVo r = new ResultVo(o);

                        rList.add(r);
                    }
                }
                return rList;
            }
        });
        return list;
    }

    @Override
    public void addToChangHai(final ResultVo r) {
        super.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                StringBuffer sb = new StringBuffer("insert into HRD_VIC.T_DATA_PASS (DATA_ID, DIVISION_PARENT, DIVISION_CODE, BAYONET_CODE, " +
                        "BAYONET_TYPE, BAYONET_STYPE, DEVICE_CODE, VEHICLE_OLD, VEHICLE_NO, LICENSE_CODE, COLOR_CODE, PASS_TIME, FIRSTINSERT_TIME, " +
                        "CHANNEL, SPEED_ACTUAL, VEHICLEMAKER_CODE, VEHICLECOLOR_CODE, VEHICLE_LENGTH, IMG1, IMG2, IMG3, IMG4, LASTEDIT_TIME, " +
                        "WRITE_TIME, PASS_ID, DATA_SOURCES)" +
                        " values (84, '450100', '450100', '" + r.getCameraID() + "', " +
                        "'2', null, '450100430000504923000000', '" + r.getLicense() + "', '" + r.getLicense() + "', '02', '2', '" + DateUtils.formateTime3(r.getResultTime()) + "','" + DateUtils.formateTime3(r.getResultTime()) + "'," +
                        " null,null, null,null, null, '" + r.getImageUrl() + "', null, null, null, " + DateUtils.formateTime3(r.getResultTime()) + "," +
                        "to_date('" + DateUtils.formateTime2(r.getResultTime()) + "', 'dd-mm-yyyy hh24:mi:ss'), null, '9')");
                SQLQuery ds = session.createSQLQuery(sb.toString());
                ds.executeUpdate();
                return null;
            }
        });
    }
    
    /**
	 * 查询过车列表信息
	 * @param startTime
	 * @param endTime
	 * @param cameraIds
	 * @param frequentlyRate
	 * @return List
	 */
    @Override
    public List queryPassVehicleList(final Timestamp startTime,final Timestamp endTime,final String cameraIds,final Integer frequentlyRate){
    	
		List result = super.getHibernateTemplate().execute(new HibernateCallback<List>() {
            @Override
            public List doInHibernate(Session session) throws HibernateException, SQLException {
            	StringBuffer queryString = new StringBuffer();
        		queryString.append(" select v.License,count(0) as totalNumber  from vlpr_result v ");
        		queryString.append(" where v.ResultTime>=? and v.ResultTime<=? and  ");
        		queryString.append(" v.cameraId in (" + cameraIds +") and ");
        		queryString.append(" v.License not like '%无车牌%' ");
        		queryString.append(" group by v.License having totalNumber >= ? ");

                SQLQuery cs = session.createSQLQuery(queryString.toString());
                cs.setParameter(0, startTime);
                cs.setParameter(1, endTime);
                cs.setParameter(2, frequentlyRate);
                
                List list = (List) cs.list();
                List resultList = new ArrayList();
                for(Object item:list){
                	Object[] it = (Object[]) item;
                	Map<Object,Object> map = new HashMap<Object,Object>();
                	map.put("license", it[0]);
                	map.put("totalNumber", it[1]);
                	resultList.add(map);               	
                }

                return resultList;
            }
        });
		return result;
	}
    
    /**
   	 * 查询监控点过车列表信息
   	 * @param startTime
   	 * @param endTime
   	 * @param cameraIds
   	 * @param license
   	 * @return List
   	 */
     @Override
     public Pager queryPassVehicleByCameraList(final Timestamp startTime,final Timestamp endTime,final String cameraIds,final String license,Integer page, Integer rows){   	 
    	final int firstResult = (page - 1) * rows;
 		final int maxResults = rows;
       	
 		Pager result = super.getHibernateTemplate().execute(new HibernateCallback<Pager>() {
               @Override
               public Pager doInHibernate(Session session) throws HibernateException, SQLException {
               	StringBuffer queryString = new StringBuffer();
           		queryString.append(" select v.License,c.name,count(0) as totalNumber,c.id  from vlpr_result v ");         
           		queryString.append(" left join camera c on c.id = v.cameraId ");
           		queryString.append(" where v.ResultTime>=? and v.ResultTime<=? and  ");
           		queryString.append(" v.cameraId in (" + cameraIds +") and ");
           		queryString.append(" v.License = ? ");
           		queryString.append(" group by v.cameraId ");
           		
           		/*
				   统计总数量
				*/
				String countSql = "select count(*) from (select count(*) from " + queryString.toString().split("from")[1]+") as temp";
				SQLQuery queryCount = session.createSQLQuery(countSql);
				queryCount.setParameter(0, startTime);
				queryCount.setParameter(1, endTime);
				queryCount.setParameter(2, license);
				long count = ((BigInteger) queryCount.uniqueResult()).longValue();
           		          		
                   SQLQuery cs = session.createSQLQuery(queryString.toString());
                   cs.setParameter(0, startTime);
                   cs.setParameter(1, endTime);
                   cs.setParameter(2, license);
                   
                   cs.setFirstResult(firstResult);
                   cs.setMaxResults(maxResults);
                   
                   List list = (List) cs.list();
                   List resultList = new ArrayList();
                   for(Object item:list){
                   	Object[] it = (Object[]) item;
                   	Map<Object,Object> map = new HashMap<Object,Object>();
                   	map.put("license", it[0]);
                   	map.put("cameraName",it[1]);
                   	map.put("totalNumber", it[2]);
                   	map.put("cameraId", it[3]);
                   	resultList.add(map);
                   }
                   
                   Pager pager=new Pager();
                   pager.setRows(resultList);
                   pager.setTotal(count);

                   return pager;
               }
           });
   		return result;
   	}

    @Override
    public List queryAerialMammalVehicle(final ResultQuery rq,final Integer nightAppearNum,final String nightStartTime,final String nightEndTime) {

        List result = super.getHibernateTemplate().execute(new HibernateCallback<List>() {
            @Override
            public List doInHibernate(Session session) throws HibernateException, SQLException {
				StringBuilder queryStr = new StringBuilder("select t.License,t.count1 count1,t2.count2 from ");
				queryStr.append(" (select v.License,count(v.License) count1 from vlpr_result v ");
				queryStr.append(" where v.ResultTime>? and v.ResultTime<? and DATE_FORMAT(v.ResultTime,'%H')>=? ");
				queryStr.append(" and DATE_FORMAT(v.ResultTime,'%H')<? GROUP BY v.License) t ");
				queryStr.append(" LEFT JOIN ");
				queryStr.append(" (select v.License,count(v.License) count2 from vlpr_result v ");
				queryStr.append(" where v.ResultTime>? and v.ResultTime<? ");
				queryStr.append(" and (DATE_FORMAT(v.ResultTime,'%H')<? or DATE_FORMAT(v.ResultTime,'%H')>=?) ");
				queryStr.append(" GROUP BY v.License) t2 ");
				queryStr.append(" on t.License=t2.License ");
				queryStr.append(" where t.count1>=?  order by t.count1 desc");
				SQLQuery cs = session.createSQLQuery(queryStr.toString());
				cs.setParameter(0, rq.getStartTime());
				cs.setParameter(1, rq.getEndTime());
				cs.setParameter(2, nightStartTime);
				cs.setParameter(3, nightEndTime);
				cs.setParameter(4, rq.getStartTime());
				cs.setParameter(5, rq.getEndTime());
				cs.setParameter(6, nightStartTime);
				cs.setParameter(7, nightEndTime);
				cs.setParameter(8, nightAppearNum);
                return cs.list();
            }
        });
        return result;
    }

	@Override
	public List queryFrequentNocturnalVehicle(final ResultQuery rq,final Integer nightAppearNum,final String nightStartTime,final String nightEndTime) {
		List result = super.getHibernateTemplate().execute(new HibernateCallback<List>() {
			@Override
			public List doInHibernate(Session session) throws HibernateException, SQLException {
				String sql="select t.License,t.lCount from (select v.License,count(v.License) lCount from vlpr_result v where v.ResultTime>? and v.ResultTime<? and DATE_FORMAT(v.ResultTime,'%H')>=? and DATE_FORMAT(v.ResultTime,'%H')<? GROUP BY v.License) t where t.lCount>=?  order by t.lCount desc";
				SQLQuery cs = session.createSQLQuery(sql);
				cs.setParameter(0, rq.getStartTime());
				cs.setParameter(1, rq.getEndTime());
				cs.setParameter(2, nightStartTime);
				cs.setParameter(3, nightEndTime);
				cs.setParameter(4, nightAppearNum);
				return cs.list();
			}
		});
		return result;
	}

    /**
     * 实时布控查询专用（其他人勿用，用了后果概不负责！！！）
     * @param rq
     * @return
     */
    @Override
	public Pager querySurveillanceResult(final ResultQuery rq) {

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
						"v.carColor,v.resultTime,c.location,v.direction," +
						"c.longitude,c.latitude,vt.filePath as filePath," +
						"v.frame_index,t.type as taskType ,d.type ,v.serialNumber," +
						"v.vehicleKind,v.vehicleBrand,v.vehicleStyle," +
						"v.locationLeft,v.locationTop,v.locationRight,v.locationBottom," +
						"v.tag, v.paper, v.drop, v.sun , v.vehicleSeries, c.id, c.ip, v.carspeed,v.carLogo,v.ImagePath,v.vehicleLeft,v.vehicleTop,v.vehicleRight,v.vehicleBootom  " +
						"from vlpr_result v ").append(sbJoin);

				//查询特征物识别结果表
				String featuresSelect = "select featureName,position,reliability from vlpr_features where SerialNumber = ?";
				//特征物识别查询
				SQLQuery featuresQuery = session.createSQLQuery(featuresSelect);

				String cameraIds = rq.getCameraIds();

				if (!StringUtils.isEmpty(cameraIds)) {
					String[] cameraNames = cameraIds.split(",");
					StringBuffer cameraNamePara = new StringBuffer();

					for(int i=0;i<cameraNames.length;i++){
						if(i==cameraNames.length-1){
							cameraNamePara.append("'"+cameraNames[i]+"'");
						}else{
							cameraNamePara.append("'"+cameraNames[i]+"',");
						}
					}
					sb.append(" and c.name in (" + cameraNamePara.toString() + ")");
				}

				String characteristic = rq.getCharacteristic();
				if (!StringUtils.isEmpty(characteristic)) {
					String[] characteristics = characteristic.split(",");
					for (String character : characteristics) {
						sb.append(" and v."+character+"=1 ");
					}
				}

				List paramList = new ArrayList();
				Timestamp start = rq.getStartTime();
				if (null != start) {
					sb.append(" and v.ResultTime>?");
					paramList.add(start);
				}
				Timestamp end = rq.getEndTime();
				if (null != end) {
					sb.append(" and v.ResultTime<=?");
					paramList.add(end);
				}
				String plate = rq.getPlate();
				if (!StringUtils.isEmpty(plate)) {
					plate = plate.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.License like '%" + plate + "%'");
				}
				String location = rq.getLocation();
				if (!StringUtils.isEmpty(location)) {
					location = location.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and c.location like '%" + location + "%'");
				}
				String plateColor = rq.getPlateColor();
				if (!StringUtils.isEmpty(plateColor)) {
					plateColor = plateColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateColor like '%" + plateColor + "%'");
				}
				String plateType = rq.getPlateType();
				if (!StringUtils.isEmpty(plateType)) {
					String plateTypes = null;
					if(plateType.equals("未知车牌")){
						plateTypes = "0";
					}else if(plateType.equals("蓝牌")){
						plateTypes = "1";
					}else if(plateType.equals("黑牌")){
						plateTypes = "2";
					}else if(plateType.equals("单排黄牌")){
						plateTypes = "3";
					}else if(plateType.equals("双排黄牌(大车尾牌，农用车)")){
						plateTypes = "4";
					}else if(plateType.equals("警车车牌")){
						plateTypes = "5";
					}else if(plateType.equals("武警车牌")){
						plateTypes = "6";
					}else if(plateType.equals("个性化车牌")){
						plateTypes = "7";
					}else if(plateType.equals("单排军车")){
						plateTypes = "8";
					}else if(plateType.equals("双排军车")){
						plateTypes = "9";
					}else if(plateType.equals("使馆牌")){
						plateTypes = "10";
					}else if(plateType.equals("香港牌")){
						plateTypes = "11";
					}else if(plateType.equals("拖拉机")){
						plateTypes = "12";
					}else if(plateType.equals("澳门牌")){
						plateTypes = "13";
					}else if(plateType.equals("厂内牌")){
						plateTypes = "14";
					}
					plateTypes = plateTypes.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.PlateType =" + plateTypes);
				}
				String carColor = rq.getCarColor();
				if (!StringUtils.isEmpty(carColor)) {
					carColor = carColor.replaceAll("\\*", "%").replaceAll("\\?", "_");
					String[] carColorNames = carColor.split(",");
					StringBuffer carColorNamePara = new StringBuffer();

					for(int i=0;i<carColorNames.length;i++){
						if(i==carColorNames.length-1){
							carColorNamePara.append("'"+carColorNames[i]+"'");
						}else{
							carColorNamePara.append("'"+carColorNames[i]+"',");
						}
					}
					sb.append(" and v.CarColor in (" + carColorNamePara.toString() + ")");
				}
				String direction = rq.getDirection();
				if (!StringUtils.isEmpty(direction)) {
					sb.append(" and v.Direction =?");
					paramList.add(direction);
				}
				String vehicleKind = rq.getVehicleKind();
				if (!StringUtils.isEmpty(vehicleKind)) {
					if(vehicleKind.equals("未识别")){
						sb.append(" and v.VehicleKind = ''");
					}else{
						vehicleKind = vehicleKind.replaceAll("\\*", "%").replaceAll("\\?", "_");
						sb.append(" and v.VehicleKind like '" + vehicleKind + "'");
					}
				}
				String vehicleBrand = rq.getVehicleBrand();
				if (!StringUtils.isEmpty(vehicleBrand)) {
					vehicleBrand = vehicleBrand.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleBrand like '%" + vehicleBrand + "%'");
				}
				String vehicleSeries = rq.getVehicleSeries();
				if (!StringUtils.isEmpty(vehicleSeries)) {
					vehicleSeries = vehicleSeries.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleSeries like '%" + vehicleSeries + "%'");
				}
				String vehicleStyle = rq.getVehicleStyle();
				if (!StringUtils.isEmpty(vehicleStyle)) {
					vehicleStyle = vehicleStyle.replaceAll("\\*", "%").replaceAll("\\?", "_");
					sb.append(" and v.VehicleStyle like '%" + vehicleStyle + "%'");
				}
				Long serialNum = rq.getSerialNumber();
				if(serialNum != null){
					sb.append(" and v.serialNumber > " + serialNum +" ");
				}
				Short tag = rq.getTag();
				if (tag != null) {
					sb.append(" and v.tag =?");
					paramList.add(tag);
				}
				Short paper = rq.getPaper();
				if (paper != null) {
					sb.append(" and v.paper =?");
					paramList.add(paper);
				}
				Short sun = rq.getSun();
				if (sun != null) {
					sb.append(" and v.sun =?");
					paramList.add(sun);
				}
				Short drop = rq.getDrop();
				if (drop != null) {
					sb.append(" and v.drop =?");
					paramList.add(drop);
				}
				Long taskId = rq.getTaskId();
				if(taskId != null){
					sb.append(" and v.TaskID =?");
					paramList.add(taskId);
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

				SQLQuery ds = session.createSQLQuery(sb.toString() + " order by v.ResultTime DESC");

				if (paramArr.length > 0) {
					for (int i = 0; i < paramArr.length; i++) {
						ds.setParameter(i, paramArr[i]);

					}
				}

				List list = ds.list();

				List<ResultVo> rList = new ArrayList();
                int size = list.size();
                if (list != null && size > 0) {
					rList = new ArrayList<ResultVo>();
					for (int i = 0; i < size; i++) {
						Object o[] = (Object[]) list.get(i);
						ResultVo r = new ResultVo(o);

						/**
						 * 插入特征物识别信息
						 */
						//获取SerialNumber
						long serialNumber = ((BigInteger) o[16]).longValue();
						featuresQuery.setParameter(0, serialNumber);
						//查询
						List featuresList = featuresQuery.list();

						//向ResultVo对象中填充特征物识别信息
						fillFeatureInfo(r,featuresList);

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
}
