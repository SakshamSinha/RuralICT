package webapp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import webapp.RuralIvrsApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RuralIvrsApplication.class)
@WebAppConfiguration
public class RuralIvrsApplicationTests {

	@Test
	public void contextLoads() {
	}

}
