package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

}
