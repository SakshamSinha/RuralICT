package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import webapp.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
	/*
	 * Default functions
	 */

	@PreAuthorize("hasRole('ADMIN'+#group.organization.organizationId)")
	@Override
	public <S extends Group> S save(S group);

	@PreAuthorize("hasRole('ADMIN'+#group.organization.organizationId)")
	@Override
	public void delete(Group group);

	/*
	 * Search functions
	 */

}
