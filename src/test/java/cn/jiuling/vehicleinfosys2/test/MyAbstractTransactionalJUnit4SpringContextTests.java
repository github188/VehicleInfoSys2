package cn.jiuling.vehicleinfosys2.test;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

public abstract class MyAbstractTransactionalJUnit4SpringContextTests extends AbstractTransactionalJUnit4SpringContextTests {
	@Resource(name = "userDS")
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}
}
