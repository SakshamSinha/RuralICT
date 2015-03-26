package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

	Organization findByAbbreviation(String abbreviation);

}
