package app.business.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.UserViewService;
import app.business.services.UserViewService.UserDetails;

@Controller
@RequestMapping("/web/{org}")
public class UsersController {

	@Autowired
	UserViewService userViewService; 

	@RequestMapping(value="/usersPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String usersPage(@PathVariable String org, Model model) {

		List<UserDetails> rows = userViewService.getUserDetailsByOrganization(org);
		
		model.addAttribute("organizationMemberships",rows);
		return "users";
	}


}