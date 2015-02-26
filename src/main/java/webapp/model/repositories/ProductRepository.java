package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
