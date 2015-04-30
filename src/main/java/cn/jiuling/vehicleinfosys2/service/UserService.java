package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.User;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface UserService {

	public Boolean validUser(User user, String pwd);

	public User findUser(String loginName);

	/**
	 * 修改密码方法
	 * 
	 * @param user
	 *            用户对象
	 * @param pwd
	 *            原密码
	 * @param newPwd
	 *            新密码
	 * @return 成功true,失败false
	 */
	public Boolean updatePwd(User user, String pwd, String newPwd);

	/**
	 * 取得用户列表
	 * 
	 * @param page
	 *            当前页
	 * @param rows
	 *            页面数据大小
	 * @return 页面对象
	 */
	public Pager list(Integer page, Integer rows);

	/**
	 * 添加新用户
	 * 
	 * @param user
	 */
	public void add(User user);

	/**
	 * 修改用户
	 * 
	 * @param user
	 */
	public void update(User user);

	/**
	 * 删除指定id的用户
	 * 
	 * @param ids
	 * @param user TODO
	 */
	public void del(String ids, User user);
	
	/**
	 * @Title: findById
	 * @Description: 通过id查询User
	 * @param id userId
	 * @return User对象
	 * @ReturnType: User
	 */
	public User findById(Integer id);

}
