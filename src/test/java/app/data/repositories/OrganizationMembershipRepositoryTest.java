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
import app.entities.OrganizationMembership;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
	@RunWith(SpringJUnit4ClassRunner.class)
	@SpringApplicationConfiguration(classes = RuralIvrsApplicationTests.class)
	@DirtiesContext
	@WebAppConfiguration
	@Transactional
public class OrganizationMembershipRepositoryTest {
	@Autowired
	OrganizationMembershipRepository organizationMembershipRepository;
	@Autowired
	OrganizationRepository organizationRepository;
	@Autowired
	UserRepository userRepository;
	
	@Test
	@Rollback
	public void getUserOrganizationMembership(){
		OrganizationMembership element = organizationMembershipRepository.findByUserAndOrganization(userRepository.findOne(3), organizationRepository.findOne(1));
		assertThat(element,
				has(
	                        property("organizationMembershipId", is(3)),
	                        property("organization.organizationId", is(1)),
	                        property("user.userId",is(3))
	                ));
	}
	
	@Test
	@Rollback
    public void getByOrganization(){
    	List<OrganizationMembership> list = organizationMembershipRepository.findByOrganization(organizationRepository.findOne(1));
    	assertThat(list.size(),is(3));
		assertThat(list,contains(
			has(
					property("organizationMembershipId", is(1)),
                    property("organization.organizationId", is(1)),
                    property("user.userId",is(1))
            ),
            has(
            		property("organizationMembershipId", is(2)),
                    property("organization.organizationId", is(1)),
                    property("user.userId",is(2))
            ),
            has(
            		property("organizationMembershipId", is(3)),
                    property("organization.organizationId", is(1)),
                    property("user.userId",is(3))
            )));
    }
	
	


}
