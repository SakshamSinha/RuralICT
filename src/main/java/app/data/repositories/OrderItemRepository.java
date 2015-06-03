package app.data.repositories;

import java.security.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.OrderItem;
import app.entities.Organization;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.order.organization.abbreviation)")
	@Override
	public OrderItem findOne(Integer id);
	
	@PostFilter("hasRole('ADMIN'+filterObject.order.organization.abbreviation)")
	@Override
	public List<OrderItem> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.order.organization.abbreviation)")
	@Override
	public Page<OrderItem> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.order.organization.abbreviation)")
	@Override
	public List<OrderItem> findAll(Sort sort);

	@PreAuthorize("hasRole('MEMBER'+#item.order.organization.abbreviation)")
	@Override
	public <S extends OrderItem> S save(@Param("item") S item);

	@PreAuthorize("hasRole('MEMBER'+#item.order.organization.abbreviation)")
	@Override
	public void delete(@Param("item") OrderItem item);

	/*
	 * Search functions
	 */
	
	public List<OrderItem> findByOrder_OrganizationAndProduct_NameAndOrder_Message_TimeBetween(Organization organization, String name, Timestamp from, Timestamp to);
	
	public List<OrderItem> findByOrder_OrganizationAndOrder_Message_Group_NameAndOrder_Message_TimeBetween(Organization organization, String name, Timestamp from, Timestamp to);
}