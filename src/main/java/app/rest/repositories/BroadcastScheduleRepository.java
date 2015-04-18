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

import app.entities.BroadcastSchedule;

public interface BroadcastScheduleRepository extends JpaRepository<BroadcastSchedule, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN_OR_PUBLISHER'+returnObject.broadcast.organization.abbreviation)")
	@Override
	public BroadcastSchedule findOne(Integer id);

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.broadcast.organization.abbreviation)")
	@Override
	public List<BroadcastSchedule> findAll();

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.broadcast.organization.abbreviation)")
	@Override
	public Page<BroadcastSchedule> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN_OR_PUBLISHER'+filterObject.broadcast.organization.abbreviation)")
	@Override
	public List<BroadcastSchedule> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN_OR_PUBLISHER'+#schedule.broadcast.organization.abbreviation)")
	@Override
	public <S extends BroadcastSchedule> S save(@Param("schedule") S schedule);

	@PreAuthorize("hasRole('ADMIN_OR_PUBLISHER'+#schedule.broadcast.organization.abbreviation)")
	@Override
	public void delete(@Param("schedule") BroadcastSchedule schedule);

	/*
	 * Search functions
	 */

}
