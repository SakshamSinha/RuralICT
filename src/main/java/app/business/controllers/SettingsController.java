package app.business.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.data.repositories.GroupRepository;
import app.data.repositories.OrganizationRepository;
import app.entities.Group;
import app.entities.Organization;

@Controller
@RequestMapping("/web/{org}")
public class SettingsController {
	
	@Autowired
	OrganizationRepository organizationRepository;

	@RequestMapping(value="/settingsPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String settingsPage(@PathVariable String org, Model model) {
		Organization organization = organizationRepository.findByAbbreviation(org);
		model.addAttribute("organization", organization);
		return "settings";
	}
	
}