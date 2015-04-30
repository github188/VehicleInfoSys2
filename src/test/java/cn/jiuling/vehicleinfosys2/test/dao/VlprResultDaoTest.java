package cn.jiuling.vehicleinfosys2.test.dao;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import cn.jiuling.vehicleinfosys2.dao.VlprResultDao;
import cn.jiuling.vehicleinfosys2.test.BaseTest;
import cn.jiuling.vehicleinfosys2.vo.Pager;

public class VlprResultDaoTest extends BaseTest {

	@Resource
	private VlprResultDao vlprResultDao;

	@Test
	public void testDdd() {
		Pager p = vlprResultDao.findByTaskId(null, null, null);
		Assert.assertTrue("查到数据了", p.getTotal() >= 0);
	}
}
