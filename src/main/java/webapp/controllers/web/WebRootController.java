package webapp.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import webapp.util.Utils;

@Controller
public class WebRootController {

	@RequestMapping("/")
	public String rootRedirect(Model model) {
		model.addAttribute("displayString", "Hello " + Utils.getAuthenticatedUser().getUsername() + "!");
		return "welcome";
	}

}
