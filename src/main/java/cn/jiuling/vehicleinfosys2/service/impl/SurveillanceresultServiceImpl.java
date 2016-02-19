package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.SurveillanceresultDao;
import cn.jiuling.vehicleinfosys2.service.SurveillanceresultService;
import org.springframework.stereotype.Service;
import cn.jiuling.vehicleinfosys2.vo.Pager;

import javax.annotation.Resource;

@Service("surveillanceresultService")
public class SurveillanceresultServiceImpl implements SurveillanceresultService {

	@Resource
	private SurveillanceresultDao surveillanceresultDao;

	@Override
	public Pager querylist(Integer surveillanceTaskId, Integer page, Integer rows){
		return surveillanceresultDao.querylist(surveillanceTaskId, page, rows);
	}
}
