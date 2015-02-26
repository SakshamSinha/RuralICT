package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

}
