package app.rest.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.broadcast.Broadcast;

public interface BroadcastRepository extends JpaRepository<Broadcast, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN_OR_PUBLISHER'+returnObject.organization.abbreviation)")
	@Override
	public Broadcast findOne(Integer id);

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.organization.abbreviation)")
	@Override
	public List<Broadcast> findAll();

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.organization.abbreviation)")
	@Override
	public Page<Broadcast> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.organization.abbreviation)")
	@Override
	public List<Broadcast> findAll(Sort sort);

	@PreAuthorize("hasRole('PUBLISHER'+#broadcast.organization.abbreviation)")
	@Override
	public <S extends Broadcast> S save(@Param("broadcast") S broadcast);

	@PreAuthorize("hasRole('ADMIN_OR_PUBLISHER'+#broadcast.organization.abbreviation)")
	@Override
	public void delete(@Param("broadcast") Broadcast broadcast);

	/*
	 * Search functions
	 */

}