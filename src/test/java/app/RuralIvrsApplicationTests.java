package app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import app.RuralIvrsApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RuralIvrsApplication.class)
@EnableAutoConfiguration(exclude = { org.springframework.security.authentication.AuthenticationCredentialsNotFoundException.class })
@WebAppConfiguration
public class RuralIvrsApplicationTests {

	@Test
	public void contextLoads() {
	}

}
