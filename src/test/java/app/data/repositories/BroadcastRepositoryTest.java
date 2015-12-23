package app.data.repositories;

import static app.matcher.BeanMatcher.has;
import static app.matcher.BeanPropertyMatcher.property;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Sort;
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
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.broadcast.Broadcast;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
	@RunWith(SpringJUnit4ClassRunner.class)
	@SpringApplicationConfiguration(classes = RuralIvrsApplicationTests.class)
	@DirtiesContext
	@WebAppConfiguration
	@Transactional
public class BroadcastRepositoryTest {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	BroadcastRepository broadcastRepository;
	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	GroupRepository groupRepository;
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getByOrganizationAndFormat(){
		List<Broadcast> list = broadcastRepository.findByOrganizationAndFormat(organizationRepository.findOne(1),"voice");
		assertThat(list.size(),is(2));
		assertThat(list,contains(
				 has(
	                        property("broadcastId", is(2)),
	                        property("format", is("voice")),
	                        property("voice.voiceId", is(1))
	                ),
	             has(
	                        property("broadcastId", is(4)),
	                        property("format", is("voice")),
	                        property("voice.voiceId", is(2))
	                )));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getByGroupAndFormat(){
		List<Broadcast> list = broadcastRepository.findByGroupAndFormat(groupRepository.findOne(2),"text");
		assertThat(list.size(),is(1));
		assertThat(list,contains(
				 has(
	                        property("broadcastId", is(3)),
	                        property("format", is("text")),
	                        property("group.groupId", is(2))
	                )));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getTopBroadcast() {
		List<GroupMembership> groupMembershipList = userRepository.findOne(2).getGroupMemberships();
		List<Group> groupList = new ArrayList<Group>();
		for(GroupMembership groupMembership: groupMembershipList) {
			groupList.add(groupMembership.getGroup());
		}
		
		Broadcast list = broadcastRepository.findTopByGroupInAndOrganizationAndFormat(groupList, organizationRepository.findOne(1), "voice", (new Sort(Sort.Direction.DESC, "broadcastedTime")));
		/*TODO */
//		assertThat(list.size(),is(1));
//		assertThat(list,contains(
//				 has(
//	                        property("broadcastId", is(4)),
//	                        property("format", is("voice")),
//	                        property("voice.voiceId", is(2))
//	                )));
	}
}
