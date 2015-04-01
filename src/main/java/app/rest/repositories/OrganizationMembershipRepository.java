package app.rest.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.OrganizationMembership;

public interface OrganizationMembershipRepository extends JpaRepository<OrganizationMembership, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("principal.userId == returnObject.user.userId or "
			+ "hasRole('ADMIN'+returnObject.organization.organizationId)")
	@Override
	public OrganizationMembership findOne(Integer id);

	@PostFilter("principal.userId == filterObject.user.userId or "
			+ "hasRole('ADMIN'+filterObject.organization.organizationId)")
	@Override
	public List<OrganizationMembership> findAll();

	@PostFilter("principal.userId == filterObject.user.userId or "
			+ "hasRole('ADMIN'+filterObject.organization.organizationId)")
	@Override
	public Page<OrganizationMembership> findAll(Pageable pageable);

	@PostFilter("principal.userId == filterObject.user.userId or "
			+ "hasRole('ADMIN'+filterObject.organization.organizationId)")
	@Override
	public List<OrganizationMembership> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#membership.organization.organizationId)")
	@Override
	public <S extends OrganizationMembership> S save(S membership);

	@PreAuthorize("hasRole('ADMIN'+#membership.organization.organizationId)")
	@Override
	public void delete(OrganizationMembership membership);

	/*
	 * Search functions
	 */

}
