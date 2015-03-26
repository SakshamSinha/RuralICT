package webapp.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByEmail(String email);

}
