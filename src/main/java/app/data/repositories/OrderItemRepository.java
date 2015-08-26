package app.data.repositories;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.Group;
import app.entities.OrderItem;
import app.entities.Organization;
import app.entities.Product;

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

//	@PostFilter("hasRole('ADMIN'+filterObject.order.organization.abbreviation)")
//	@Override
//	public Page<OrderItem> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.order.organization.abbreviation)")
	@Override
	public List<OrderItem> findAll(Sort sort);

	@PreAuthorize("hasRole('MEMBER'+#orderItem.order.organization.abbreviation)")
	@Override
	public <S extends OrderItem> S save(@Param("orderItem") S item);

	@PreAuthorize("hasRole('MEMBER'+#item.order.organization.abbreviation)")
	@Override
	public void delete(@Param("item") OrderItem item);

	/*
	 * Search functions
	 */	
	public List<OrderItem> findByOrder_OrganizationAndProduct_NameAndOrder_Message_TimeBetween(Organization organization, String name, Timestamp from, Timestamp to);
	
	public List<OrderItem> findByOrder_OrganizationAndOrder_Message_Group_NameAndOrder_Message_TimeBetween(Organization organization, String name, Timestamp from, Timestamp to);
	
	public List<OrderItem> findByOrder_OrderId(@Param("orderId") int orderId);
	
	@RestResource(rel="orderSummaryProducts", path="orderSummaryProducts")
	public List<OrderItem> findByOrder_Organization_AbbreviationAndProduct_NameAndOrder_Message_TimeBetween( @Param("org") String org, @Param("prod") String name, @Param("fromTime")  @DateTimeFormat(pattern = "yyyy-MM-dd") Date from, @Param("toTime") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to);
	
	@RestResource(rel="orderSummaryGroups", path="orderSummaryGroups")
	public List<OrderItem> findByOrder_Organization_AbbreviationAndOrder_Message_Group_NameAndOrder_Message_TimeBetween(@Param("org") String org, @Param("groupName") String name, @Param("fromTime")  @DateTimeFormat(pattern = "yyyy-MM-dd") Date from, @Param("toTime")  @DateTimeFormat(pattern = "yyyy-MM-dd") Date to);
	
	public List<OrderItem> findByOrder_Message_GroupAndOrder_Message_TimeBetween(Group group, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate);
	
	public List<OrderItem> findByProductAndOrder_Message_TimeBetween(Product product, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate);
}