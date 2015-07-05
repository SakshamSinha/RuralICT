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

import app.entities.BroadcastRecipient;
import app.entities.BroadcastSchedule;
import app.entities.OutboundCall;

public interface OutboundCallRepository extends JpaRepository<OutboundCall, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.broadcastRecipient.broadcast.organization.abbreviation)")
	@Override
	public OutboundCall findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.broadcastRecipient.broadcast.organization.abbreviation)")
	@Override
	public List<OutboundCall> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.broadcastRecipient.broadcast.organization.abbreviation)")
	@Override
	public Page<OutboundCall> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.broadcastRecipient.broadcast.organization.abbreviation)")
	@Override
	public List<OutboundCall> findAll(Sort sort);

	@Override
	public <S extends OutboundCall> S save(@Param("call") S call);

	@PreAuthorize("hasRole('ADMIN'+#call.broadcastRecipient.broadcast.organization.abbreviation)")
	@Override
	public void delete(@Param("call") OutboundCall call);

	/*
	 * Search functions
	 */
	public OutboundCall findByBroadcastScheduleAndBroadcastRecipient(BroadcastSchedule broadcastSchedule, BroadcastRecipient broadcastRecipient);
}
