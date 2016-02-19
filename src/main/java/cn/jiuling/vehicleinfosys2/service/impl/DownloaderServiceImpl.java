package cn.jiuling.vehicleinfosys2.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.jiuling.vehicleinfosys2.util.ExcelFileUtil;
import cn.jiuling.vehicleinfosys2.util.FileUtils;
import cn.jiuling.vehicleinfosys2.util.FileZipCompressorUtil;
import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.dao.VlprVfmResultDao;
import cn.jiuling.vehicleinfosys2.dao.VlprVfmTaskDao;
import cn.jiuling.vehicleinfosys2.model.VlprVfmTask;
import cn.jiuling.vehicleinfosys2.service.DownloaderService;
import cn.jiuling.vehicleinfosys2.util.PropertiesUtils;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;
import cn.jiuling.vehicleinfosys2.vo.SearchCarByImageVo;

/**
 * 
 * 下载service
 * 
 * @author daixiaowei
 */
@Service("downloaderService")
public class DownloaderServiceImpl implements DownloaderService {

	@Resource
	private VlprResultDao vlprResultDao;
	
	@Resource
	private VlprVfmResultDao vlprVfmResultDao;
	
	@Resource
	private VlprVfmTaskDao vlprVfmTaskDao;
	
	/**
	 * 计算下载的条目数
	 * @param rq
	 * @return Integer
	 */
	@Override
	public Integer downloadItemCount(ResultQuery rq){
		
		Integer count = vlprResultDao.queryMunityResultCount(rq);		
		return count;
	}
	
	/**
	 * 计算下载的条目数
	 * @param taskId 任务id
	 * @return Integer
	 */
	@Override
	public Integer downloadItemCount(Integer taskId){
		
		Long countL = vlprResultDao.findMunityByTaskId(taskId).getTotal();		
		return countL.intValue();
	}

	/**
	 * 下载综合查询结果
	 * 
	 * @return String 文件路径
	 */
	@Override
	public String downloadTotalComprehensive(ResultQuery rq) {
		Pager pager = vlprResultDao.queryMunityResult(rq);
		
		return fileProcess(pager.getRows());
	}
	
	/**
	 * 下载综合查询结果（任务）
	 * 
	 * @return String 文件路径
	 */
	@Override
	public String downloadTotalComprehensive(Integer taskId) {
		Pager pager = vlprResultDao.findMunityByTaskId(taskId);
		
		return fileProcess(pager.getRows());
	}
	
	/**
	 * 下载综合查询结果(选择)
	 * 
	 * @return String 文件路径
	 */
	@Override
	public String downloadTotalChoose(String ids) {
		Pager pager = vlprResultDao.queryResults(ids);
		
		return fileProcess(pager.getRows());
	}

