package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.BrandmodelDao;
import cn.jiuling.vehicleinfosys2.model.BrandModel;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("brandmodelDao")
public class BrandmodelDaoImpl extends HibernateDaoSupport implements BrandmodelDao  {

	@Autowired
	public void setSessionFactoryOverride(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	@Override
	public List<BrandModel> listBrand(){
		String sql = " from BrandModel t group by t.brandName order by t.id";
		List<BrandModel> listBrand = getHibernateTemplate().find(sql);
		return listBrand;
	}

	@Override
	public List<BrandModel> listSubbrand(String brandName){
		String sql = " from BrandModel t where t.brandName = '" + brandName +"' group by t.carSeries order by t.id";
		List<BrandModel> listSubbrand = getHibernateTemplate().find(sql);
		return listSubbrand;
	}

	@Override
	public List<BrandModel> listCar(String brandName, String carSeries){
		String sql = " from BrandModel t where t.brandName = '" + brandName +"' and t.carSeries = '" + carSeries +"' order by t.id";
		List<BrandModel> listCar = getHibernateTemplate().find(sql);
		return listCar;
	}
}
