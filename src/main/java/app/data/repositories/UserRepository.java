package app.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import app.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	/*
	 * Default functions
	 */

	@PreAuthorize("principal.userId == #user.userId")
	@Override
	public <S extends User> S save(@Param("user") S user);

	@PreAuthorize("principal.userId == #user.userId")
	@Override
	public void delete(@Param("user") User user);

	/*
	 * Search functions
	 */

	List<User> findByEmail(@Param("email") String email);
	
	User findByuserPhoneNumbers_phoneNumber(@Param("phonenumber") String phonenumber);
	
}
