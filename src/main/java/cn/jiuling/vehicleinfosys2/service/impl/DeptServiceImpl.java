package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.DeptDao;
import cn.jiuling.vehicleinfosys2.exception.ServiceException;
import cn.jiuling.vehicleinfosys2.model.Dept;
import cn.jiuling.vehicleinfosys2.service.DeptService;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.Tree;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
	@Resource
	private DeptDao deptDao;

	@Override
	public Dept findDeptById(Integer id) {
		return deptDao.find(id);
	}

	@Override
	public Pager findDeptChildren(Dept c, Integer page, Integer rows) {
		return deptDao.findDeptChildren(c.getId(), page, rows);
	}

	@Override
	public void updateDept(Dept dept) {
		Dept d = deptDao.find(dept.getId());
		Dept d2 = deptDao.findByProperty("name", dept.getName());
		if (null != d2 && d2.getId().intValue() != d.getId().intValue()) {
			throw new ServiceException("单位名称已存在");
		}
		d.setDsc(dept.getDsc());
		d.setName(dept.getName());
		deptDao.update(d);
		deptDao.updateChildDept(dept.getId(), dept.getName());
	}

	@Override
	public void save(Dept dept) {
		dept.setIsEnable((short) 1);
		Integer parentId = dept.getParentId() == null ? 0 : dept.getParentId();
		dept.setParentId(parentId);
		Dept d2 = deptDao.findByProperty("name", dept.getName());
		if (null != d2) {
			throw new ServiceException("单位名称已存在");
		}
		deptDao.save(dept);
		if (parentId > 0) {
			// 设置了父元素名称
			Dept dto = findDeptById(parentId);
			dept.setParentName(dto.getName());
			dept.setAncestorId(dto.getAncestorId() + dept.getId() + ",");
		} else {
			dept.setAncestorId("," + dept.getId() + ",");
		}
		deptDao.save(dept);
	}

	@Override
	public List<Dept> getAll() {
		return deptDao.getAll();
	}

	@Override
	public Dept findByProperty(String name, Object value) {
		return deptDao.findByProperty(name, value);
	}

	@Override
	public List<Tree> getDeptTree(Integer deptId) {
		List<Dept> allList = null;
		List<Dept> deptList = new ArrayList<Dept>();
		List<Tree> deptTree = null;
		List<Tree> curSblingsList = new ArrayList<Tree>();
		List<Tree> nextSblingsList = new ArrayList<Tree>();
		Tree oneNode = null;
		Dept oneDept = null;
		int i = 0;

		/*allList = deptDao.getAll();*/
		allList = deptDao.findByPropertyList("isEnable", Short.valueOf("1"));
		if (deptId != null) {
			oneDept = deptDao.find(deptId);
			if (oneDept != null) {
				deptList.add(oneDept);
			}
		} else {
			// deptList = deptDao.findByPropertyList("parentId", deptId);
			deptList = getDeptChildrenList(null, allList);
		}

		// System.out.println("deptList size:" + deptList.size() +
		// "all list size:" + allList.size());

		// 初始化
		if (deptList != null && deptList.size() > 0) {
			deptTree = new ArrayList<Tree>();
			for (; i < deptList.size(); i++) {
				oneDept = deptList.get(i);
				oneNode = new Tree(oneDept.getId(), oneDept.getName(), oneDept.getAncestorId());
				deptTree.add(oneNode);
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
				// deptList = deptDao.findByPropertyList("parentId",
				// oneNode.getId());
				deptList = getDeptChildrenList(oneNode.getId(), allList);
				if (deptList != null && deptList.size() > 0) {
					oneNode.setChildren(curChildren);
					for (int j = 0; j < deptList.size(); j++) {
						oneDept = deptList.get(j);
						oneNode = new Tree(oneDept.getId(), oneDept.getName(), oneDept.getAncestorId());
						curChildren.add(oneNode);
						nextSblingsList.add(oneNode);
					}
				}
			}
		}
		return deptTree;
	}

	private List<Dept> getDeptChildrenList(Integer deptId, List<Dept> deptList) {
		List<Dept> childrenList = new ArrayList();
		Dept oneDept = null;

		if (deptList == null || deptList.size() < 1) {
			return null;
		}

		for (int i = 0; i < deptList.size(); i++) {
			oneDept = deptList.get(i);
			if (deptId == null) {
				if (oneDept.getParentId().intValue() == 0) {
					childrenList.add(oneDept);
				}
			} else {
				if (oneDept.getParentId().intValue() == deptId.intValue()
				/*&& oneDept.getId().intValue() != deptId.intValue()*/) {
					childrenList.add(oneDept);
				}
			}
		}
		return childrenList;
	}

	@Override
	public List deptTree(Integer id) {
		List list = deptDao.getDeptTree(id);

		return list;
	}

	public void enableOrDisable(Integer id, Short enableOrDisable) {
		Short ENABLE = 1;
		Short DISABLE = 0;
		Dept d = deptDao.find(id);
		if (enableOrDisable.intValue() == ENABLE.intValue()) {
			d.setIsEnable(ENABLE);
			deptDao.update(d);
		} else if (enableOrDisable.intValue() == DISABLE.intValue()) {
			d.setIsEnable(DISABLE);
			deptDao.disableChildren(d.getId());
		} else {
			throw new ServiceException("停用启用参数错误");
		}
	}
}
