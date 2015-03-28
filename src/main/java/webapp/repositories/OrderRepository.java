package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
