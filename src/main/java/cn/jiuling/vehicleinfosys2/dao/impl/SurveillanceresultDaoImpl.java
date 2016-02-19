package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.SurveillanceresultDao;
import cn.jiuling.vehicleinfosys2.model.SurveillanceResult;
import cn.jiuling.vehicleinfosys2.model.VlprFeatures;
import cn.jiuling.vehicleinfosys2.vo.SurveillanceResultVo;
import org.springframework.stereotype.Repository;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository("surveillanceresultDao")
public class SurveillanceresultDaoImpl extends BaseDaoImpl<SurveillanceResult> implements SurveillanceresultDao {

    /**
     * 向ResultVo对象中填充特征物识别信息
     *
     * @param vo vo类
     * @param featureList 特征物识别结果
     */
    private void fillFeatureInfo(SurveillanceResultVo vo,List featureList){
        for(int i=0;i<featureList.size();i++){
            VlprFeatures oo = (VlprFeatures)featureList.get(i);

            //特征物名
            String featureName = oo.getFeatureName();
            //特征物位置信息
            String position = oo.getPosition();
            //可信度
            String reliability = oo.getReliability();

            if(StringUtils.isEmpty(position)){
                position = "[]";
            }
            if(StringUtils.isEmpty(reliability)){
                reliability = "[]";
            }

            //年检标签
            if("tag".equals(featureName)){
                vo.setTagRectAndScore("{\"rect\":"+position+",\"score\":"+reliability+"}");
            }
            //纸巾盒
            if("paper".equals(featureName)){
                vo.setPaperRectAndScore("{\"rect\":"+position+",\"score\":"+reliability+"}");
            }
            //遮阳板
            if("sun".equals(featureName)){
                vo.setSunRectAndScore("{\"rect\":"+position+",\"score\":"+reliability+"}");
            }
            //坠饰
            if("drop".equals(featureName)){
                vo.setDropRectAndScore("{\"rect\":"+position+",\"score\":"+reliability+"}");
            }
            //车前挡风玻璃窗口
            if("window".equals(featureName)){
                vo.setWindowRectAndScore("{\"rect\":"+position+",\"score\":"+reliability+"}");
            }
        }
    }

