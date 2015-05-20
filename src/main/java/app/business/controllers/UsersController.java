package app.business.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.UserRowService;
import app.business.services.UserRowService.UserRow;

@Controller
@RequestMapping("/web/{org}")
public class UsersController {

	@Autowired
	UserRowService userRowService; 

	@RequestMapping(value="/usersPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String usersPage(@PathVariable String org, Model model) {

		List<UserRow> rows = userRowService.getUserRowForOrganization(org);
		
		model.addAttribute("organizationMemberships",rows);
		return "users";
	}


}