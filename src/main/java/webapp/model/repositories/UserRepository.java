package webapp.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
