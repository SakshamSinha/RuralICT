package webapp.controllers.rest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

}
