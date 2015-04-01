package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.entities.Organization;
import app.rest.repositories.OrganizationRepository;

@Controller
@RequestMapping("/web/{org}")
public class IndexController {

	@Autowired
	OrganizationRepository organizationRepository;

	@RequestMapping(value="/")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String indexPage(@PathVariable String org, Model model) {
		Organization organization = organizationRepository.findByAbbreviation(org);
		model.addAttribute("organization", organization);
		return "index";
	}

}
