package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import webapp.model.entities.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

	Organization findByAbbreviation(@Param("abbreviation") String abbreviation);

}
