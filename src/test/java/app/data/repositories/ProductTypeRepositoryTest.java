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
import app.entities.ProductType;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
	@RunWith(SpringJUnit4ClassRunner.class)
	@SpringApplicationConfiguration(classes = RuralIvrsApplicationTests.class)
	@DirtiesContext
	@WebAppConfiguration
	@Transactional
public class ProductTypeRepositoryTest {
	@Autowired
	ProductTypeRepository productTypeRepository;
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getAllProductListSortedByName(){
		List<ProductType> list = productTypeRepository.findAllByOrderByNameAsc();
		assertThat(list.size(),is(2));
		assertThat(list,contains(
				has(
                		property("productTypeId", is(2)),
                        property("name", is("Testing"))
                ),
				has(
                        property("productTypeId", is(1)),
                        property("name", is("Vegetables"))
                )
                ));
	}

}
