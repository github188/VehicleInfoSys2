package cn.jiuling.vehicleinfosys2.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.jiuling.vehicleinfosys2.dao.VlprAppearanceDao;
import cn.jiuling.vehicleinfosys2.model.VlprAppearance;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@Repository("vlprAppearanceDao")
public class VlprApearanceDaoImpl extends BaseDaoImpl<VlprAppearance> implements VlprAppearanceDao{
	
	/**
	 * 查询位置信息
	 * 
	 * @return pager
	 */
	@Override
	public Pager querAppearance(){
		String querSql = "from VlprAppearance ";
		
		Long total = super.count("select count(*) " + querSql, null);
		List list = super.find(querSql, null, null, null);
		Pager pager = new Pager(total,list);
		
		return pager;
	}
	
	/**
	 * 更新线程读取数据库的位置信息
	 */
	@Override
	public void updateThreadPositionAppearance(Long seriNumber){
		
		String querSql = " update VlprAppearance set threadPosition = ? ";
		super.getHibernateTemplate().bulkUpdate(querSql, seriNumber);
	}
	
	/**
	 * 更新页面读取数据库的信息
	 */
	@Override
	public void updatePagePositionAppearance(Long id){
		
		String querSql = " update VlprAppearance set pagePosition = ? ";
		super.getHibernateTemplate().bulkUpdate(querSql, id);
	}
		
}
