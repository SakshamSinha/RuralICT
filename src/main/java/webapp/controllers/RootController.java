package webapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import webapp.entities.OrganizationMembership;
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
		List<OrganizationMembership> memberships = user.getOrganizationMemberships();
		for (OrganizationMembership m : memberships) {
			// TODO
		}
		return "choose";
	}

	@RequestMapping("/{org}")
	public String organizationRoot() {
		return "index";
	}

}
