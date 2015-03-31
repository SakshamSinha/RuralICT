package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.entities.User;
import app.repositories.UserRepository;
import app.util.Utils;

@Controller
public class RootController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/")
	public String contextRoot() {
		return "redirect:/web";
	}

	@RequestMapping("/web")
	public String webRoot(Model model) {
		User user = Utils.getCurrentUser(userRepository);
		model.addAttribute("organizationMemberships", user.getOrganizationMemberships());
		return "choose"; // TODO we need to implement this template
	}

	@RequestMapping(value="/web/{org}/")
	public String organizationRoot(@PathVariable String org) {
		return "index";
	}

}
