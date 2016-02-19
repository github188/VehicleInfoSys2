package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface FaillogService {

	public Pager findFaillogTask(Integer id, Integer page, Integer rows);

}
