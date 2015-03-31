package app.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.OutboundCall;

public interface OutboundCallRepository extends JpaRepository<OutboundCall, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.broadcastRecipient.broadcast.organization.organizationId)")
	@Override
	public OutboundCall findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.broadcastRecipient.broadcast.organization.organizationId)")
	@Override
	public List<OutboundCall> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.broadcastRecipient.broadcast.organization.organizationId)")
	@Override
	public Page<OutboundCall> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.broadcastRecipient.broadcast.organization.organizationId)")
	@Override
	public List<OutboundCall> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#call.broadcastRecipient.broadcast.organization.organizationId)")
	@Override
	public <S extends OutboundCall> S save(S call);

	@PreAuthorize("hasRole('ADMIN'+#call.broadcastRecipient.broadcast.organization.organizationId)")
	@Override
	public void delete(OutboundCall call);

	/*
	 * Search functions
	 */

}
