package cn.jiuling.vehicleinfosys2.dao;

import java.io.Serializable;
import java.util.List;

import cn.jiuling.vehicleinfosys2.vo.Pager;

public interface BaseDao<T> {
	public Serializable save(T t);

	public Serializable saveAndFlush(T t);

	public void delete(T t);

	public void deleteAll();

	public T find(Serializable id);

	public void update(T t);

	public void saveOrUpdate(T t);

	public List<T> getAll();

	public T findByProperty(String name, Object value);

	public List findByExample(Object example);

	public List findByPropertyList(String name, Object value);

	public List find(String queryString, Object[] values, Integer page, Integer rows);

	/**
	 * 分页模糊查询
	 * 
	 * @param exampleEntity
	 *            一个实体对象，当其中属性存在值时，like查询
	 * @param page
	 *            当前页
	 * @param rows
	 *            页面大小数
	 * @return
	 */
	public Pager list(Object exampleEntity, Integer page, Integer rows);
	
	public Pager list(Object exampleEntity, Integer page, Integer rows, String query);

	public Pager list(Integer page, Integer rows);

	/**
	 * @Title: updateOrDelete
	 * @Description: 支持原生SQL的更新和删除
	 * @param sql
	 *            原生SQL语句
	 * @param params
	 *            参数列表
	 * @ReturnType: void
	 */
	public void updateOrDelete(final String sql, final Object[] params);
}
