package app.data.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.InboundCall;
import app.entities.Organization;

public interface InboundCallRepository extends JpaRepository<InboundCall, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('ADMIN'+returnObject.organization.abbreviation)")
	@Override
	public InboundCall findOne(Integer id);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.abbreviation)")
	@Override
	public List<InboundCall> findAll();

	@PostFilter("hasRole('ADMIN'+filterObject.organization.abbreviation)")
	@Override
	public Page<InboundCall> findAll(Pageable pageable);

	@PostFilter("hasRole('ADMIN'+filterObject.organization.abbreviation)")
	@Override
	public List<InboundCall> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#call.organization.abbreviation)")
	@Override
	public void delete(@Param("call") InboundCall call);

	/*
	 * Search functions
	 */
	public List<InboundCall> findByOrganization(Organization organization);
	
	@RestResource(rel="getInboundCalls", path="getInboundCalls")
	public List<InboundCall> findByOrganizationAndTimeBetween(@Param("org") Organization org, @Param("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date from, @Param("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date to);
}
