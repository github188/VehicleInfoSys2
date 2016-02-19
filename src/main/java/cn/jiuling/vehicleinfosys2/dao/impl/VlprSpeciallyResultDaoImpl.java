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

import cn.jiuling.vehicleinfosys2.dao.VlprSpeciallyResultDao;
import cn.jiuling.vehicleinfosys2.model.VlprSpeciallyResult;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;

@Repository("vlprSpeciallyResultDao")
public class VlprSpeciallyResultDaoImpl extends BaseDaoImpl<VlprSpeciallyResult> implements VlprSpeciallyResultDao{
	
	/**
	 * 查询以图收车任务图片识别结果
	 * @param rq
	 * @return
	 */
	@Override
	public Pager querySerachCarByImageResult(final ResultQuery rq) {

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
						"from vlpr_specially_result v ").append(sbJoin);

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
				if (list != null && list.size() > 0) {
					rList = new ArrayList<ResultVo>();
					for (int i = 0; i < list.size(); i++) {
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
	 * 向ResultVo对象中填充特征物识别信息
	 * 
	 * @param vo vo类
	 * @param featureList 特征物识别结果
	 */
	private void fillFeatureInfo(ResultVo vo,List featureList){
		if(featureList==null){
			return;
		}
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
}
