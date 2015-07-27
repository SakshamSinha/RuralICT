package app.business.controllers.rest;

import javax.persistence.RollbackException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;
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
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.UserViewService;
import app.business.services.UserViewService.UserView;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.UserPhoneNumber;

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
	OrganizationService organizationService;

	@Autowired
	UserPhoneNumberService userPhoneNumberService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value="/userViews/add/{org}/{groupId}", method=RequestMethod.POST)
	public @ResponseBody boolean addUserView(@RequestBody UserView userView, @PathVariable int groupId, @PathVariable String org ) {
		
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		Group group = groupService.getGroup(groupId);
		try {
			
			UserPhoneNumber phone = userPhoneNumberService.getUserPhoneNumber(userView.getPhone().getPhoneNumber());
			
			if(userPhoneNumberService.findPreExistingPhoneNumber(userView.getPhone().getPhoneNumber()))
			{
				// if phone number doesn't exist, add the user and his phone number to database
				userView = userViewService.addUserView(userView);
				phone = userPhoneNumberService.getUserPhoneNumber(userView.getPhone().getPhoneNumber());
				// will have to test in new organization
				
			}
			
			// else if his phone number exists
			GroupMembership groupMembership = groupMembershipService.getUserGroupMembership(phone.getUser(), group);
			
			// check if the user has previous group membership
			if(groupMembership == null)
			{
			groupMembershipService.addGroupMembership(new GroupMembership(group, phone.getUser()));
			}
			else
				return false;
			
			OrganizationMembership organizationMembership = organizationMembershipService.getUserOrganizationMembership(phone.getUser(), organization);
			
			// check if the user has previous organization membership
			// this is useful when adding an user through a new organization
			if(organizationMembership == null)
			{
			organizationMembershipService.addOrganizationMembership(new OrganizationMembership(group.getOrganization(),phone.getUser(), false, false));
			}
			
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
