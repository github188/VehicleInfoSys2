package cn.jiuling.vehicleinfosys2.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.jiuling.vehicleinfosys2.dao.FakeLicenseDao;
import cn.jiuling.vehicleinfosys2.model.FakeLicenseCarInfo;
import cn.jiuling.vehicleinfosys2.vo.FakeLicenseQuery;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * 
 * 假套牌车查询DAO impl
 * 
 * @author daixiaowei
 *
 */
@Repository("fakeLicenseDao")
public class FakeLicenseDaoImpl extends BaseDaoImpl<FakeLicenseCarInfo> implements FakeLicenseDao {
	
	/**
	 * 套牌车实时报警分页查询假套牌车辆信息
	 */
	@Override
	public Pager realTimelist(FakeLicenseQuery query, Integer page, Integer rows) {
		
		StringBuilder sbSql = new StringBuilder("from FakeLicenseCarInfo where 1=1 ");
		
		//告警时间
		String warnTime = query.getQueryWarnTime();
		if(!StringUtils.isEmpty(warnTime)){
			String[] warnTimes = warnTime.split(",");
			if(warnTimes.length == 1){
				if(warnTime.contains("today")){
					sbSql.append(" and to_days(warnTime) = to_days(now()) ");
				}
				if(warnTime.contains("yesterday")){
					sbSql.append(" and to_days(now()) - to_days(warnTime) = 1 ");
				}
				if(warnTime.contains("before")){
					sbSql.append(" and datediff(curdate(),warnTime) >= 2 ");
				}
			}
			
			if(warnTimes.length == 2){
				if(warnTime.contains("today") && warnTime.contains("yesterday")){
					sbSql.append(" and datediff(curdate(),warnTime) <= 1 ");
				}
				
				if(warnTime.contains("today") && warnTime.contains("before")){
					sbSql.append(" and datediff(curdate(),warnTime) != 1 ");
				}
				
				if(warnTime.contains("yesterday") && warnTime.contains("before")){
					sbSql.append(" and datediff(curdate(),warnTime) >= 1 ");
				}
			}
			
		}else {
			sbSql.append(" and 1!=1 ");
		}
		
		//处理状态
		String optionState = query.getOptionState();
		if(!StringUtils.isEmpty(optionState)){
			String[] optionStates = optionState.split(",");
			StringBuffer optionStateParam = new StringBuffer();
			
			for(int i=0;i<optionStates.length;i++){
				
				if(i==optionStates.length-1){
					optionStateParam.append("'"+optionStates[i]+"'");
				}else{
					optionStateParam.append("'"+optionStates[i]+"',");
				}
			}
			
			sbSql.append(" and optionState in ("+optionStateParam.toString()+") ");
		}else {
			sbSql.append(" and 1!=1 ");
		}
		
		//报警类型
		String warnType = query.getWarnType();
		if(!StringUtils.isEmpty(warnType)){
			String[] warnStates = warnType.split(",");
			StringBuffer warnParam = new StringBuffer();
			
			for(int i=0;i<warnStates.length;i++){
				
				if(i==warnStates.length-1){
					warnParam.append("'"+warnStates[i]+"'");
				}else{
					warnParam.append("'"+warnStates[i]+"',");
				}
			}
			
			sbSql.append(" and warnType in ("+warnParam.toString()+") ");
		}else {
			sbSql.append(" and 1!=1 ");
		}
		
		//可信度
		Integer confidence = query.getConfidence();
		if(confidence != null){
			sbSql.append(" and confidence >= "+confidence);
		}
		
		//监控点名称
		String camerName = query.getCamerName();
		if(!StringUtils.isEmpty(camerName)){
			
			String[] camerNames = camerName.split(",");
			StringBuffer camerNameParam = new StringBuffer();
			
			for(int i=0;i<camerNames.length;i++){
				
				if(i==camerNames.length-1){
					camerNameParam.append("'"+camerNames[i]+"'");
				}else{
					camerNameParam.append("'"+camerNames[i]+"',");
				}
			}
			
			sbSql.append(" and camerName in ("+camerNameParam.toString()+") ");
		}
		
		Long total = count("select count(*) " + sbSql.toString(), null);
		List list = super.find(sbSql.toString(), null, page, rows);
		Pager p = new Pager(total, list);
		
		return p;
						
	}
	
	
	/**
	 * 查询假套牌车辆信息(查询页面)
	 */
	@Override
	public Pager queryFakeLicenseCarInfolist(FakeLicenseQuery query, Integer page, Integer rows) {
		
		StringBuilder sbSql = new StringBuilder("from FakeLicenseCarInfo where 1=1 ");
		
		//车牌号码
		String license = query.getLicense();
		if(!StringUtils.isEmpty(license)){
			sbSql.append(" and license like '%"+license+"%' ");
		}
		
		//车牌种类
		String plateType = query.getPlateType();
		if(!StringUtils.isEmpty(plateType)){
			String[] plateTypes = plateType.split(",");
			StringBuffer plateTypeParam = new StringBuffer();
			
			for(int i=0;i<plateTypes.length;i++){
				
				if(i==plateTypes.length-1){
					plateTypeParam.append(plateTypes[i]);
				}else{
					plateTypeParam.append(plateTypes[i]+",");
				}
			}
			
			sbSql.append(" and plateType in ("+plateTypeParam.toString()+") ");
		}
		
		//车辆类型
		String viehicleKind = query.getViehicleKind();
		if(!StringUtils.isEmpty(viehicleKind)){
			String[] viehicleKinds = viehicleKind.split(",");
			StringBuffer viehicleKindParam = new StringBuffer();
			
			for(int i=0;i<viehicleKinds.length;i++){
				
				if(i==viehicleKinds.length-1){
					viehicleKindParam.append("'"+viehicleKinds[i]+"'");
				}else{
					viehicleKindParam.append("'"+viehicleKinds[i]+"',");
				}
			}
			
			sbSql.append(" and viehicleKind in ("+viehicleKindParam.toString()+") ");
		}
		
		//车辆颜色
		String carColor = query.getCarColor();
		if(!StringUtils.isEmpty(carColor)){
			String[] carColors = carColor.split(",");
			StringBuffer carColorParam = new StringBuffer();
			
			for(int i=0;i<carColors.length;i++){
				
				if(i==carColors.length-1){
					carColorParam.append("'"+carColors[i]+"'");
				}else{
					carColorParam.append("'"+carColors[i]+"',");
				}
			}
			
			sbSql.append(" and carColor in ("+carColorParam.toString()+") ");
		}

		//过车时间段
		String startTime = query.getStartTime();
		if(!StringUtils.isEmpty(startTime)){
			sbSql.append(" and vehicelTime >= '"+startTime+"' ");
		}
		
		String endTime = query.getEndTime();
		if(!StringUtils.isEmpty(endTime)){
			sbSql.append(" and vehicelTime <= '"+endTime+"' ");
		}
		
				
		//报警类型
		String warnType = query.getWarnType();
		if(!StringUtils.isEmpty(warnType)){
			String[] warnStates = warnType.split(",");
			StringBuffer warnParam = new StringBuffer();
			
			for(int i=0;i<warnStates.length;i++){
				
				if(i==warnStates.length-1){
					warnParam.append("'"+warnStates[i]+"'");
				}else{
					warnParam.append("'"+warnStates[i]+"',");
				}
			}
			
			sbSql.append(" and warnType in ("+warnParam.toString()+") ");
		}
		
		//人工审核
		String manulAudit = query.getManulAudit();
		if(!StringUtils.isEmpty(manulAudit)){
			String[] manulAudits = manulAudit.split(",");
			StringBuffer manulAuditParam = new StringBuffer();
			
			for(int i=0;i<manulAudits.length;i++){
				
				if(i==manulAudits.length-1){
					manulAuditParam.append("'"+manulAudits[i]+"'");
				}else{
					manulAuditParam.append("'"+manulAudits[i]+"',");
				}
			}
			
			sbSql.append(" and manulAudit in ("+manulAuditParam.toString()+") ");
		}
		
		
		//可信度
//		Integer confidence = query.getConfidence();
//		if(confidence != null){
//			sbSql.append(" and confidence >= "+confidence);
//		}
		
		//监控点名称
		String camerName = query.getCamerName();
		if(!StringUtils.isEmpty(camerName)){
			
			String[] camerNames = camerName.split(",");
			StringBuffer camerNameParam = new StringBuffer();
			
			for(int i=0;i<camerNames.length;i++){
				
				if(i==camerNames.length-1){
					camerNameParam.append("'"+camerNames[i]+"'");
				}else{
					camerNameParam.append("'"+camerNames[i]+"',");
				}
			}
			
			sbSql.append(" and camerName in ("+camerNameParam.toString()+") ");
		}
		
		Long total = count("select count(*) " + sbSql.toString(), null);
		List list = super.find(sbSql.toString(), null, page, rows);
		Pager p = new Pager(total, list);
		
		return p;
						
	}
	
	/**
	 * 查询最大id
	 */
	@Override
	public Long queryMaxId(){
		
		String querString = "select MAX(id) from FakeLicenseCarInfo";
		Long maxId = (Long)super.getHibernateTemplate().find(querString).get(0);
		
		return maxId;
	}

}
