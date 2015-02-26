package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.OrganizationMembership;

public interface OrganizationMembershipRepository extends JpaRepository<OrganizationMembership, Integer> {

}
