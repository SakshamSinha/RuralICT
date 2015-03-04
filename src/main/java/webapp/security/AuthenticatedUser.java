package webapp.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import webapp.model.entities.User;
import webapp.model.entities.UserPhoneNumber;
import webapp.model.repositories.UserPhoneNumberRepository;

public class AuthenticatedUser implements UserDetails {
	private static final long serialVersionUID = 1L;

	int userId;
	String username;
	String password;
	List<GrantedAuthority> authorities;

	public AuthenticatedUser(User user, UserPhoneNumberRepository userPhoneNumberRepository) {
		userId = user.getUserId();
		username = user.getEmail();
		password = user.getSha256Password();

		if (username == null || username.isEmpty()) {
			UserPhoneNumber number = userPhoneNumberRepository.findByUserAndPrimary(user, 1);
			username = number.getPhoneNumber();
			if (username == null || username.isEmpty())
				username = "@User" + user.getUserId(); // we should probably never come to this
		}

		authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
	}

	public int getUserId() {
		return userId;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
