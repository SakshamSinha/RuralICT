package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.entities.Organization;
import app.entities.User;
import app.rest.repositories.OrganizationRepository;
import app.rest.repositories.UserRepository;
import app.util.Utils;

@Controller
public class RootController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrganizationRepository organizationRepository;

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
	public String organizationRoot(@PathVariable String org, Model model) {
		Organization organization = organizationRepository.findByAbbreviation(org);
		model.addAttribute("organization", organization);
		return "index";
	}

	/*
	 * We want there to be a trailing slash after the organization, so that AngularJS understands that it needs to
	 * look for resources under the organization, and not at the same path level.
	 */
	@RequestMapping(value="/web/{org}")
	public String organizationRootWithoutSlash(@PathVariable String org) {
		return "redirect:/web/"+org+"/";
	}

}
