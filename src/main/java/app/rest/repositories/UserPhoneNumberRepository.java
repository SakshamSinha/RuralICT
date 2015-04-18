package app.rest.repositories;

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
	public <S extends UserPhoneNumber> S save(@Param("number") S number);

	@PreAuthorize("principal.userId == #number.user.userId")
	@Override
	public void delete(@Param("number") UserPhoneNumber number);

	/*
	 * Search functions
	 */

	UserPhoneNumber findByUserAndPrimaryTrue(@Param("user") User user);

}
