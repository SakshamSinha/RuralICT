package webapp.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import webapp.model.entities.User;
import webapp.model.entities.UserPhoneNumber;

public interface UserPhoneNumberRepository extends JpaRepository<UserPhoneNumber, String> {

	List<UserPhoneNumber> findByUser(@Param("user") User user);
	UserPhoneNumber findByUserAndPrimary(@Param("user") User user, @Param("primary") int primary);
}
