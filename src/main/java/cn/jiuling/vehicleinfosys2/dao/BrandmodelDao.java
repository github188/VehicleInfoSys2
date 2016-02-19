package cn.jiuling.vehicleinfosys2.dao;

import java.util.List;

import cn.jiuling.vehicleinfosys2.model.BrandModel;

public interface BrandmodelDao{
	/**
	 * 查找所有汽车品牌
	 */
	public List<BrandModel> listBrand();

	/**
	 * 查找所有汽车车系
	 *
	 * @param brandName
	 */
	public List<BrandModel> listSubbrand(String brandName);

	/**
	 * 查找所有汽车车款
	 *
	 * @param brandName
	 */
	public List<BrandModel> listCar(String brandName, String carSeries);
}
