package app.data.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.BillLayoutSettings;
import app.entities.Organization;

public interface BillLayoutSettingsRepository extends JpaRepository<BillLayoutSettings, Integer> {
	/*
	 * Default functions
	 */

	@Override
	public BillLayoutSettings findOne(Integer id);

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.organization.abbreviation)")
	@Override
	public List<BillLayoutSettings> findAll();

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.organization.abbreviation)")
	@Override
	public Page<BillLayoutSettings> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.organization.abbreviation)")
	@Override
	public List<BillLayoutSettings> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#settings.organization.abbreviation)")
	@Override
	public <S extends BillLayoutSettings> S save(@Param("settings") S settings);

	@PreAuthorize("hasRole('ADMIN'+#settings.organization.abbreviation)")
	@Override
	public void delete(@Param("settings") BillLayoutSettings setting);

	/*
	 * Search functions
	 */ 
	public BillLayoutSettings findByOrganization(Organization organization);
}
