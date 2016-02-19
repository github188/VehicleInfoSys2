package cn.jiuling.vehicleinfosys2.service;


import cn.jiuling.vehicleinfosys2.model.Area;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.Tree;

import java.util.List;

public interface AreaService {
	public Pager list(Area area, Integer page, Integer rows);

	public Area findAreaById(Integer id);

	public void updateArea(Area area);

	public void save(Area area);

	public List<Area> getAll();

	public Area findByProperty(String name, Object value);

	public List<Tree> getAreaTree(Integer areaId);

	public Pager findAreaChildren(Area area, Integer page, Integer rows);

	public String enableOrDisable(Integer id, Short enableOrDisable);
	
	public List areaTree(Integer id);
}
