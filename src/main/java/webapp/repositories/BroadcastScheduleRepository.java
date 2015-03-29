package webapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import webapp.entities.BroadcastSchedule;

public interface BroadcastScheduleRepository extends JpaRepository<BroadcastSchedule, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+returnObject.broadcast.organization.organizationId)")
	@Override
	public BroadcastSchedule findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+filterObject.broadcast.organization.organizationId)")
	@Override
	public List<BroadcastSchedule> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+filterObject.broadcast.organization.organizationId)")
	@Override
	public Page<BroadcastSchedule> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+filterObject.broadcast.organization.organizationId)")
	@Override
	public List<BroadcastSchedule> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#schedule.broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+#schedule.broadcast.organization.organizationId)")
	@Override
	public <S extends BroadcastSchedule> S save(S schedule);

	@PreAuthorize("hasRole('ADMIN'+#schedule.broadcast.organization.organizationId) or "
			+ "hasRole('PUBLISHER'+#schedule.broadcast.organization.organizationId)")
	@Override
	public void delete(BroadcastSchedule schedule);

	/*
	 * Search functions
	 */

}
