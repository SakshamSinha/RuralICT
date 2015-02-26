package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.ProductType;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {

}
