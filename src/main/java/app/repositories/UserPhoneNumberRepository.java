package app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.User;
import app.entities.UserPhoneNumber;

public interface UserPhoneNumberRepository extends JpaRepository<UserPhoneNumber, String> {
	/*
	 * Default functions
	 */

	@PreAuthorize("principal.userId == #number.user.userId")
	@Override
	public <S extends UserPhoneNumber> S save(S number);

	@PreAuthorize("principal.userId == #number.user.userId")
	@Override
	public void delete(UserPhoneNumber number);

	/*
	 * Search functions
	 */

	List<UserPhoneNumber> findByUser(@Param("user") User user);
	UserPhoneNumber findByUserAndPrimary(@Param("user") User user, @Param("primary") int primary);

}
