package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.CollectPicturesDao;
import cn.jiuling.vehicleinfosys2.model.VlprCollectPictures;
import cn.jiuling.vehicleinfosys2.util.DateUtils;
import cn.jiuling.vehicleinfosys2.vo.CollectPicturesVo;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("taskDownloadDao")
public class CollectPicturesDaoImpl extends BaseDaoImpl<VlprCollectPictures> implements CollectPicturesDao {

	@Override
	public Pager list(CollectPicturesVo cpv, Integer page, Integer rows) {
		StringBuilder sb = new StringBuilder("from VlprCollectPictures vcp where 1=1 ");
		if(cpv.getCameraNames()!=null && !"".equals(cpv.getCameraNames())){
			sb.append(" and vcp.cameraName in (");
			String cameraNames[] = cpv.getCameraNames().split(",");
			for (int i = 0; i < cameraNames.length; i ++){
				sb.append("'"+cameraNames[i]+"'");
				sb.append(",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append(")");
		}

		if(cpv.getStatus()!=null && !"".equals(cpv.getStatus())) {
			if (cpv.getStatus() == 5) {//进行中的任务状态（等待中、下载中、暂停中）
				sb.append(" and vcp.status in (0,1,3)");
			} else if (cpv.getStatus() == 6) { //已结束的任务状态（已完成、已终止）
				sb.append(" and vcp.status in (2,4)");
			}else{
				sb.append(" and vcp.status = " +cpv.getStatus());
			}
		}
		// 查询总记录数
		Long total = (Long) getHibernateTemplate().find("select count(*) " + sb.toString()).get(0);

		sb.append(" order by vcp.createTime desc");

		List list = find(sb.toString(), null, page, rows);
		return new Pager(total, list);
	}

	@Override
	public List list(Short status) {
		StringBuilder hql = new StringBuilder(" from VlprCollectPictures vcp where vcp.status=" + status + " order by vcp.id desc");
		return getHibernateTemplate().find(hql.toString());
	}

	@Override
	public List list(Short status[]) {
		StringBuilder sb = new StringBuilder(" from VlprCollectPictures vcp where 1=1 ");
		if (null != status && status.length > 0) {
			sb.append(" and vcp.status in (");
			for (int i = 0; i < status.length; i++) {
				sb.append(status[i]);
				sb.append(",");
			}
			sb.deleteCharAt(sb.lastIndexOf(","));
			sb.append(")");
		}

		sb.append(" order by vcp.id desc");
		return getHibernateTemplate().find(sb.toString());
	}

	@Override
	public void updateRealCollectPictrue(VlprCollectPictures vcp) {
		if(vcp.getStatus() == 0 || 	vcp.getStatus() == 3 || vcp.getStatus() == 4){ //等待、暂停、终止状态
			StringBuilder sql = new StringBuilder("update VlprCollectPictures vcp ");
			sql.append(" set vcp.status=" + vcp.getStatus());
			sql.append(" where vcp.id=?");
			super.getHibernateTemplate().bulkUpdate(sql.toString(), vcp.getId());
		}else{
			StringBuilder sql = new StringBuilder("update VlprCollectPictures vcp ");
			sql.append(" set vcp.downloadCount=" + vcp.getDownloadCount());
			if(vcp.getMaxRecordId() !=null){
				sql.append(" ,vcp.maxRecordId=" + vcp.getMaxRecordId());
			}
			if(vcp.getPassTime() !=null){
				sql.append(" ,vcp.passTime='" + DateUtils.formateTime(vcp.getPassTime()) + "'");
			}
			if(vcp.getStatus() == 2){ //已完成
				sql.append(" ,vcp.status=" + vcp.getStatus());
			}else if(vcp.getStatus() == 1){
				//如果该任务状态为等待中，则改为下载中
				StringBuilder updateStatusSql = new StringBuilder("update VlprCollectPictures vcp set vcp.status="+vcp.getStatus());
				updateStatusSql.append(" where vcp.status=0 and vcp.id=?");
				super.getHibernateTemplate().bulkUpdate(updateStatusSql.toString(), vcp.getId());
			}
			sql.append(" where vcp.id=?");
			super.getHibernateTemplate().bulkUpdate(sql.toString(), vcp.getId());
		}
	}
}

