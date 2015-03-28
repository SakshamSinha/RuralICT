package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

}
