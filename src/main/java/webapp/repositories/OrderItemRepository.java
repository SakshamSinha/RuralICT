package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
	/*
	 * Default functions
	 */


	/*
	 * Search functions
	 */

}
