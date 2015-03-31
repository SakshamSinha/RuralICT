package app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.productType.organization.organizationId)")
	@Override
	public Product findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.productType.organization.organizationId)")
	@Override
	public List<Product> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.productType.organization.organizationId)")
	@Override
	public Page<Product> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.productType.organization.organizationId)")
	@Override
	public List<Product> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#product.productType.organization.organizationId)")
	@Override
	public <S extends Product> S save(S product);

	@PreAuthorize("hasRole('ADMIN'+#product.productType.organization.organizationId)")
	@Override
	public void delete(Product product);

	/*
	 * Search functions
	 */

}