	/**
	 * 处理下载用的文件
	 */
	private String fileProcess(List resultList) {
		//压缩文件路径
		String zipFileDirName = "";
		
		//下载根文件夹路径
		String rootDir = PropertiesUtils.get("downloadPath");
		File rootFiles = new File(rootDir);
		if(rootFiles.exists()){	
			FileUtils.deleteFileOrDirector(rootFiles);
			rootFiles.mkdirs();
		}else{
			rootFiles.mkdirs();
		}
		
		//结果文件夹路径
		String resultFileDir = rootDir+File.separator+"vlprDownloadResult";
		File resultFile = new File(resultFileDir);
		if(!resultFile.exists()){
			resultFile.mkdir();
		}
		
		//车型
		String motorTypeDir = resultFileDir+File.separator+"车型";
		//品牌型号
		String motorBrandDir = resultFileDir+File.separator+"品牌型号";
		//颜色
		String motorColorDir = resultFileDir+File.separator+"车身颜色";
		//原图
		String motorOriginalDir = resultFileDir+File.separator+"图片";
		
		//创建车型文件夹
		File motorTypeFile = new File(motorTypeDir);
		if(!motorTypeFile.exists()){
			motorTypeFile.mkdir();
		}
		//创建品牌型号文件夹
		File motorBrandFile = new File(motorBrandDir);
		if(!motorBrandFile.exists()){
			motorBrandFile.mkdir();
		}
		//创建颜色文件夹
		File motorColorFile = new File(motorColorDir);
		if(!motorColorFile.exists()){
			motorColorFile.mkdir();
		}
		//创建原图文件夹
		File motorOriginalFile = new File(motorOriginalDir);
		if(!motorOriginalFile.exists()){
			motorOriginalFile.mkdir();
		}
		
		//生成分类图片
		for(Object item:resultList){
			ResultVo resultVo = (ResultVo) item;
			
			//车型Map
			Map<String,String> motorTypeMap = new HashMap<String,String>();
			//品牌型号Map
			Map<String,String> motorBrandMap = new HashMap<String,String>();
			//颜色Map
			Map<String,String> motorColorMap = new HashMap<String,String>();
			
			//拷贝原图
			File srcFile = new File(resultVo.getImagePath());
			FileUtils.copyFileToDirectory(srcFile,motorOriginalDir);
			
			//按颜色分类拷贝
			String carColor = resultVo.getCarColor();
			if(carColor!=null && !"".equals(carColor.trim())){
				if(motorColorMap.containsKey(carColor)){
					FileUtils.copyFileToDirectory(srcFile,motorColorMap.get(carColor));
				}else{
					String dir = motorColorDir+File.separator+carColor;
					File file = new File(dir);
					if(!file.exists()){
						file.mkdir();
					}
					motorColorMap.put(carColor, dir);
					FileUtils.copyFileToDirectory(srcFile,dir);					
				}
			}
			
			//按车型分类拷贝
			String vehicleKind = resultVo.getVehicleKind();
			if(vehicleKind!=null && !"".equals(vehicleKind.trim())){
				if(motorTypeMap.containsKey(vehicleKind)){
					FileUtils.copyFileToDirectory(srcFile,motorTypeMap.get(vehicleKind));
				}else{
					String dir = motorTypeDir+File.separator+vehicleKind;
					File file = new File(dir);
					if(!file.exists()){
						file.mkdir();
					}
					motorTypeMap.put(vehicleKind, dir);
					FileUtils.copyFileToDirectory(srcFile,dir);					
				}
			}
			
			//按品牌型号分类拷贝
			StringBuffer carBrandBuffer = new StringBuffer();
			if(resultVo.getVehicleBrand()!=null && !"".equals(resultVo.getVehicleBrand().trim())){
				carBrandBuffer.append(resultVo.getVehicleBrand());
			}
			if(resultVo.getVehicleSeries()!=null && !"".equals(resultVo.getVehicleSeries().trim())){
				carBrandBuffer.append("-"+resultVo.getVehicleSeries());
			}
			if(resultVo.getVehicleStyle()!=null && !"".equals(resultVo.getVehicleStyle().trim())){
				carBrandBuffer.append("-"+resultVo.getVehicleStyle());
			}
			
			String carBrand = carBrandBuffer.toString();
			
			if(carBrand ==null || "".equals(carBrand.trim())){
				carBrand = "XXXXX_品牌_未识别";
			}
			
			if(motorBrandMap.containsKey(carBrand)){
				FileUtils.copyFileToDirectory(srcFile,motorBrandMap.get(carBrand));
			}else{
				String dir = motorBrandDir+File.separator+carBrand;
				File file = new File(dir);
				if(!file.exists()){
					file.mkdir();
				}
				motorBrandMap.put(carBrand, dir);
				FileUtils.copyFileToDirectory(srcFile,dir);					
			}
		}
		
		//生成EXCEL文件
		String[] headers = {"车牌 ","车牌类型 ","车身颜色","车型 ","品牌 ","车系 ","款型 ","车牌归属地","车牌颜色","年检标 ","纸巾盒","遮阳板 ","挂饰 ","可信度","方向","地点 ","过车时间 ","车辆图片"};
		List<ResultVo> dataset = resultList;
		File excelFile = new File(resultFileDir+File.separator+"结果信息报表.xls");
		try {
			//生成excel文件
			ExcelFileUtil.exportExcel("结果信息", headers, dataset, new FileOutputStream(excelFile));
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		//生成的压缩文件名
		zipFileDirName = rootDir+File.separator+"vlprDownloadResult_"+df.format(new Date())+".zip";
		File rarFile = new File(zipFileDirName);
		if(rarFile.exists()){
			rarFile.delete();
		}
		//生成压缩文件
		FileZipCompressorUtil fileZipUtil = new FileZipCompressorUtil(zipFileDirName);
		fileZipUtil.compressExe(resultFileDir);
		
		return zipFileDirName;
	}
	
	/**
	 * 以图搜车业务处理
	 * 
	 * 
	 */
	
	/**
	 * 计算下载的条目数(以图搜车)
	 * @param taskId 任务id
	 * @return Integer
	 */
	@Override
	public Integer downloadScarItemCount(Integer taskVfmId){
		
		Integer countL = vlprVfmResultDao.queryMunityListCount(taskVfmId);		
		return countL;
	}
	
	/**
	 * 下载搜车结果（以图搜车）
	 * 
	 * @return String 文件路径
	 */
	@Override
	public String downloadScarTotal(Integer taskVfmId) {
		Pager pager = vlprVfmResultDao.queryMunityList(taskVfmId);
		VlprVfmTask vlprVfmTask = vlprVfmTaskDao.findByProperty("taskID", taskVfmId.longValue());
		
		return fileScarProcess(pager.getRows(),vlprVfmTask.getDesImagePath());
	}
	
	
	/**
	 * 处理下载用的文件(以图搜车)
	 */
	private String fileScarProcess(List resultList,String originalImageDir) {
		//压缩文件路径
		String zipFileDirName = "";
		
		//下载根文件夹路径
		String rootDir = PropertiesUtils.get("downloadPath");
		File rootFiles = new File(rootDir);
		if(rootFiles.exists()){	
			FileUtils.deleteFileOrDirector(rootFiles);
			rootFiles.mkdirs();
		}else{
			rootFiles.mkdirs();
		}
		
		//结果文件夹路径
		String resultFileDir = rootDir+File.separator+"vlprDownloadResult";
		File resultFile = new File(resultFileDir);
		if(!resultFile.exists()){
			resultFile.mkdir();
		}
		
		//车型
		String motorTypeDir = resultFileDir+File.separator+"车型";
		//品牌型号
		String motorBrandDir = resultFileDir+File.separator+"品牌型号";
		//颜色
		String motorColorDir = resultFileDir+File.separator+"车身颜色";
		//原图
		String motorOriginalDir = resultFileDir+File.separator+"原图";
		//搜车结果图片
		String motorResultDir = resultFileDir+File.separator+"搜车结果图片";
		
		//创建车型文件夹
		File motorTypeFile = new File(motorTypeDir);
		if(!motorTypeFile.exists()){
			motorTypeFile.mkdir();
		}
		//创建品牌型号文件夹
		File motorBrandFile = new File(motorBrandDir);
		if(!motorBrandFile.exists()){
			motorBrandFile.mkdir();
		}
		//创建颜色文件夹
		File motorColorFile = new File(motorColorDir);
		if(!motorColorFile.exists()){
			motorColorFile.mkdir();
		}		
		//创建原图文件夹
		File motorOriginalFile = new File(motorOriginalDir);
		if(!motorOriginalFile.exists()){
			motorOriginalFile.mkdir();
		}
		
		//创建搜车结果图片文件夹
		File motorResultFile = new File(motorResultDir);
		if(!motorResultFile.exists()){
			motorResultFile.mkdir();
		}
		
		//拷贝原始图片
		File originalFile = new File(originalImageDir);
		FileUtils.copyFileToDirectory(originalFile,motorOriginalDir);
				
		//生成分类图片
		for(Object item:resultList){
			SearchCarByImageVo resultVo = (SearchCarByImageVo) item;
			
			//车型Map
			Map<String,String> motorTypeMap = new HashMap<String,String>();
			//品牌型号Map
			Map<String,String> motorBrandMap = new HashMap<String,String>();
			//颜色Map
			Map<String,String> motorColorMap = new HashMap<String,String>();
			
			//拷贝搜车结果图片
			File srcFile = new File(resultVo.getImagePath());
			FileUtils.copyFileToDirectory(srcFile,motorResultDir);
			
			//按颜色分类拷贝
			String carColor = resultVo.getCarColor();
			if(carColor!=null && !"".equals(carColor.trim())){
				if(motorColorMap.containsKey(carColor)){
					FileUtils.copyFileToDirectory(srcFile,motorColorMap.get(carColor));
				}else{
					String dir = motorColorDir+File.separator+carColor;
					File file = new File(dir);
					if(!file.exists()){
						file.mkdir();
					}
					motorColorMap.put(carColor, dir);
					FileUtils.copyFileToDirectory(srcFile,dir);					
				}
			}
			
			//按车型分类拷贝
			String vehicleKind = resultVo.getVehicleKind();
			if(vehicleKind!=null && !"".equals(vehicleKind.trim())){
				if(motorTypeMap.containsKey(vehicleKind)){
					FileUtils.copyFileToDirectory(srcFile,motorTypeMap.get(vehicleKind));
				}else{
					String dir = motorTypeDir+File.separator+vehicleKind;
					File file = new File(dir);
					if(!file.exists()){
						file.mkdir();
					}
					motorTypeMap.put(vehicleKind, dir);
					FileUtils.copyFileToDirectory(srcFile,dir);					
				}
			}
			
			//按品牌型号分类拷贝
			StringBuffer carBrandBuffer = new StringBuffer();
			if(resultVo.getVehicleBrand()!=null && !"".equals(resultVo.getVehicleBrand().trim())){
				carBrandBuffer.append(resultVo.getVehicleBrand());
			}
			if(resultVo.getVehicleSeries()!=null && !"".equals(resultVo.getVehicleSeries().trim())){
				carBrandBuffer.append("-"+resultVo.getVehicleSeries());
			}
			if(resultVo.getVehicleStyle()!=null && !"".equals(resultVo.getVehicleStyle().trim())){
				carBrandBuffer.append("-"+resultVo.getVehicleStyle());
			}
			
			String carBrand = carBrandBuffer.toString();
			
			if(carBrand ==null || "".equals(carBrand.trim())){
				carBrand = "XXXXX_品牌_未识别";
			}
			
			if(motorBrandMap.containsKey(carBrand)){
				FileUtils.copyFileToDirectory(srcFile,motorBrandMap.get(carBrand));
			}else{
				String dir = motorBrandDir+File.separator+carBrand;
				File file = new File(dir);
				if(!file.exists()){
					file.mkdir();
				}
				motorBrandMap.put(carBrand, dir);
				FileUtils.copyFileToDirectory(srcFile,dir);					
			}
		}
		
		//生成EXCEL文件
		String[] headers = {"车牌 ","车牌类型 ","车身颜色","车型 ","品牌 ","车系 ","款型 ","车牌归属地","车牌颜色","年检标 ","纸巾盒","遮阳板 ","挂饰 ","地点 ","过车时间 ","匹配度","原始图片","搜车结果图片"};
		List<SearchCarByImageVo> dataset = resultList;
		File excelFile = new File(resultFileDir+File.separator+"搜车结果信息报表.xls");
		try {
			//生成excel文件
			ExcelFileUtil.exportScarExcel("搜车结果信息", headers, dataset, new FileOutputStream(excelFile),originalImageDir);
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		//生成的压缩文件名
		zipFileDirName = rootDir+File.separator+"vlprDownloadResult_"+df.format(new Date())+".zip";
		File rarFile = new File(zipFileDirName);
		if(rarFile.exists()){
			rarFile.delete();
		}
		//生成压缩文件
		FileZipCompressorUtil fileZipUtil = new FileZipCompressorUtil(zipFileDirName);
		fileZipUtil.compressExe(resultFileDir);
		
		return zipFileDirName;
	}
	
	

}
