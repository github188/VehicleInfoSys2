package cn.jiuling.vehicleinfosys2.service.impl;

import cn.jiuling.vehicleinfosys2.dao.ActionprivilegeDao;
import cn.jiuling.vehicleinfosys2.dao.RoleActionprivilegeDao;
import cn.jiuling.vehicleinfosys2.dao.RoleDao;
import cn.jiuling.vehicleinfosys2.dao.SysresourceDao;
import cn.jiuling.vehicleinfosys2.exception.ServiceException;
import cn.jiuling.vehicleinfosys2.model.Actionprivilege;
import cn.jiuling.vehicleinfosys2.model.Role;
import cn.jiuling.vehicleinfosys2.model.RoleActionprivilege;
import cn.jiuling.vehicleinfosys2.model.Sysresource;
import cn.jiuling.vehicleinfosys2.service.RoleService;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.Tree;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {
	private Logger log = Logger.getLogger(RoleServiceImpl.class);
	@Resource
	private RoleDao roleDao;
	@Resource
	private SysresourceDao sysresourceDao;
	@Resource
	private ActionprivilegeDao actionprivilegeDao;
	@Resource
	private RoleActionprivilegeDao roleActionprivilegeDao;

	@Override
	public Pager list(Object c, Integer page, Integer rows) {
		return roleDao.list(c, page, rows);
	}

	@Override
	public Role add(Role r) {
		Role role = roleDao.findByProperty("name", r.getName());
		if (null != role) {
			throw new ServiceException("角色名重复");
		}
		Integer id = (Integer) roleDao.save(r);
		r.setId(id);
		return r;
	}

	@Override
	public void del(Integer id) {
		Role role = roleDao.find(id);
		roleDao.delete(role);
	}

	@Override
	public void update(Role r) {
		// 不能存在同名的role
		List list = roleDao.findByPropertyList("name", r.getName());
		if (list.size() > 0) {
			Role rr = (Role) list.get(0);
			if (rr.getId().intValue() != r.getId().intValue()) {
				throw new ServiceException("角色名重复");
			}
		}
		Role role = roleDao.find(r.getId());
		role.setName(r.getName());
		role.setRemark(r.getRemark());
		role.setStatus(r.getStatus());
		roleDao.update(role);
	}

	@Override
	public List<Tree> findRolePrivilegesTree(Integer id) {
		// 获取所有的资源；
		// 对每一个资源，查找资源权限表，获取对应的权限， 为每一个权限建立一个节点
		// 对给定资源的每一个权限，查找是否分配给了角色;如果是，则该节点标记为选中状态
		List<Sysresource> resourceList = sysresourceDao.getAll();
		Sysresource oneRes = null;
		List<Tree> rolePrivilegeTree = null;
		Tree resourceNode = null;
		Tree prvNode = null;

		if (resourceList != null && resourceList.size() > 0) {
			rolePrivilegeTree = new ArrayList<Tree>();
			for (int i = 0; i < resourceList.size(); i++) {
				oneRes = resourceList.get(i);
				List<Actionprivilege> actionPrvList = actionprivilegeDao.findByPropertyList("resourceId", oneRes.getId());
				if (actionPrvList != null && actionPrvList.size() > 0) {
					// 添加资源节点
					resourceNode = new Tree();
					resourceNode.setId(oneRes.getId());
					resourceNode.setText(oneRes.getName());
					resourceNode.setChildren(new ArrayList<Tree>());
					rolePrivilegeTree.add(resourceNode);
					for (int j = 0; j < actionPrvList.size(); j++) {
						// 添加资源下的操作权限节点
						Actionprivilege onePrv = actionPrvList.get(j);
						RoleActionprivilege prv = new RoleActionprivilege(id, onePrv.getId());
						prvNode = new Tree();
						prvNode.setId(onePrv.getId());
						prvNode.setText(onePrv.getActionName());
						resourceNode.getChildren().add(prvNode);
						List<RoleActionprivilege> prvList = roleActionprivilegeDao.findByExample(prv);
						// 设置操作权限是否被分配
						if (prvList != null && prvList.size() > 0) {
							prvNode.setChecked(true);
						}

					}
				}
			}
		}

		return rolePrivilegeTree;
	}

	public void updateRolePrivileges(Integer roleId, Integer[] prvList) {
		RoleActionprivilege onePrv = null;
		if (roleId != null && prvList != null && prvList.length > 0) {
			roleActionprivilegeDao.deleteAll();
			for (int i = 0; i < prvList.length; i++) {
				onePrv = new RoleActionprivilege();
				onePrv.setPrivilegeId(prvList[i]);
				onePrv.setRoleId(roleId);
				log.info("roleId:" + roleId + "; prvId:" + prvList[i]);
				roleActionprivilegeDao.save(onePrv);
			}
		}

	}

    public void delete(Integer id) {
        roleDao.delete(id);
    }
}
