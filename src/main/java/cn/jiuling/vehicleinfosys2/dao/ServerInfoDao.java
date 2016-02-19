package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.Server_Info;
import cn.jiuling.vehicleinfosys2.model.Serverinfo;
import cn.jiuling.vehicleinfosys2.vo.Pager;

import java.util.List;

public interface ServerInfoDao extends BaseDao<Server_Info> {

	List findByIds(String ids);
}
