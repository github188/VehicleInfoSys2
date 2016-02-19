package cn.jiuling.vehicleinfosys2.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.jiuling.vehicleinfosys2.dao.UserDao;
import cn.jiuling.vehicleinfosys2.exception.ServiceException;
import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.service.UserService;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.UserInfo;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;

	@Override
	public Boolean validUser(User user, String pwd) {
		return null != user && user.getPwd().equals(pwd);
	}

	public User findUser(String loginName) {
		return userDao.findByProperty("loginName", loginName);
	}

    @Override
    public User findUser(Integer userId) {
        return userDao.find(userId);
    }

	@Override
	public Boolean updatePwd(User user, String pwd, String newPwd) {
		Boolean b = false;
		if (user.getPwd().equals(pwd)) {
			User u = userDao.find(user.getId());
			u.setPwd(newPwd);
			userDao.update(u);
			b = true;
		}
		return b;
	}

	@Override
	public Pager list(Integer page, Integer rows) {
		return userDao.listUser(page, rows);
	}

	@Override
	public void add(User user) {
		userDao.save(user);

	}

	@Override
	public void del(String ids, User user) {
		String userId = user.getId().toString();
		if (ids.equals(userId)
				|| ids.contains("," + userId + ",")
				|| ids.startsWith(userId + ",")
				|| ids.endsWith("," + userId)) {
			throw new ServiceException("不能删除自己！");
		}
		
		if(ids.equals("1")
				|| ids.contains(",1,")
				|| ids.startsWith("1,")
				|| ids.endsWith(",1")) {
			throw new ServiceException("不能删除admin！");
		}
		userDao.delUser(ids);
	}

	@Override
	public void update(User user) {
		User oldUser = userDao.find(user.getId());
		org.springframework.beans.BeanUtils.copyProperties(user, oldUser, new String[] { "id", "pwd", "loginName" });
		userDao.save(oldUser);
	}

	@Override
	public User findById(Integer id) {
		return userDao.find(id);
	}
	
	@Override
	public UserInfo getUserInfo(String loginName) {
		return userDao.getUserInfo(loginName);
	}
}
