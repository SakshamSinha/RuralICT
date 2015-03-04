package webapp.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import webapp.model.entities.User;
import webapp.model.entities.UserPhoneNumber;

public interface UserPhoneNumberRepository extends JpaRepository<UserPhoneNumber, String> {

	List<UserPhoneNumber> findByUser(User user);
	UserPhoneNumber findByUserAndPrimary(User user, int primary);
}
