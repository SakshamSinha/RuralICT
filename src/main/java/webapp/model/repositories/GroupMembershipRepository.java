package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.GroupMembership;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Integer> {

}
