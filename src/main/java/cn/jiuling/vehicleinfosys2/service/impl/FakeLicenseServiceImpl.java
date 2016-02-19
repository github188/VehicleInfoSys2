package cn.jiuling.vehicleinfosys2.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.jiuling.vehicleinfosys2.dao.FakeLicenseDao;
import cn.jiuling.vehicleinfosys2.dao.VehicleRegistrationGovernmentDao;
import cn.jiuling.vehicleinfosys2.dao.VlprAppearanceDao;
import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.model.FakeLicenseCarInfo;
import cn.jiuling.vehicleinfosys2.model.VehicleRegistrationGovernment;
import cn.jiuling.vehicleinfosys2.model.VlprAppearance;
import cn.jiuling.vehicleinfosys2.model.VlprResult;
import cn.jiuling.vehicleinfosys2.service.FakeLicenseService;
import cn.jiuling.vehicleinfosys2.vo.FakeLicenseQuery;
import cn.jiuling.vehicleinfosys2.vo.Pager;

/**
 * 
 * 假套牌车业务处理
 * @author daixiaowei
 *
 */
@Service("fakeLicenseService")
public class FakeLicenseServiceImpl implements FakeLicenseService {
	
	private Logger log = Logger.getLogger(FakeLicenseServiceImpl.class);
	
	@Resource
	private FakeLicenseDao fakeLicenseDao;
	
	@Resource
	private VehicleRegistrationGovernmentDao vehicleRegistrationGovernmentDao;
	
	@Resource
	private VlprResultDao vlprResultDao;
	
	@Resource
	private VlprAppearanceDao vlprAppearanceDao;
	
	/**
	 * 套牌车实时报警分页查询假套牌车辆信息
	 */
	@Override
	public Pager uploadRealTimelist(FakeLicenseQuery query, Integer page, Integer rows) {
		
		//跟新呈现表最大页面展示id
		Pager positionInfoPage = vlprAppearanceDao.querAppearance();				
		if(positionInfoPage.getTotal()>0){
			Long maxId = fakeLicenseDao.queryMaxId();
			if(maxId == null){
				maxId= 0L;
			}
			vlprAppearanceDao.updatePagePositionAppearance(maxId);
		}
		
		return fakeLicenseDao.realTimelist(query, page, rows);
	}
	
	/**
	 * 查询假套牌车辆信息(查询页面)
	 */
	@Override
	public Pager querylist(FakeLicenseQuery query, Integer page, Integer rows) {
		
		return fakeLicenseDao.queryFakeLicenseCarInfolist(query, page, rows);
	}
	
	/**
	 * 查询车管所车辆信息
	 */
	@Override
	public Pager queryVehicleRegistration(VehicleRegistrationGovernment query,
			Integer page, Integer rows) {
		
		Pager pageInfo = vehicleRegistrationGovernmentDao.list(query, page, rows);
		
		return pageInfo;
	}

	@Override
	public Pager list(FakeLicenseCarInfo query, Integer page, Integer rows) {
		Pager pager = fakeLicenseDao.list(query, page, rows);
		return pager;
	}

	@Override
	public void update(FakeLicenseCarInfo updater) {
		
		String updateSql = "update vlpr_fakeLicensed set manulAudit = ?,optionState = ? where id = ?";
		Object paramArr[] = new Object[3];
		
		paramArr[0]= updater.getManulAudit();
		paramArr[1] = updater.getOptionState();
		paramArr[2] = updater.getId();
		
		fakeLicenseDao.updateOrDelete(updateSql, paramArr);
	}
	
