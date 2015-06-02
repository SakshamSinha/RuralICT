package app.business.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.OrganizationService;
import app.entities.Group;
import app.entities.Organization;

@Controller
@RequestMapping("/web/{org}")
public class GroupsListController {

	@Autowired
	OrganizationService organizationService;

	@RequestMapping(value="/groupsList")
	@PreAuthorize("hasRole('MEMBER'+#org)")
	@Transactional
	public String groupsList(@PathVariable String org, Model model) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		model.addAttribute("groups",new ArrayList<Group> (organizationService.getOrganizationGroupList(organization)));
		return "groupsList";
	}

}
