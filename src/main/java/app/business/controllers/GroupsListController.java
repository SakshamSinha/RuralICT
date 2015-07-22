package app.business.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		ArrayList<Group> childOrganizaitionsList = new ArrayList<Group> (organizationService.getOrganizationGroupList(organization));
		Collections.sort(childOrganizaitionsList, new GroupNameComparator());
		int i = 0;
		Group parentGroup=null;
		for (Group group:childOrganizaitionsList)
		{
			if(group.getParentGroup()==null)
			{
				parentGroup=group;
				break;
			}
			i++;
		}
		childOrganizaitionsList.remove(i);
		childOrganizaitionsList.add(0, parentGroup);
		model.addAttribute("groups",childOrganizaitionsList);
		return "groupsList";
	}

	class GroupNameComparator implements Comparator<Group>
	{
		@Override
		public int compare(Group firstgroup, Group secondgroup) {
			int i=firstgroup.getName().compareTo(secondgroup.getName());
			return i;
		}
	}
}
