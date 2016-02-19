package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.AreaDao;
import cn.jiuling.vehicleinfosys2.model.Area;
import cn.jiuling.vehicleinfosys2.service.AreaService;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.Tree;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("areaService")
public class AreaServiceImpl implements AreaService {
	@Resource
	private AreaDao areaDao;

	@Override
	public Area findByProperty(String name, Object value) {
		return areaDao.findByProperty(name, value);
	}

	@Override
	public List<Area> getAll() {
		return areaDao.getAll();
	}

	@Override
	public Pager list(Area area, Integer page, Integer rows) {
		return areaDao.list(area, page, rows);
	}

	@Override
	public void save(Area area) {
		area.setIsEnable((short) 1);
		Integer parentId = area.getParentId() == null ? 0 : area.getParentId();
		area.setParentId(parentId);
		areaDao.save(area);
		if (parentId > 0) {
			// 设置了父元素名称
			Area dto = findAreaById(parentId);
			area.setParentName(dto.getName());
			area.setAncestorId(dto.getAncestorId() + area.getId() + ",");
		} else {
			area.setAncestorId("," + area.getId() + ",");
		}
		areaDao.save(area);
	}

	@Override
	public void updateArea(Area area) {
		Integer id = area.getId();
		String name = area.getName();
		Area a = areaDao.find(id);
		a.setName(name);
		a.setDsc(area.getDsc());
		a.setAreaCode(area.getAreaCode());
		areaDao.update(a);
		areaDao.updateChildren(id, name);
	}

	@Override
	public Area findAreaById(Integer id) {
		return areaDao.find(id);
	}

	@Override
	public List<Tree> getAreaTree(Integer areaId) {
		List<Area> allList = null;
		List<Area> areaList = new ArrayList<Area>();
		List<Tree> areaTree = null;
		List<Tree> curSblingsList = new ArrayList<Tree>();
		List<Tree> nextSblingsList = new ArrayList<Tree>();
		Tree oneNode = null;
		Area oneArea = null;
		int i = 0;

		// allList = areaDao.getAll();
		allList = areaDao.findByPropertyList("isEnable", Short.valueOf("1"));
		if (areaId != null) {
			oneArea = areaDao.find(areaId);
			if (oneArea != null) {
				areaList.add(oneArea);
			}
		} else {
			areaList = getAreaChildrenList(null, allList);
		}

		// 初始化
		if (areaList != null && areaList.size() > 0) {
			areaTree = new ArrayList<Tree>();
			for (; i < areaList.size(); i++) {
				oneArea = areaList.get(i);
				oneNode = new Tree(oneArea.getId(), oneArea.getName(), oneArea.getAncestorId());
				areaTree.add(oneNode);
				nextSblingsList.add(oneNode);
			}
		}

		// 生成部门树结构
		while (nextSblingsList.size() > 0) {
			curSblingsList = nextSblingsList;
			nextSblingsList = new ArrayList<Tree>();
			for (i = 0; i < curSblingsList.size(); i++) {
				oneNode = curSblingsList.get(i);
				List<Tree> curChildren = new ArrayList<Tree>();
				areaList = getAreaChildrenList(oneNode.getId(), allList);
				if (areaList != null && areaList.size() > 0) {
					oneNode.setChildren(curChildren);
					for (int j = 0; j < areaList.size(); j++) {
						oneArea = areaList.get(j);
						oneNode = new Tree(oneArea.getId(), oneArea.getName(), oneArea.getAncestorId());
						curChildren.add(oneNode);
						nextSblingsList.add(oneNode);
					}
				}
			}
		}
		return areaTree;
	}

	private List<Area> getAreaChildrenList(Integer areaId, List<Area> areaList) {
		List<Area> childrenList = new ArrayList();
		Area oneArea = null;

		if (areaList == null || areaList.size() < 1) {
			return null;
		}

		for (int i = 0; i < areaList.size(); i++) {
			oneArea = areaList.get(i);
			if (areaId == null) {
				if (oneArea.getParentId().intValue() == 0) {
					childrenList.add(oneArea);
				}
			} else {
				if (oneArea.getParentId().intValue() == areaId.intValue()
				/*&& oneArea.getId().intValue() != areaId.intValue()*/) {
					childrenList.add(oneArea);
				}
			}
		}
		return childrenList;
	}

	public Pager findAreaChildren(Area area, Integer page, Integer rows) {
		return areaDao.findAreaChildren(area.getId(), page, rows);
	}

	public String enableOrDisable(Integer id, Short enableOrDisable) {
		Short ENABLE = 1;
		Short DISABLE = 0;
		Area d = areaDao.find(id);
        String str = "";
		if (enableOrDisable.intValue() == ENABLE.intValue()) {
            String ancestorId = d.getAncestorId();
            ancestorId = ancestorId.substring(1, ancestorId.lastIndexOf(","));
            if(ancestorId.length() > 1) {
                ancestorId = ancestorId.substring(0,ancestorId.lastIndexOf(","));
                List<Area> list = areaDao.getAncestor(ancestorId);
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    Area b = list.get(i);
                    if(b.getIsEnable() == 0) {
                        str = "请先启用上级行政区!";
                        return str;
                    }
                }
            }
            d.setIsEnable(ENABLE);
			areaDao.update(d);
		} else if (enableOrDisable.intValue() == DISABLE.intValue()) {
			d.setIsEnable(DISABLE);
			areaDao.disableChildren(d.getId());
		} else {
			throw new RuntimeException("停用启用参数错误");
		}
        return str;
	}

	@Override
	public List areaTree(Integer id) {
		List list = areaDao.getAreaTree(id);
		return list;
	}
}
