package webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

}
