package app;

import static app.matcher.BeanMatcher.has;
import static app.matcher.BeanPropertyMatcher.property;
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

import app.data.repositories.GroupMembershipRepository;
import app.data.repositories.GroupRepository;
import app.entities.Group;
import app.entities.GroupMembership;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
	@RunWith(SpringJUnit4ClassRunner.class)
	@SpringApplicationConfiguration(classes = RuralIvrsApplication.class)
	@DirtiesContext
	@WebAppConfiguration
	@Transactional
public class GroupRepositoryTest {
	
	@Autowired
	GroupMembershipRepository groupMembershipRepository;
	@Autowired
	GroupRepository groupRepository;
	
	
	@Test
	@Rollback
	public void getAllGroupMembershipListSortedByGroupName(){
		List<GroupMembership> sort=groupMembershipRepository.findByGroup(groupRepository.findOne(1));
		
		assertThat(sort.size(), is(2));
		
		assertThat(sort.get(0), 
                has(
                        property("groupMembershipId", is(1)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(1))
                ));
		
		assertThat(sort.get(1),
                has(
                        property("groupMembershipId", is(2)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(2))
                ));
		/*
		assertThat(sort.get(2),
                has(
                        property("groupMembershipId", is(1)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(1))
                ));
                */
        
	}
	
	
	

}
