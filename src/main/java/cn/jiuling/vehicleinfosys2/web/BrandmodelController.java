package cn.jiuling.vehicleinfosys2.web;

import cn.jiuling.vehicleinfosys2.model.BrandModel;
import cn.jiuling.vehicleinfosys2.service.BrandmodelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 品牌型号联动Controller
 * 
 * @author bl
 * 
 * @date 2015-07-21
 */
@Controller
@RequestMapping("brandmodel")
public class BrandmodelController {
	@Resource
	private BrandmodelService brandmodelService;

	@RequestMapping("listbrand.action")
	@ResponseBody
	public List<BrandModel> listBrand() {
		List<BrandModel> listBrand = brandmodelService.listBrand();
		return listBrand;
	}

	@RequestMapping("listsubbrand.action")
	@ResponseBody
	public List<BrandModel> listSubbrand(String brandName) {
		List<BrandModel> listSubbrand = brandmodelService.listSubbrand(brandName);
		return listSubbrand;
	}

	@RequestMapping("listcar.action")
	@ResponseBody
	public List<BrandModel> listCar(String brandName, String carSeries) {
		List<BrandModel> listCar = brandmodelService.listCar(brandName, carSeries);
		return listCar;
	}
	
}
