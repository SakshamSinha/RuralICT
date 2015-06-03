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
import app.entities.Group;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
	@RunWith(SpringJUnit4ClassRunner.class)
	@SpringApplicationConfiguration(classes = RuralIvrsApplicationTests.class)
	@DirtiesContext
	@WebAppConfiguration
	@Transactional
public class GroupRepositoryTest {
	
	@Autowired
	GroupRepository groupRepository;
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getAllGroupListSortedByName(){
		List<Group> list = groupRepository.findAllByOrderByNameAsc();
		assertThat(list.size(),is(3));
		assertThat(list,contains(
				has(
                        property("groupId", is(2)),
                        property("organization.organizationId", is(1)),
                        property("parentGroup.groupId", is(1)),
                        property("name", is("Newgroup"))
                ),
                has(
                		property("groupId", is(1)),
                        property("organization.organizationId", is(1)),
                        //property("parentGroup.groupId", is((Integer)null)),
                        property("name", is("Parent Group"))
                ),
                has(
                		property("groupId", is(3)),
                        property("organization.organizationId", is(1)),
                        property("parentGroup.groupId", is(1)),
                        property("name", is("Random"))
                )));
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void updateParentGroup(){
		Group group = groupRepository.findOne(3);
		Group newParentGroup = groupRepository.findOne(2);
		group.getParentGroup().removeSubGroup(group);
		newParentGroup.addSubGroup(group);
		assertThat(group,
				has(
                        property("groupId", is(3)),
                        property("organization.organizationId", is(1)),
                        property("parentGroup.groupId", is(2)),
                        property("name", is("Random"))
                ));
		
	}

}
