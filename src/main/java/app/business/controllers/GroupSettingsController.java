package app.business.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.GroupService;
import app.business.services.OrganizationService;
import app.entities.Group;

@Controller
@RequestMapping("/web/{org}")
public class GroupSettingsController {

	@Autowired
	GroupService groupService;
	
	@Autowired
	OrganizationService organizationService;

	@RequestMapping(value="/groupSettings/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String groupPage(@PathVariable String org, @PathVariable int groupId, Model model) {
		Group group = groupService.getGroup(groupId);
		Group parentGroup = organizationService.getParentGroup(organizationService.getOrganizationByAbbreviation(org));
		model.addAttribute("group", group);
		model.addAttribute("parentGroup", parentGroup);
		return "groupSettings";
	}

}
