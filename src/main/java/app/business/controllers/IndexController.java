package app.business.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import app.business.services.OrganizationService;
import app.entities.Organization;


@Controller
@RequestMapping("/web/{org}")
public class IndexController {

	@Autowired
	OrganizationService organizationService;

	@RequestMapping(value="/")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String indexPage(@PathVariable String org, Model model) {
		
	    Organization organization= organizationService.getOrganizationByAbbreviation(org);
		model.addAttribute("organization", organization);
		return "index";
	}

}
