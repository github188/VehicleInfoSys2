package cn.jiuling.vehicleinfosys2.service;

import cn.jiuling.vehicleinfosys2.model.Serverinfo;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface ServerService {

	public Pager list(Integer page, Integer rows, String sortName, String order);

	/**
	 * 保存或者更新
	 * 
	 * @param s
	 */
	public void saveOrUpdate(Serverinfo s);

	/**
	 * 取得服务器状态并且向从服务器广播消息
	 * 
	 * @param page
	 * @param rows
	 * @param sortName
	 *            TODO
	 * @param order
	 *            TODO
	 * @return
	 */
	public Pager listAndPublish(Integer page, Integer rows, String sortName, String order);
	
	/**
	 * 移除无响应服务器
	 * 
	 * @param c
	 */
	public void remove(Serverinfo s);
}
