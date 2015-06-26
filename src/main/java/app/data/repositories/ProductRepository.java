package app.data.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.productType.organization.abbreviation)")
	@Override
	public Product findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.productType.organization.abbreviation)")
	@Override
	public List<Product> findAll();
	//TODO try to overcoe this problem
	//@PostFilter("hasRole('ADMIN'+filterObject.productType.organization.abbreviation)")
	@Override
	public Page<Product> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.productType.organization.abbreviation)")
	@Override
	public List<Product> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#product.productType.organization.abbreviation)")
	@Override
	public <S extends Product> S save(@Param("product") S product);

	@PreAuthorize("hasRole('ADMIN'+#product.productType.organization.abbreviation)")
	@Override
	public void delete(@Param("product") Product product);

	/*
	 * Search functions
	 */
	//Is there a need to add preauthorize here
	public List<Product> findAllByOrderByNameAsc();

}
