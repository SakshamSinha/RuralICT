package app.data.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.Order;
import app.entities.Organization;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.organization.abbreviation)")
	@Override
	public Order findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.abbreviation)")
	@Override
	public List<Order> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.organization.abbreviation)")
	@Override
	public Page<Order> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.abbreviation)")
	@Override
	public List<Order> findAll(Sort sort);

	@PreAuthorize("hasRole('MEMBER'+#order.organization.abbreviation)")
	@Override
	public void delete(@Param("order") Order order);

	/*
	 * Search functions
	 */
     
	public List<Order> findByOrganization(Organization organization);
}
