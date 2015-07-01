package app;

import static app.matcher.BeanMatcher.has;
import static app.matcher.BeanPropertyMatcher.property;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import app.data.repositories.UserPhoneNumberRepository;
import app.data.repositories.UserRepository;
import app.entities.User;
import app.entities.UserPhoneNumber;


@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RuralIvrsApplication.class)
@DirtiesContext
@WebAppConfiguration
@Transactional
public class UserPhoneNumberRepositoryTest {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserPhoneNumberRepository userPhoneNumberRepository;

	@Test
	@Rollback
	public void getUserPhoneNumberByUserAndPrimaryTrueTest(){
		User user = userRepository.findOne(3);
		UserPhoneNumber userPhoneNumber = userPhoneNumberRepository.findByUserAndPrimaryTrue(user);
		assertThat(userPhoneNumber,
				has(
                        property("phoneNumber", is("919876543210")),
                        property("primary", is(true))
                 ));
	 
    
    }
    
	@Test
	@Rollback
	public void getUserPhoneNumberListByUserTest(){
		User user = userRepository.findOne(2);
		List<UserPhoneNumber> userPhoneNumber = userPhoneNumberRepository.findByUser(user);
		assertThat(userPhoneNumber,contains(
				has(
                        property("phoneNumber", is("919922440560"))
                 )));
	 
    
    }
    
}
