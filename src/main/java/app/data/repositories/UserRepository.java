package app.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import app.entities.Group;
import app.entities.Organization;
import app.entities.User;

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
	
}
