package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.GroupMembership;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Integer> {

}
