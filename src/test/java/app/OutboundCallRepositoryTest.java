package app;

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

import app.data.repositories.BroadcastRecipientRepository;
import app.data.repositories.BroadcastScheduleRepository;
import app.data.repositories.OutboundCallRepository;
import app.entities.BroadcastRecipient;
import app.entities.BroadcastSchedule;
import app.entities.OutboundCall;


@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RuralIvrsApplicationTests.class)
@DirtiesContext
@WebAppConfiguration
@Transactional
public class OutboundCallRepositoryTest {
	
	@Autowired
	BroadcastScheduleRepository broadcastScheduleRepository;
	
	@Autowired
	BroadcastRecipientRepository broadcastRecipientRepository;
	
	@Autowired
	OutboundCallRepository outboundCallRepository;
	
	
    @SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getOutboundCallByBroadcastScheduleAndBroadcastRecipient(){
		BroadcastSchedule broadcastSchedule = broadcastScheduleRepository.findOne(7);
		BroadcastRecipient broadcastRecipient = broadcastRecipientRepository.findOne(1);
		OutboundCall outboundCall = outboundCallRepository.findByBroadcastScheduleAndBroadcastRecipient(broadcastSchedule, broadcastRecipient);
		assertThat(outboundCall,
				has(
                        property("outboundCallId", is(6))
                ));
	 }   
}
