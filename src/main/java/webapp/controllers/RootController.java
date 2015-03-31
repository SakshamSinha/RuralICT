package webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import webapp.entities.User;
import webapp.repositories.UserRepository;
import webapp.util.Utils;

@Controller
public class RootController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/")
	public String root(Model model) {
		User user = Utils.getCurrentUser(userRepository);
		model.addAttribute("organizationMemberships", user.getOrganizationMemberships());
		return "choose"; // TODO we need to implement this template
	}

	@RequestMapping("/{org}")
	public String organizationRoot(@PathVariable String org) {
		return "index";
	}

}
