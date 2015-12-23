package app.data.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.BroadcastDefaultSettings;
import app.entities.Organization;

public interface BroadcastDefaultSettingsRepository extends JpaRepository<BroadcastDefaultSettings, Integer> {
	/*
	 * Default functions
	 */

	@Override
	public BroadcastDefaultSettings findOne(Integer id);

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.organization.abbreviation)")
	@Override
	public List<BroadcastDefaultSettings> findAll();

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.organization.abbreviation)")
	@Override
	public Page<BroadcastDefaultSettings> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.organization.abbreviation)")
	@Override
	public List<BroadcastDefaultSettings> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#setting.organization.abbreviation)")
	@Override
	public <S extends BroadcastDefaultSettings> S save(@Param("setting") S setting);

	@PreAuthorize("hasRole('ADMIN'+#setting.organization.abbreviation)")
	@Override
	public void delete(@Param("setting") BroadcastDefaultSettings setting);

	/*
	 * Search functions
	 */
	//TODO change function to have find by group also 
	public BroadcastDefaultSettings findByOrganization(Organization organization);
}
