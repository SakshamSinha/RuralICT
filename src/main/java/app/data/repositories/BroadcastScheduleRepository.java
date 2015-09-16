package app.data.repositories;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.BroadcastRecipient;
import app.entities.BroadcastSchedule;
import app.entities.User;
import app.entities.broadcast.Broadcast;

public interface BroadcastScheduleRepository extends JpaRepository<BroadcastSchedule, Integer> {
	/*
	 * Default functions
	 */
	public final static String FIND_BY_BROADCASTID_QUERY = "SELECT bs " + 
            "FROM broadcast_schedule bs " +
            "order by broadcast_schedule_id DESC LIMIT 1";

	//TODO
	//@PostAuthorize("hasRole('ADMIN_OR_PUBLISHER'+returnObject.broadcast.organization.abbreviation)")
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

	//TODO
	//@PreAuthorize("hasRole('ADMIN_OR_PUBLISHER'+#schedule.broadcast.organization.abbreviation)")
	@Override
	public <S extends BroadcastSchedule> S save(@Param("schedule") S schedule);

	@PreAuthorize("hasRole('ADMIN_OR_PUBLISHER'+#schedule.broadcast.organization.abbreviation)")
	@Override
	public void delete(@Param("schedule") BroadcastSchedule schedule);

	/*
	 * Search functions
	 */
	public List<BroadcastSchedule> findByBroadcastOrderByTimeAsc(Broadcast broadcast);
	public List<BroadcastSchedule> findByBroadcastAndSendToAllTrueAndTimeGreaterThanOrderByTimeAsc(Broadcast broadcast, Timestamp time);
	public List<BroadcastSchedule> findByBroadcastAndTimeGreaterThanOrderByTimeAsc(Broadcast broadcast, Timestamp time);
	public List<BroadcastSchedule> findByBroadcast(Broadcast broadcast);
	public BroadcastSchedule findByBroadcastAndTime(Broadcast broadcast, Timestamp time);
	/*@Query(FIND_BY_BROADCASTID_QUERY)
	public BroadcastSchedule findLast();*/
}
