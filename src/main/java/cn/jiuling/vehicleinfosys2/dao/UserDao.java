package cn.jiuling.vehicleinfosys2.dao;

import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.vo.Pager;
import cn.jiuling.vehicleinfosys2.vo.UserInfo;

public interface UserDao extends BaseDao<User> {
	/**
	 * 返回user页面对象
	 * 
	 * @param page
	 *            当前面
	 * @param rows
	 *            页面大小
	 * @return 页面对象
	 */
	public Pager listUser(Integer page, Integer rows);

	/**
	 * 根据id删除用户
	 * 
	 * @param ids
	 */
	public void delUser(String ids);
	
	/**
	 * 查询用户信息
	 * @param loginName
	 * @return
	 */
	public UserInfo getUserInfo(final String loginName);

}
