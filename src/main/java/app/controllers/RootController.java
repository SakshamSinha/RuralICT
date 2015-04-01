package app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.entities.Organization;
import app.entities.OrganizationMembership;
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
	@Transactional
	public String webRoot(Model model) {
		User user = Utils.getCurrentUser(userRepository);
		List<OrganizationMembership> authenticated = new ArrayList<OrganizationMembership>();

		/*
		 * Right now, we're only letting admins access the web interface. So only take into consideration orgs
		 * that the user is an admin of.
		 */
		for (OrganizationMembership m : user.getOrganizationMemberships()) {
			if (m.getIsAdmin())
				authenticated.add(m);
		}

		/*
		 * If the user is an admin in only one org, forward them to that org, else
		 * show them a list of orgs they can access.
		 */
		if (authenticated.size() == 1) {
			return "redirect:/web/"+authenticated.get(0).getOrganization().getAbbreviation()+"/";
		} else {
			model.addAttribute("organizationMemberships", authenticated);
			return "choose"; // TODO we need to implement this template
		}
	}

	@RequestMapping("/web/{org}/")
	public String organizationRoot(@PathVariable String org, Model model) {
		Organization organization = organizationRepository.findByAbbreviation(org);
		model.addAttribute("organization", organization);
		return "index";
	}

	/*
	 * We want there to be a trailing slash after the organization, so that AngularJS understands that it needs to
	 * look for resources under the organization, and not at the same path level.
	 */
	@RequestMapping("/web/{org}")
	public String organizationRootWithoutSlash(@PathVariable String org) {
		return "redirect:/web/"+org+"/";
	}

}
