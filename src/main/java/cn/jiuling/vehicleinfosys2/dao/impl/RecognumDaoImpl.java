package cn.jiuling.vehicleinfosys2.dao.impl;

import cn.jiuling.vehicleinfosys2.dao.RecognumDao;
import cn.jiuling.vehicleinfosys2.model.VlprRecognum;
import cn.jiuling.vehicleinfosys2.util.DateUtils;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository("recognumDao")
public class RecognumDaoImpl extends BaseDaoImpl<VlprRecognum> implements RecognumDao {

	@Override
	public boolean checkRecognum() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf.format(new Date());
		StringBuilder sb = new StringBuilder("from VlprRecognum vr where vr.recogDate='"+today+"' and vr.recogNum >= vr.recogCnt");
		Long total = count("select count(*) " + sb.toString(), null);
		if(total>0){
			return false;
		}
		return true;
	}
}

