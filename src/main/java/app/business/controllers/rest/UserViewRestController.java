package app.business.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.GroupMembershipService;
import app.business.services.GroupService;
import app.business.services.OrganizationMembershipService;
import app.business.services.UserService;
import app.business.services.UserViewService;
import app.business.services.UserViewService.UserView;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.OrganizationMembership;

@RestController
@RequestMapping("/api")
public class UserViewRestController {

	@Autowired
	UserViewService userViewService;

	@Autowired
	GroupService groupService;

	@Autowired
	GroupMembershipService groupMembershipService;

	@Autowired
	OrganizationMembershipService organizationMembershipService;

	@Autowired
	UserService userService;

	@RequestMapping(value="/userViews/add/{groupId}", method=RequestMethod.POST)
	@Transactional
	public @ResponseBody boolean addUserView(@RequestBody UserView userView, @PathVariable int groupId) {
		try {
			Group group = groupService.getGroup(groupId);
			userView = userViewService.addUserView(userView);

			groupMembershipService.addGroupMembership(new GroupMembership(group, userView.getUser()));
			organizationMembershipService.addOrganizationMembership(new OrganizationMembership(group.getOrganization(), userView.getUser(), false, false));
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@RequestMapping(value="/userViews/delete/{userId}", method=RequestMethod.DELETE)
	@Transactional
	public @ResponseBody boolean removeUserView(@PathVariable int userId) {
		try {
			UserView userView = userViewService.getUserView(userService.getUser(userId));
			userViewService.removeUserView(userView);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