	@Override
	public Pager querylist(Integer surveillanceTaskId, Integer page, Integer rows){
		if(!StringUtils.isEmpty(surveillanceTaskId)){
			StringBuilder sb = new StringBuilder(" from SurveillanceResult s where s.surveillanceTaskId = '" + surveillanceTaskId +"'");
			Long total = count("select count(*) " + sb.toString(), null);
			List list1 = super.find(sb.toString(), null, page, rows);
            List list = new ArrayList();
            SurveillanceResult surveillanceResult = new SurveillanceResult();
            if (list1 != null && list1.size() > 0) {
                for (int i = 0; i < list1.size(); i++) {
                    SurveillanceResultVo surveillanceResultVo = new SurveillanceResultVo();
                    surveillanceResult = (SurveillanceResult)list1.get(i);
                    //获取SerialNumber
                    long serialNumber = surveillanceResult.getSerialNumber();
                    StringBuilder dsb = new StringBuilder(" from VlprFeatures v where v.serialNumber = '"+ serialNumber +"'");
                    List list2 = super.find(dsb.toString(), null, null, null);
                    surveillanceResultVo.setId(surveillanceResult.getId());
                    surveillanceResultVo.setSurveillanceTaskId(surveillanceResult.getSurveillanceTaskId());
                    surveillanceResultVo.setSerialNumber(surveillanceResult.getSerialNumber());
                    surveillanceResultVo.setLicense(surveillanceResult.getLicense());
                    surveillanceResultVo.setLicenseAttribution(surveillanceResult.getLicenseAttribution());
                    surveillanceResultVo.setPlateColor(surveillanceResult.getPlateColor());
                    surveillanceResultVo.setPlateType(surveillanceResult.getPlateType());
                    surveillanceResultVo.setConfidence(surveillanceResult.getConfidence());
                    surveillanceResultVo.setDirection(surveillanceResult.getDirection());
                    surveillanceResultVo.setLocationLeft(surveillanceResult.getLocationLeft());
                    surveillanceResultVo.setLocationTop(surveillanceResult.getLocationTop());
                    surveillanceResultVo.setLocationRight(surveillanceResult.getLocationRight());
                    surveillanceResultVo.setLocationBottom(surveillanceResult.getLocationBottom());
                    surveillanceResultVo.setCarColor(surveillanceResult.getCarColor());
                    surveillanceResultVo.setImageUrl(surveillanceResult.getImageUrl());
                    surveillanceResultVo.setResultTime(surveillanceResult.getResultTime());
                    surveillanceResultVo.setFrame_index(surveillanceResult.getFrame_index());
                    surveillanceResultVo.setVehicleKind(surveillanceResult.getVehicleKind());
                    surveillanceResultVo.setVehicleBrand(surveillanceResult.getVehicleBrand());
                    surveillanceResultVo.setVehicleSeries(surveillanceResult.getVehicleSeries());
                    surveillanceResultVo.setVehicleStyle(surveillanceResult.getVehicleStyle());
                    surveillanceResultVo.setTag(surveillanceResult.getTag());
                    surveillanceResultVo.setPaper(surveillanceResult.getPaper());
                    surveillanceResultVo.setDrop(surveillanceResult.getDrop());
                    surveillanceResultVo.setSun(surveillanceResult.getSun());
                    surveillanceResultVo.setLocation(surveillanceResult.getLocation());
                    fillFeatureInfo(surveillanceResultVo, list2);
                    list.add(surveillanceResultVo);
                }
            }

			Pager p = new Pager(total, list);
			return p;
		}else{
			StringBuilder sb = new StringBuilder("from SurveillanceResult s ");
            Long total = count("select count(*) " + sb.toString(), null);
            List list1 = super.find(sb.toString(), null, page, rows);
            List list = new ArrayList();
            SurveillanceResult surveillanceResult = new SurveillanceResult();
            if (list1 != null && list1.size() > 0) {
                for (int i = 0; i < list1.size(); i++) {
                    SurveillanceResultVo surveillanceResultVo = new SurveillanceResultVo();
                    surveillanceResult = (SurveillanceResult)list1.get(i);
                    //获取SerialNumber
                    long serialNumber = surveillanceResult.getSerialNumber();
                    StringBuilder dsb = new StringBuilder(" from VlprFeatures v where v.serialNumber = '"+ serialNumber +"'");
                    List list2 = super.find(dsb.toString(), null, null, null);
                    surveillanceResultVo.setId(surveillanceResult.getId());
                    surveillanceResultVo.setSurveillanceTaskId(surveillanceResult.getSurveillanceTaskId());
                    surveillanceResultVo.setSerialNumber(surveillanceResult.getSerialNumber());
                    surveillanceResultVo.setLicense(surveillanceResult.getLicense());
                    surveillanceResultVo.setLicenseAttribution(surveillanceResult.getLicenseAttribution());
                    surveillanceResultVo.setPlateColor(surveillanceResult.getPlateColor());
                    surveillanceResultVo.setPlateType(surveillanceResult.getPlateType());
                    surveillanceResultVo.setConfidence(surveillanceResult.getConfidence());
                    surveillanceResultVo.setDirection(surveillanceResult.getDirection());
                    surveillanceResultVo.setLocationLeft(surveillanceResult.getLocationLeft());
                    surveillanceResultVo.setLocationTop(surveillanceResult.getLocationTop());
                    surveillanceResultVo.setLocationRight(surveillanceResult.getLocationRight());
                    surveillanceResultVo.setLocationBottom(surveillanceResult.getLocationBottom());
                    surveillanceResultVo.setCarColor(surveillanceResult.getCarColor());
                    surveillanceResultVo.setImageUrl(surveillanceResult.getImageUrl());
                    surveillanceResultVo.setResultTime(surveillanceResult.getResultTime());
                    surveillanceResultVo.setFrame_index(surveillanceResult.getFrame_index());
                    surveillanceResultVo.setVehicleKind(surveillanceResult.getVehicleKind());
                    surveillanceResultVo.setVehicleBrand(surveillanceResult.getVehicleBrand());
                    surveillanceResultVo.setVehicleSeries(surveillanceResult.getVehicleSeries());
                    surveillanceResultVo.setVehicleStyle(surveillanceResult.getVehicleStyle());
                    surveillanceResultVo.setTag(surveillanceResult.getTag());
                    surveillanceResultVo.setPaper(surveillanceResult.getPaper());
                    surveillanceResultVo.setDrop(surveillanceResult.getDrop());
                    surveillanceResultVo.setSun(surveillanceResult.getSun());
                    surveillanceResultVo.setLocation(surveillanceResult.getLocation());
                    fillFeatureInfo(surveillanceResultVo, list2);
                    list.add(surveillanceResultVo);
                }
            }
            Pager p = new Pager(total, list);
            return p;
		}
	}

	@Override
	public void delete(Integer[] surveillanceTaskId){
		List<String> b = new ArrayList<String>();
		for (int i = 0; i < surveillanceTaskId.length; i++) {
			b.add(String.valueOf(surveillanceTaskId[i]));
		}
		String str = b.toString().replace("[", "(").replace("]", ")").replace(" ", "");
		System.out.println(str);
		String sql = "DELETE FROM surveillance_result WHERE  surveillance_task_id in "+ str + "";
		updateOrDelete(sql, null);
	}
}
