package cn.jiuling.vehicleinfosys2.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.jiuling.vehicleinfosys2.dao.VlprTaskFailLogDao;
import cn.jiuling.vehicleinfosys2.service.FaillogService;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@Service("faillogService")
public class FaillogServiceImpl implements FaillogService {

	@Resource
	private VlprTaskFailLogDao vlprTaskFailLogDao;

	@Override
	public Pager findFaillogTask(Integer id, Integer page, Integer rows) {
		return vlprTaskFailLogDao.getFailLogPager(id, page, rows);
	}

}
