package app.util;

import org.springframework.security.core.context.SecurityContextHolder;

import app.entities.User;
import app.repositories.UserRepository;
import app.security.AuthenticatedUser;

/**
 * Utilities for this application.
 */
public class Utils {

	/**
	 * Returns the UserDetails object set during authentication for the currently logged in user. No database lookup is
	 * done.
	 * @return The currently logged in user's AuthenticatedUser object.
	 */
	public static AuthenticatedUser getSecurityPrincipal() {
		return (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	/**
	 * Returns the persistent user object from the database for the currently logged in user.
	 * @param userRepository The user repository for the user lookup.
	 * @return The currently logged in user's persistent User object.
	 */
	public static User getCurrentUser(UserRepository userRepository) {
		return userRepository.findOne(getSecurityPrincipal().getUserId());
	}

}
