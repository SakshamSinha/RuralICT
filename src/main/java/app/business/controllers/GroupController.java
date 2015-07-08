package app.business.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.OrganizationService;
import app.data.repositories.GroupRepository;
import app.entities.Group;
import app.entities.Organization;

@Controller
@RequestMapping("/web/{org}")
public class GroupController {

	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	OrganizationService organizationService;

	@RequestMapping(value="/groupPage/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String groupPage(@PathVariable String org, @PathVariable int groupId, Model model) {
		Group group = groupRepository.findOne(groupId);
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		model.addAttribute("group", group);
		model.addAttribute("organization",organization);
		return "groupOperations";
	}

}