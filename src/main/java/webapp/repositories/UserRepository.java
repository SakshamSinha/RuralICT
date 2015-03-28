package webapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import webapp.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByEmail(@Param("email") String email);

}
