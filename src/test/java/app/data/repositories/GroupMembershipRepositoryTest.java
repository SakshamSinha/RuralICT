package app.data.repositories;

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

import app.RuralIvrsApplicationTests;
import app.entities.GroupMembership;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
	@RunWith(SpringJUnit4ClassRunner.class)
	@SpringApplicationConfiguration(classes = RuralIvrsApplicationTests.class)
	@DirtiesContext
	@WebAppConfiguration
	@Transactional
public class GroupMembershipRepositoryTest {
	
	@Autowired
	GroupMembershipRepository groupMembershipRepository;
	@Autowired
	GroupRepository groupRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Test
	@Rollback
	public void isUserGroupMembership(){
		GroupMembership element=groupMembershipRepository.findByUserAndGroup(userRepository.findOne(2),groupRepository.findOne(1));
		assertThat(element,
				has(
                        property("groupMembershipId", is(2)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(2))
                ));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getGroupMembershipListByUser(){
		List<GroupMembership> list = groupMembershipRepository.findByUser(userRepository.findOne(2));
		assertThat(list.size(),is(2));
		assertThat(list,contains(
				has(
                        property("groupMembershipId", is(2)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(2))
                ),
                has(
                        property("groupMembershipId", is(3)),
                        property("group.groupId", is(2)),
                        property("user.userId", is(2))
                )));
				
				
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getGroupMembershipListByUserSortedByGroupName(){
		List<GroupMembership> list = groupMembershipRepository.findByUserOrderByGroup_NameAsc(userRepository.findOne(2));
		assertThat(list.size(),is(2));
		assertThat(list,contains(
				has(
                        property("groupMembershipId", is(3)),
                        property("group.groupId", is(2)),
                        property("user.userId", is(2))
                ),
                has(
                        property("groupMembershipId", is(2)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(2))
                )));
	}
	
	@Test
	@Rollback
	public void getGroupMembershipListByGroup(){
		List<GroupMembership> list = groupMembershipRepository.findByGroup(groupRepository.findOne(3));
		assertThat(list.size(), is(1));
		
		assertThat(list,contains(
                has(
                        property("groupMembershipId", is(4)),
                        property("group.groupId", is(3)),
                        property("user.userId", is(3))
                )));
	}
	
	@Test
	@Rollback
	public void getGroupMembershipListByGroupSortedByUserName(){
		List<GroupMembership> list = groupMembershipRepository.findByGroupOrderByUser_NameAsc(groupRepository.findOne(1));
		assertThat(list.size(),is(2));
		assertThat(list,contains(
				has(
                        property("groupMembershipId", is(1)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(1))
                ),
                has(
                        property("groupMembershipId", is(2)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(2))
                )));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback
	//It again reverses the order. Again no stable sort.
	public void getAllGroupMembershipListSortedByUserName(){
		List<GroupMembership> list = groupMembershipRepository.findAllByOrderByUser_NameAsc();
		assertThat(list.size(),is(4));
		assertThat(list,contains(
				has(
                        property("groupMembershipId", is(1)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(1))
                ),
                has(
                        property("groupMembershipId", is(3)),
                        property("group.groupId", is(2)),
                        property("user.userId", is(2))
                ),
                has(
                        property("groupMembershipId", is(2)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(2))
                ),
                has(
                        property("groupMembershipId", is(4)),
                        property("group.groupId", is(3)),
                        property("user.userId", is(3))
                )));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getAllGroupMembershipListSortedByGroupName(){
		List<GroupMembership> sort=groupMembershipRepository.findAllByOrderByGroup_NameAsc();
		
		assertThat(sort.size(), is(4));
		//why the order of second and third has changed. It has to be a stable sort.
		assertThat(sort,contains(
				has(
                        property("groupMembershipId", is(3)),
                        property("group.groupId", is(2)),
                        property("user.userId", is(2))
                ),
                has(
                        property("groupMembershipId", is(1)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(1))
                ),
                has(
                        property("groupMembershipId", is(2)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(2))
                ),
                has(
                        property("groupMembershipId", is(4)),
                        property("group.groupId", is(3)),
                        property("user.userId", is(3))
                )));        
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getGroupMembershipListByUserAndOrganization(){
		List<GroupMembership> list = groupMembershipRepository.findByUserAndGroup_Organization(userRepository.findOne(2),organizationRepository.findOne(1));
		assertThat(list.size(),is(2));
		assertThat(list,contains(
				has(
                        property("groupMembershipId", is(2)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(2))
                ),
                has(
                        property("groupMembershipId", is(3)),
                        property("group.groupId", is(2)),
                        property("user.userId", is(2))
                )));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getGroupsByUserAndOrganizationSorted(){
		List<GroupMembership> list = groupMembershipRepository.findByUserAndGroup_OrganizationOrderByGroup_NameAsc(userRepository.findOne(2),organizationRepository.findOne(1));
		assertThat(list.size(),is(2));
		assertThat(list,contains(
				has(
                        property("groupMembershipId", is(3)),
                        property("group.groupId", is(2)),
                        property("user.userId", is(2))
                ),
                has(
                        property("groupMembershipId", is(2)),
                        property("group.groupId", is(1)),
                        property("user.userId", is(2))
                )));
	}
	
}
