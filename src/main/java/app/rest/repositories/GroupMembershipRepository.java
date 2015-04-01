package app.rest.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.GroupMembership;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("principal.userId == returnObject.user.userId or "
			+ "hasRole('ADMIN_OR_PUBLISHER'+returnObject.group.organization.organizationId)")
	@Override
	public GroupMembership findOne(Integer id);

	@PostFilter("principal.userId == filterObject.user.userId or "
			+ "hasRole('ADMIN_OR_PUBLISHER'+filterObject.group.organization.organizationId)")
	@Override
	public List<GroupMembership> findAll();

	@PostFilter("principal.userId == filterObject.user.userId or "
			+ "hasRole('ADMIN_OR_PUBLISHER'+filterObject.group.organization.organizationId)")
	@Override
	public Page<GroupMembership> findAll(Pageable pageable);

	@PostFilter("principal.userId == filterObject.user.userId or "
			+ "hasRole('ADMIN_OR_PUBLISHER'+filterObject.group.organization.organizationId)")
	@Override
	public List<GroupMembership> findAll(Sort sort);

	@PreAuthorize("hasRole('ADMIN'+#membership.group.organization.organizationId)")
	@Override
	public <S extends GroupMembership> S save(S membership);

	@PreAuthorize("hasRole('ADMIN'+#membership.group.organization.organizationId)")
	@Override
	public void delete(GroupMembership membership);

	/*
	 * Search functions
	 */

}
