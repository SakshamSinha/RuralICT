package app;

import static app.matcher.BeanMatcher.has;
import static app.matcher.BeanPropertyMatcher.property;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.sql.Timestamp;
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

import app.data.repositories.BroadcastRepository;
import app.data.repositories.BroadcastScheduleRepository;
import app.entities.BroadcastSchedule;
import app.entities.broadcast.Broadcast;


@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RuralIvrsApplicationTests.class)
@DirtiesContext
@WebAppConfiguration
@Transactional
public class BroadcastScheduleRepositoryTest {
	
	@Autowired
	BroadcastRepository broadcastRepository;
	
	@Autowired
	BroadcastScheduleRepository broadcastScheduleRepository;
	
	
    @SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getBroadcastScheduleListByBroadcastOrderByTimeAscTest(){
		Broadcast broadcast = broadcastRepository.findOne(2);
		List<BroadcastSchedule> broadcastlist = broadcastScheduleRepository.findByBroadcastOrderByTimeAsc(broadcast);
		assertThat(broadcastlist.size(),is(3));
		assertThat(broadcastlist,contains(
				has(
                        property("broadcastScheduleId", is(5)),
                        property("broadcast.broadcastId", is(2))
                        
                ),
                has(
                        property("broadcastScheduleId", is(3)),
                        property("broadcast.broadcastId", is(2))
                   ),
                has(
                        property("broadcastScheduleId", is(7)),
                        property("broadcast.broadcastId", is(2))
                )));
	 }
    
    @SuppressWarnings("unchecked")
	@Test
	@Rollback
    public void getBroadcastScheduleListByByBroadcastAndSendToAllTrueAndTimeGreaterThanOrderByTimeAscTest(){
		Broadcast broadcast = broadcastRepository.findOne(2);
		Timestamp timestamp = Timestamp.valueOf("2015-04-17 17:55:06");
		List<BroadcastSchedule> broadcastlist = broadcastScheduleRepository.findByBroadcastAndSendToAllTrueAndTimeGreaterThanOrderByTimeAsc(broadcast,timestamp);
		assertThat(broadcastlist.size(),is(1));
		assertThat(broadcastlist,contains(
				has(
                        property("broadcastScheduleId", is(7)),
                        property("broadcast.broadcastId", is(2))
                )));
	 }
    
    @SuppressWarnings("unchecked")
	@Test
	@Rollback
    public void getBroadcastScheduleListByByBroadcastAndTimeGreaterThanOrderByTimeAscTest(){
		Broadcast broadcast = broadcastRepository.findOne(2);
		Timestamp timestamp = Timestamp.valueOf("2015-04-17 15:55:06");
		List<BroadcastSchedule> broadcastlist = broadcastScheduleRepository.findByBroadcastAndTimeGreaterThanOrderByTimeAsc(broadcast,timestamp);
		assertThat(broadcastlist.size(),is(2));
		assertThat(broadcastlist,contains(
				has(
                        property("broadcastScheduleId", is(3)),
                        property("broadcast.broadcastId", is(2))
                   ),
                has(
                        property("broadcastScheduleId", is(7)),
                        property("broadcast.broadcastId", is(2))
                )));
	 }
    
    
}
