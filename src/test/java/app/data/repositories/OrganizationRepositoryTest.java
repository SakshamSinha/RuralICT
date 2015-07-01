package app.data.repositories;

import static app.matcher.BeanMatcher.has;
import static app.matcher.BeanPropertyMatcher.property;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.contains;
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
import app.entities.Organization;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
	@RunWith(SpringJUnit4ClassRunner.class)
	@SpringApplicationConfiguration(classes = RuralIvrsApplicationTests.class)
	@DirtiesContext
	@WebAppConfiguration
	@Transactional
public class OrganizationRepositoryTest {
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Test
	@Rollback
	public void getOrganizationByAbbreviation(){
		Organization element = organizationRepository.findByAbbreviation("iitb");
		assertThat(element,
			has(
                        property("organizationId", is(1)),
                        property("name", is("IITBombay")),
                        property("address",is("new address"))
                ));
	}
	
	@Test
	@Rollback
	public void getOrganizationByIVRS(){
		Organization element = organizationRepository.findByIvrNumber("1233");
		assertThat(element,
			has(
                        property("organizationId", is(2)),
                        property("name", is("Testing")),
                        property("address",is("add"))
                ));
	}
	
	@Test
	@Rollback
	public void getAllOrganizationListSortedByName(){
		List<Organization> list = organizationRepository.findAllByOrderByNameAsc();
		assertThat(list.size(),is(3));
		assertThat(list,contains(
			has(
					property("organizationId", is(1)),
                    property("name", is("IITBombay")),
                    property("address",is("new address"))
            ),
            has(
            		property("organizationId", is(3)),
                    property("name", is("Testging")),
                    property("address",is("dsf"))
            ),
            has(
            		property("organizationId", is(2)),
                    property("name", is("Testing")),
                    property("address",is("add"))
            )));
	}
	
	@Test
	@Rollback
	public void updateParentOrganization(){
		Organization organization = organizationRepository.findOne(3);
		Organization newParentOrganization = organizationRepository.findOne(2);
		organization.getParentOrganization().removeSubOrganization(organization);
		newParentOrganization.addSubOrganization(organization);
		assertThat(organization,
				has(
						property("organizationId", is(3)),
	                    property("name", is("Testging")),
                        property("parentOrganization.organizationId", is(2)),
                        property("address",is("dsf"))
                ));
	}

}
