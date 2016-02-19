package cn.jiuling.vehicleinfosys2.service.impl;

import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.jiuling.vehicleinfosys2.dao.ServerDao;
import cn.jiuling.vehicleinfosys2.model.Serverinfo;
import cn.jiuling.vehicleinfosys2.service.MessageService;
import cn.jiuling.vehicleinfosys2.service.ServerService;
import cn.jiuling.vehicleinfosys2.vo.Pager;

@Service("serverService")
public class ServerServiceImpl implements ServerService {
	@Resource
	private ServerDao serverDao;
	@Resource
	private MessageService messageService;

	@Override
	public Pager list(Integer page, Integer rows, String sortName, String order) {
		return serverDao.list(page, rows, sortName, order);
	}

	@Override
	public void saveOrUpdate(Serverinfo s) {
		Serverinfo oldServer = serverDao.findByProperty("ip", s.getIp());
		if (oldServer != null) {
			org.springframework.beans.BeanUtils.copyProperties(s, oldServer, new String[] { "id" });
		} else {
			oldServer = s;
		}
		// 服务器更新时间以主服务器为准
		oldServer.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		// TODO 给server加一个字段，s是从服务器，把s.updateTime放到新的字段里面slaveTime
		serverDao.saveOrUpdate(oldServer);
	}

	@Override
	public Pager listAndPublish(Integer page, Integer rows, String sortName, String order) {
		messageService.sendMsg("getStatus");
		return list(page, rows, sortName, order);
	}

	@Override
    public void remove(Serverinfo s) {
		Serverinfo _s = serverDao.find(s.getId());
		serverDao.delete(_s);
    }
}
