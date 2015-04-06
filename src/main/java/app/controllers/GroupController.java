package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.entities.Group;
import app.rest.repositories.GroupRepository;

@Controller
@RequestMapping("/web/{org}")
public class GroupController {

	@Autowired
	GroupRepository groupRepository;

	@RequestMapping(value="/groupPage/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String groupPage(@PathVariable String org, @PathVariable int groupId, Model model) {
		Group group = groupRepository.findOne(groupId);
		model.addAttribute("group", group);
		return "groupOperations";
	}

}