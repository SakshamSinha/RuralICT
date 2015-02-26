package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
