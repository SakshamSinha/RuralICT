package app.business.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.UserService;
import app.entities.OrganizationMembership;
import app.entities.User;
import app.util.Utils;

@Controller
public class RootController {

	@Autowired
	UserService userService;

	@RequestMapping("/")
	public String contextRoot() {
		return "redirect:/web";
	}

	/*
	 * We want there to be a trailing slash after the organization, so that AngularJS understands that it needs to
	 * look for resources under the organization, and not at the same path level.
	 */
	@RequestMapping("/web/{org}")
	public String organizationRootWithoutSlash(@PathVariable String org) {
		return "redirect:/web/"+org+"/";
	}

	@RequestMapping("/web")
	@Transactional
	public String webRoot(Model model, HttpSession session) {

		/*
		 * Right now, we're only letting admins access the web interface. So only take into consideration orgs
		 * that the user is an admin of.
		 */
		
		User currentUser = userService.getCurrentUser();
		List<OrganizationMembership> authenticated = userService.getAdminMembershipList(currentUser);

		/*
		 * If the user is an admin in only one org, forward them to that org, else
		 * show them a list of orgs they can access.
		 */
		if (authenticated.size() == 1) {
			return "redirect:/web/"+authenticated.get(0).getOrganization().getAbbreviation()+"/";
		} else if (authenticated.size() > 1) {
			model.addAttribute("organizationMemberships", authenticated);
			return "choose"; // TODO we need to implement this template
		} else {
			session.invalidate();
			return "redirect:/login?error";
		}
	}

}
