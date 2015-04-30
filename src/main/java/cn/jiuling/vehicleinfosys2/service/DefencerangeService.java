package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.Defencerange;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface DefencerangeService {

	public void save(User user, Defencerange defenceRange);

	public Pager list(User user, Integer page, Integer rows);

	public void del(Integer id);

}
