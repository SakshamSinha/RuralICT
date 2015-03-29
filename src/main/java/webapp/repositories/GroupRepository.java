package webapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import webapp.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
	/*
	 * Default functions
	 */

	@PostAuthorize("hasRole('MEMBER'+returnObject.organization.organizationId)")
	@Override
	public Group findOne(Integer id);

	@PostFilter("hasRole('MEMBER'+filterObject.organization.organizationId)")
	@Override
	public List<Group> findAll();

	@PostFilter("hasRole('MEMBER'+filterObject.organization.organizationId)")
	@Override
	public Page<Group> findAll(Pageable pageable);

	@PostFilter("hasRole('MEMBER'+filterObject.organization.organizationId)")
	@Override
	public List<Group> findAll(Sort sort);
	
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
