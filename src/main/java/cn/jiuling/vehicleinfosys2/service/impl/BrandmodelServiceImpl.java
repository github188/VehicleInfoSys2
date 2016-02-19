package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.BrandmodelDao;
import cn.jiuling.vehicleinfosys2.model.BrandModel;
import cn.jiuling.vehicleinfosys2.service.BrandmodelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service("brandmodelService")
public class BrandmodelServiceImpl implements BrandmodelService {

	@Resource
	private BrandmodelDao brandmodelDao;

	@Override
	public List<BrandModel> listBrand(){
		List<BrandModel> listBrand = brandmodelDao.listBrand();
		return listBrand;
	}

	@Override
	public List<BrandModel> listSubbrand(String brandName){
		List<BrandModel> listSubbrand = brandmodelDao.listSubbrand(brandName);
		return listSubbrand;
	}

	@Override
	public List<BrandModel> listCar(String brandName, String carSeries){
		List<BrandModel> listCar = brandmodelDao.listCar(brandName, carSeries);
		return listCar;
	}
}
