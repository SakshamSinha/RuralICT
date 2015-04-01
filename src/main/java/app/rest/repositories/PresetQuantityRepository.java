package app.rest.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.PresetQuantity;

public interface PresetQuantityRepository extends JpaRepository<PresetQuantity, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.organization.organizationId)")
	@Override
	public PresetQuantity findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.organizationId)")
	@Override
	public List<PresetQuantity> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.organization.organizationId)")
	@Override
	public Page<PresetQuantity> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.organizationId)")
	@Override
	public List<PresetQuantity> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#quantity.organization.organizationId)")
	@Override
	public <S extends PresetQuantity> S save(S quantity);

	@PreAuthorize("hasRole('ADMIN'+#quantity.organization.organizationId)")
	@Override
	public void delete(PresetQuantity quantity);

	/*
	 * Search functions
	 */

}
