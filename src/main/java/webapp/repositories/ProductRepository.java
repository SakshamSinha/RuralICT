package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	/*
	 * Default functions
	 */


	/*
	 * Search functions
	 */

}
