package cn.jiuling.vehicleinfosys2.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.jiuling.vehicleinfosys2.dao.DefencerangeDao;
import cn.jiuling.vehicleinfosys2.model.Defencerange;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.service.DefencerangeService;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@Service("defencerangeService")
public class DefencerangeServiceImpl implements DefencerangeService {

	@Resource
	private DefencerangeDao defencerangeDao;

	@Override
	public void save(User user, Defencerange defenceRange) {
		defenceRange.setUserId(user.getId());
		defencerangeDao.save(defenceRange);
	}

	@Override
	public Pager list(User user, Integer page, Integer rows) {
		return defencerangeDao.listByUser(user, page, rows);
	}

	@Override
	public void del(Integer id) {
		Defencerange d = defencerangeDao.find(id);
		if (null != d) {
			defencerangeDao.delete(d);
		}
	}
}