	@Override
	public void saveFakeLicenseAnalyze(){
		/**
		 *假套牌车分析业务处理 
		 */			
		//查询本次处理的起始位置
        Pager positionInfoPage = vlprAppearanceDao.querAppearance();
        VlprAppearance vlprAppearance = (VlprAppearance) (positionInfoPage.getRows().get(0));
        Long minSeriNumber = vlprAppearance.getThreadPosition();
        //查询数据库中最大的seriNumber
        Long maxSeriNumber = vlprResultDao.queryMaxSeriNumber(minSeriNumber,10);

		//查询识别结果
		List<VlprResult> result = (List<VlprResult>) vlprResultDao.queryResultBetweenBySeriNumber(maxSeriNumber, minSeriNumber);
		
		for(int i=0;i<result.size();i++){
			
			VlprResult item = result.get(i);
			
			//处理到最后一个元素时把位置信息写入位置表
			if(i==result.size()-1){
				Long minsn = item.getSerialNumber();
				vlprAppearanceDao.updateThreadPositionAppearance(minsn);
			}
						
			//空牌车分析
			if("无车牌".equals(item.getLicense())){
				
				FakeLicenseCarInfo destItme = new FakeLicenseCarInfo();
				copyProperties(item,destItme);
				destItme.setWarnType("无牌车");
				
				//插入数据库
				fakeLicenseDao.save(destItme);
				
				continue;
			}
			
			//根据车牌号查询车管所数据
			VehicleRegistrationGovernment query = new VehicleRegistrationGovernment();
			query.setLicense(item.getLicense());
			Pager page = vehicleRegistrationGovernmentDao.list(query,null, null);
			
			//假牌车分析
			if(page.getTotal() == 0){
				
				FakeLicenseCarInfo destItme = new FakeLicenseCarInfo();
				copyProperties(item,destItme);
				destItme.setWarnType("假牌车");
				
				//插入数据库
				fakeLicenseDao.save(destItme);
				
				continue;
			}
			
			//套牌车分析
			VehicleRegistrationGovernment vehicleRegistrationGovernment = (VehicleRegistrationGovernment)page.getRows().get(0);
			
			boolean palteFage = true;
			boolean carColor = true;
			boolean vhKind = true;
			boolean vhtype = true;
			//车牌种类不同
			if(item.getPlateType().intValue() != vehicleRegistrationGovernment.getPlateType()){
				palteFage = false;
			}
			//车辆颜色
			if(!item.getCarColor().equalsIgnoreCase(vehicleRegistrationGovernment.getCarColor())){
				carColor = false;
			}
			//车辆类型
			if(!item.getVehicleKind().equalsIgnoreCase(vehicleRegistrationGovernment.getVehicleKind())){
				vhKind = false;
			}
			//品牌型号
			StringBuffer mybss = new StringBuffer("");
			if(!StringUtils.isEmpty(item.getVehicleBrand())){
				mybss.append(item.getVehicleBrand());
			}
			if(!StringUtils.isEmpty(item.getVehicleSeries())){
				mybss.append(item.getVehicleSeries());
			}
			if(!StringUtils.isEmpty(item.getVehicleStyle())){
				mybss.append(item.getVehicleStyle());
			}
			
			StringBuffer governmentbss = new StringBuffer("");
			if(!StringUtils.isEmpty(vehicleRegistrationGovernment.getVehicleBrand())){
				governmentbss.append(vehicleRegistrationGovernment.getVehicleBrand());
			}
			if(!StringUtils.isEmpty(vehicleRegistrationGovernment.getVehicleSeries())){
				governmentbss.append(vehicleRegistrationGovernment.getVehicleSeries());
			}
			if(!StringUtils.isEmpty(vehicleRegistrationGovernment.getVehicleStyle())){
				governmentbss.append(vehicleRegistrationGovernment.getVehicleStyle());
			}
			
			if(!mybss.toString().equalsIgnoreCase(governmentbss.toString())){			
				vhtype = false;
			}
			
			if(!(palteFage&&carColor&&vhKind&&vhtype)){
				
				FakeLicenseCarInfo destItme = new FakeLicenseCarInfo();
				copyProperties(item,destItme);
				destItme.setWarnType("套牌车");
				
				//插入数据库
				fakeLicenseDao.save(destItme);
			}			
		}				
	}
	
	@Override
	public Long uploadDifferenceNumber(){
		Long num = 0L;
		
		Pager positionInfoPage = vlprAppearanceDao.querAppearance();
		//获取pagePosition
		VlprAppearance vlprAppearance = (VlprAppearance) (positionInfoPage.getRows().get(0));
		Long pagePosition = vlprAppearance.getPagePosition();
		
		//获取vlpr_fakelicensed表中的最大id
		Long maxId = fakeLicenseDao.queryMaxId();
		num = maxId - pagePosition;
		
		if(num>0){
			//更新页面配置信息
			vlprAppearanceDao.updatePagePositionAppearance(maxId);
		}
		return num; 
	}
	
	private void copyProperties(VlprResult src, FakeLicenseCarInfo des){
		
		des.setSerialNumber(src.getSerialNumber());
		des.setWarnTime(new Timestamp(System.currentTimeMillis()));
		des.setVehicelTime(src.getResultTime());
		String camerName = vlprResultDao.queryCameraNameBySeriNumber(src.getSerialNumber());
		//监控点名称
		des.setCamerName(camerName);
		des.setWarnType("");
		des.setManulAudit("未审核");
		des.setLicense(src.getLicense());
		des.setPlateType(new Integer(src.getPlateType()));
		des.setViehicleKind(src.getVehicleKind());
		des.setCarColor(src.getCarColor());
		des.setVehicleBrand(src.getVehicleBrand());
		des.setVehicleSeries(src.getVehicleSeries());
		des.setVehicleStyle(src.getVehicleStyle());
		des.setConfidence(new Integer(src.getConfidence()));
		des.setOptionState("未处理");		
	}

}
