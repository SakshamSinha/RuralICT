package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import webapp.entities.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
	/*
	 * Default functions
	 */

	@PreAuthorize("hasRole('ADMIN'+#org.organizationId) or "
			+ "(#org.parentOrganization != null and hasRole('ADMIN'+#org.parentOrganization.organizationId))")
	@Override
	public <S extends Organization> S save(S org);

	@PreAuthorize("hasRole('ADMIN'+#org.organizationId)")
	@Override
	public void delete(Organization org);

	/*
	 * Search functions
	 */

	Organization findByAbbreviation(@Param("abbreviation") String abbreviation);

}
