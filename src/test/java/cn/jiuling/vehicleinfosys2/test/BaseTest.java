package cn.jiuling.vehicleinfosys2.test;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-*.xml", "classpath:springmvc-servlet.xml" })
public abstract class BaseTest extends MyAbstractTransactionalJUnit4SpringContextTests {
	protected final Logger log = Logger.getLogger(this.getClass());
	protected static MockHttpServletRequest request;
	protected static MockHttpServletResponse response;

	@BeforeClass
	public static void before() {
		request = new MockHttpServletRequest();
		request.setCharacterEncoding("UTF-8");
		response = new MockHttpServletResponse();
	}

}
