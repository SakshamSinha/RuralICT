package webapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import webapp.entities.broadcast.Broadcast;

public interface BroadcastRepository extends JpaRepository<Broadcast, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+returnObject.organization.organizationId)")
	@Override
	public Broadcast findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+filterObject.organization.organizationId)")
	@Override
	public List<Broadcast> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+filterObject.organization.organizationId)")
	@Override
	public Page<Broadcast> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+filterObject.organization.organizationId)")
	@Override
	public List<Broadcast> findAll(Sort sort);

	@PreAuthorize("hasRole('PUBLISHER'+#broadcast.organization.organizationId)")
	@Override
	public <S extends Broadcast> S save(S broadcast);

	@PreAuthorize("hasRole('ADMIN'+#broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+#broadcast.organization.organizationId)")
	@Override
	public void delete(Broadcast broadcast);

	/*
	 * Search functions
	 */

}
