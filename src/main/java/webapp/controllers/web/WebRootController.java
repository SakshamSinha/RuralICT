package webapp.controllers.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import webapp.security.AuthenticatedUser;

@Controller
public class WebRootController {

	@RequestMapping("/")
	public String rootRedirect(Model model) {
		AuthenticatedUser authUser = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("displayString", "Hello " + authUser.getUsername() + "!");
		return "welcome";
	}

}
