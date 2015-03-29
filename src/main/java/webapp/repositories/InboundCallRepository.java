package webapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import webapp.entities.InboundCall;

public interface InboundCallRepository extends JpaRepository<InboundCall, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.organization.organizationId)")
	@Override
	public InboundCall findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.organizationId)")
	@Override
	public List<InboundCall> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.organization.organizationId)")
	@Override
	public Page<InboundCall> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.organizationId)")
	@Override
	public List<InboundCall> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#call.organization.organizationId)")
	@Override
	public <S extends InboundCall> S save(S call);

	@PreAuthorize("hasRole('ADMIN'+#call.organization.organizationId)")
	@Override
	public void delete(InboundCall call);

	/*
	 * Search functions
	 */

}
