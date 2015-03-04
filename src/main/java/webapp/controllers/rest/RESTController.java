package webapp.controllers.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RESTController {

	@RequestMapping
	public String test() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return "API test for " + auth.getName();
	}

	@RequestMapping(value="/{org}/member")
	@PreAuthorize("hasRole('MEMBER'+#org)")
	public String testPermissionsMember(@PathVariable String org) {
		return "Authorized member!";
	}

	@RequestMapping(value="/{org}/publisher")
	@PreAuthorize("hasRole('PUBLISHER'+#org)")
	public String testPermissionsPublisher(@PathVariable String org) {
		return "Authorized publisher!";
	}

	@RequestMapping(value="/{org}/admin")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String testPermissionsAdmin(@PathVariable String org) {
		return "Authorized admin!";
	}

}
