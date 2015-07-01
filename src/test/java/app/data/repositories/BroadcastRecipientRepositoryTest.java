package app.data.repositories;

import static app.matcher.BeanMatcher.has;
import static app.matcher.BeanPropertyMatcher.property;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

import app.RuralIvrsApplicationTests;
import app.entities.BroadcastRecipient;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
	@RunWith(SpringJUnit4ClassRunner.class)
	@SpringApplicationConfiguration(classes = RuralIvrsApplicationTests.class)
	@DirtiesContext
	@WebAppConfiguration
	@Transactional
public class BroadcastRecipientRepositoryTest {
	@Autowired
	BroadcastRecipientRepository broadcastRecipientRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	BroadcastRepository broadcastRepository;
	
	@Test
	@Rollback
	public void getTopBroadcast(){
		 BroadcastRecipient element = broadcastRecipientRepository.findTopByUserAndBroadcast_OrganizationOrderByBroadcast_BroadcastedTimeDesc(userRepository.findOne(2),organizationRepository.findOne(1));
		 assertThat(element,
				 has(
	                        property("broadcastRecipientId", is(6)),
	                        property("broadcast.broadcastId", is(4)),
	                        property("user.userId", is(2))
	                ));
	}
	
	@Test
	@Rollback
	public void getBroadcastRecipientByUserAndBroadcast(){
		BroadcastRecipient element = broadcastRecipientRepository.findByUserAndBroadcast(userRepository.findOne(1), broadcastRepository.findOne(2));
		assertThat(element,
				 has(
	                        property("broadcastRecipientId", is(5)),
	                        property("broadcast.broadcastId", is(2)),
	                        property("user.userId", is(1))
	                ));
	}
	

}
