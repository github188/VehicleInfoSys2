package cn.jiuling.vehicleinfosys2.service.impl;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import cn.jiuling.vehicleinfosys2.dao.ResourceDao;
import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.model.VlprResult;
import cn.jiuling.vehicleinfosys2.service.ResultService;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.ResultQuery;
import cn.jiuling.vehicleinfosys2.vo.ResultVo;

@Service("resultService")
public class ResultServiceImpl implements ResultService {

	@Resource
	private VlprResultDao vlprResultDao;
	private ResourceDao resourceDao;

	@Override
	public ResultVo findById(Long id) {
		ResultVo result = vlprResultDao.findResult(id);
		return result;
	}

	@Override
	public Pager list(ResultQuery rq, Integer page, Integer rows) {
		VlprResult v = new VlprResult();
		try {
			BeanUtils.copyProperties(v, rq);
		} catch (Exception e) {

		}
		return vlprResultDao.list(v, page, rows);
	}

	@Override
	public Pager listByTaskId(Integer taskId, Integer page, Integer rows) {
		return vlprResultDao.findByTaskId(taskId, page, rows);
	}

	@Override
	public Pager query(ResultQuery rq, Integer page, Integer rows) {
		return vlprResultDao.query(rq, page, rows);
	}

}
