package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.Defencerange;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface DefencerangeDao extends BaseDao<Defencerange> {

	public Pager listByUser(User user, Integer page, Integer rows);

}
