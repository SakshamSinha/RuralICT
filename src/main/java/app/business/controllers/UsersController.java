package app.business.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.OrganizationService;
import app.business.services.UserViewService;
import app.business.services.UserViewService.UserView;
import app.entities.Organization;

@Controller
@RequestMapping("/web/{org}")
public class UsersController {

	@Autowired
	UserViewService userViewService; 
	
	@Autowired
	OrganizationService organizationService;

	@RequestMapping(value="/usersPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String usersPage(@PathVariable String org, Model model) {

		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<UserView> rows = userViewService.getUserViewListByOrganization(org);
		
		model.addAttribute("organization",organization);
		model.addAttribute("userViews",rows);
		return "users";
	}
}