package app.business.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.GroupService;
import app.business.services.OrganizationService;
import app.business.services.UserViewService;
import app.business.services.UserViewService.UserView;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.Organization;

@Controller
@RequestMapping("/web/{org}")
public class GroupMembersController {

	@Autowired
	UserViewService userViewService;
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	GroupService groupService;
	
	@RequestMapping(value="/memberList/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String settingsPage(@PathVariable String org, @PathVariable int groupId, Model model) {
		
		List<UserView> userViewList = userViewService.getUserViewListByGroup(groupId,org);
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<Group> groupList = groupService.getGroupListByOrganization(organization);
		for (UserView user:userViewList)
		{
			List<GroupMembership> groupMembershipList = user.getUser().getGroupMemberships();
			List<GroupMembership> removeGroupMemberShipList = new ArrayList<GroupMembership>();
			for(int i=0;i<groupMembershipList.size();i++)
			{
				if(groupMembershipList.get(i).getGroup().getOrganization().getName()!=organization.getName())
				{
					removeGroupMemberShipList.add(groupMembershipList.get(i));
				}
			}
			groupMembershipList.removeAll(removeGroupMemberShipList);
		}
		Group parentGroup = organizationService.getParentGroup(organization);
		model.addAttribute("organization", organization);
		model.addAttribute("userViews", userViewList);
		model.addAttribute("parentGroup", parentGroup);
		model.addAttribute("groupId", groupId);
		model.addAttribute("g",groupList);
		return "groupWiseMember";
	}

}