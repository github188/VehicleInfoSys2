package cn.jiuling.vehicleinfosys2.dao;

import java.math.BigInteger;

import cn.jiuling.vehicleinfosys2.model.Datasource;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface ResourceDao extends BaseDao<Datasource> {
	public Pager listByIds(Integer[] ids, Integer page, Integer rows);

	public Pager list(Integer[] ids, String location, Integer page, Integer rows);
	
	public void delete(Datasource d, Boolean isAllDelete);
	
	public String findBigPicFromTheLast(Integer id);

	public Pager findByParentId(Integer parentId, Integer page, Integer rows);

	public Pager fromRemoteDB(Integer page, Integer rows);
	
	public BigInteger getLastInsertId();
}
