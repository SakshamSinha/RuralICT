package app.business.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.UserViewService;

@Controller
@RequestMapping("/web/{org}")
public class UsersController {

	@Autowired
	UserViewService userViewService; 

	@RequestMapping(value="/usersPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String usersPage(@PathVariable String org, Model model) {

		List<UserView> rows = userViewService.getUserViewByOrganization(org);
		
		model.addAttribute("organizationMemberships",rows);
		return "users";
	}


}