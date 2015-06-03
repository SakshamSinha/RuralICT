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
import app.entities.Product;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
	  TransactionalTestExecutionListener.class})
	@RunWith(SpringJUnit4ClassRunner.class)
	@SpringApplicationConfiguration(classes = RuralIvrsApplicationTests.class)
	@DirtiesContext
	@WebAppConfiguration
	@Transactional
public class ProductRepositoryTest {
	@Autowired
	ProductRepository productRepository;
	
	@SuppressWarnings("unchecked")
	@Test
	@Rollback
	public void getAllProductListSortedByName(){
		List<Product> list = productRepository.findAllByOrderByNameAsc();
		assertThat(list.size(),is(7));
		assertThat(list,contains(
				has(
                		property("productId", is(15)),
                        property("name", is("Beans"))
                ),
				has(
                        property("productId", is(16)),
                        property("name", is("Beet Root"))
                ),
                has(
                		property("productId", is(17)),
                        property("name", is("Bittergourd"))
                ),
                has(
                		property("productId", is(5)),
                        property("name", is("Cabbage"))
                ),
                has(
                		property("productId", is(6)),
                        property("name", is("Onion"))
                ),
                has(
                		property("productId", is(19)),
                        property("name", is("Potato"))
                ),
                has(
                		property("productId", is(4)),
                        property("name", is("Tomato"))
                )));
	}

}
