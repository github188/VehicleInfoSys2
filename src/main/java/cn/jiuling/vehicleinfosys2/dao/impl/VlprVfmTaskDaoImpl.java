package cn.jiuling.vehicleinfosys2.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jiuling.vehicleinfosys2.dao.VlprVfmTaskDao;
import cn.jiuling.vehicleinfosys2.model.VlprVfmTask;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * 以图搜车任务dao
 * @author daixiaowei
 *
 */
@Repository("vlprVfmTaskDao")
public class VlprVfmTaskDaoImpl extends BaseDaoImpl<VlprVfmTask> implements VlprVfmTaskDao {
	
	
	/**
	 * 停止以图搜车任务
	 * @param taskId
	 * @return int
	 */
	@Override
	public int stopVlprVfmTask(Long taskId){
		String updateSql = "update VlprVfmTask  set flag = 0 where taskID = ? ";
		
		int updateItemNum = super.getHibernateTemplate().bulkUpdate(updateSql, taskId);
		
		return updateItemNum;
		
	}
	
	@Override
	public Pager getVlprVfmTask(VlprVfmTask vlprVfmTask,Integer page, Integer rows){
		StringBuffer querSql = new StringBuffer(" from VlprVfmTask vt where 1=1 ");
		StringBuffer countSql = new StringBuffer(" select count(*) from VlprVfmTask vt where 1=1 ");
		
		if(vlprVfmTask.getPlateType() !=null){
			querSql.append(" and plateType = "+vlprVfmTask.getPlateType());
			countSql.append(" and plateType = "+vlprVfmTask.getPlateType());
		}
		
		if(vlprVfmTask.getCameraId() !=null){
			String[] cIds = vlprVfmTask.getCameraId().split(",");
			StringBuffer subStr = new StringBuffer(); 
			for(int i=0;i<cIds.length;i++){
				if(i== cIds.length-1){
					subStr.append( "find_in_set("+cIds[i]+",cameraId)>0 ");
				}else{
					subStr.append( "find_in_set("+cIds[i]+",cameraId)>0 or ");
				}
			}
			querSql.append(" and ("+subStr.toString()+")");
			countSql.append(" and ("+subStr.toString()+")");
		}
		
		if(vlprVfmTask.getVehicleColor() !=null){
			querSql.append(" and vehicleColor ='"+vlprVfmTask.getVehicleColor()+"'");
			countSql.append(" and vehicleColor ='"+vlprVfmTask.getVehicleColor()+"'");
		}
		
		if(vlprVfmTask.getVehicleKind() !=null){
			querSql.append(" and vehicleKind ='"+vlprVfmTask.getVehicleKind()+"'");
			countSql.append(" and vehicleKind ='"+vlprVfmTask.getVehicleKind()+"'");
		}
		
		if(vlprVfmTask.getVehicleBrand() !=null){
			querSql.append(" and vehicleBrand ='"+vlprVfmTask.getVehicleBrand()+"'");
			countSql.append(" and vehicleBrand ='"+vlprVfmTask.getVehicleBrand()+"'");
		}
		
		if(vlprVfmTask.getVehicleSeries() !=null){
			querSql.append(" and vehicleSeries ='"+vlprVfmTask.getVehicleSeries()+"'");
			countSql.append(" and vehicleSeries ='"+vlprVfmTask.getVehicleSeries()+"'");
		}
		
		if(vlprVfmTask.getVehicleStyle() !=null){
			querSql.append(" and vehicleStyle ='"+vlprVfmTask.getVehicleStyle()+"'");
			countSql.append(" and vehicleStyle ='"+vlprVfmTask.getVehicleStyle()+"'");
		}
		
		if(vlprVfmTask.getLicense() !=null){
			querSql.append(" and license like '%"+vlprVfmTask.getLicense()+"%'");
			countSql.append(" and license like '%"+vlprVfmTask.getLicense()+"%'");
		}
		
		List list = this.find(querSql.toString(), null, page, rows);
		Long tc = this.count(countSql.toString(), null);
	    
		Long count=0L;
		Pager pager = new Pager();
		
 		if(tc!=null){
 			pager.setTotal(tc);
 		}else{
 			pager.setTotal(0L);
 		}		
 		pager.setRows(list);
		return pager;
	}
}
