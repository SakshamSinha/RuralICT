package app.business.controllers.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.GroupMembershipService;
import app.business.services.GroupService;
import app.business.services.OrganizationMembershipService;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.UserViewService;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.User;
import app.entities.UserPhoneNumber;
import app.util.UserManage;

@RestController
@RequestMapping("/api/{org}/manageUsers")
public class ManageUsersRestController {

	@Autowired
	UserViewService userViewService;

	@Autowired
	UserService userService;

	@Autowired
	OrganizationService organizationService;

	@Autowired
	OrganizationMembershipService organizationMembershipService;

	@Autowired
	GroupService groupService;

	@Autowired
	GroupMembershipService groupMembershipService;

	@Autowired
	UserPhoneNumberService userPhoneNumberService;

	// Method to the get all the User list in the 'Manage Users' tab
	@RequestMapping(value="/getUserList", method=RequestMethod.GET, produces = "application/json")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public List<UserManage> getUserList(@PathVariable String org) {

		List<UserManage> userrows = new ArrayList<UserManage>();

		Organization organization = organizationService.getOrganizationByAbbreviation(org);

		List<OrganizationMembership> membershipList = organizationMembershipService.getOrganizationMembershipList(organization);

		for(OrganizationMembership membership : membershipList)
		{
			User user = membership.getUser();

			// Get required attributes for each user
			int manageUserID = user.getUserId();
			String name = user.getName();
			String email = user.getEmail();
			String phone = userPhoneNumberService.getUserPrimaryPhoneNumber(user).getPhoneNumber();
			String role  = userService.getUserRole(user, organization);
			String address = user.getAddress();

			// Create the UserManage Object and add it to the list
			UserManage userrow = new UserManage(manageUserID, name, email, phone, role, address);
			userrows.add(userrow);
		}

		return userrows;
	}
	
	/*
	// Tests if the phone number already exists in the database or not
	@RequestMapping(value="/testPhoneNumber", method = RequestMethod.POST, produces = "text/plain")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String testPhoneNumber(@PathVariable String org, @RequestBody Map<String,String> newUserDetails) {

		Organization organization = organizationService.getOrganizationByAbbreviation(org);

		// Get the input parameters from AngularJS
		String phone = newUserDetails.get("phone");
		
		// Check if the phoneNumber is already present in the database
		if(userPhoneNumberService.findPreExistingPhoneNumber(phone))
		{
			return "-1";
		}
		else
			return "0";
		
	}*/

	// Method to add a new user according to the details entered in the Modal Dialog Box
	@RequestMapping(value="/addNewUser", method = RequestMethod.POST, produces = "application/json")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public UserManage addNewUser(@PathVariable String org, @RequestBody Map<String,String> newUserDetails) {

		Organization organization = organizationService.getOrganizationByAbbreviation(org);

		// Get the input parameters from AngularJS
		String name = newUserDetails.get("name");
		String email = newUserDetails.get("email");
		String phone = newUserDetails.get("phone");
		String role  = newUserDetails.get("role");
		String address = newUserDetails.get("address");

		// Variables to store the boolean values of the roles
		boolean isAdmin = false;
		boolean isPublisher = false;

		// Add the new User to database
		User user = new User(name, address, "en", "en", email);
		userService.addUser(user);

		// Add the Organization Membership for the user in the Database
		OrganizationMembership membership = new OrganizationMembership(organization, user, isAdmin, isPublisher);
		organizationMembershipService.addOrganizationMembership(membership);
		
		// Add the new user by default to parent group
		Group parentgroup = organizationService.getParentGroup(organization);
		GroupMembership groupMembership = new GroupMembership(parentgroup, user);
		groupMembershipService.addGroupMembership(groupMembership);

		// Add the Primary Phone number for the user in the database
		UserPhoneNumber primaryPhoneNumber = new UserPhoneNumber(user, phone, true);
		userPhoneNumberService.addUserPhoneNumber(primaryPhoneNumber);

		// Create the UserManage Object
		int manageUserID = user.getUserId();

		UserManage userrow = new UserManage(manageUserID, name, email, phone, role, address);

		// Finally return it as a JSON response body
		return userrow;
	}

	// It is assumed that the user does not have the role of admin or publisher
	@RequestMapping(value="/addUserRole", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public void addRole(@PathVariable String org, @RequestBody Map<String,String> userDetails) {

		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		int manageUserId = Integer.parseInt(userDetails.get("userid"));
		String addRole = userDetails.get("addRole");

		User user = userService.getUser(manageUserId);

		OrganizationMembership membership = organizationMembershipService.getUserOrganizationMembership(user, organization);

		if(addRole.equals("Admin"))
		{
			membership.setIsAdmin(true);
		}
		else if(addRole.equals("Publisher"))
		{
			membership.setIsPublisher(true);
		}
		else if(addRole.equals("Member"))
		{
			membership.setIsAdmin(false);
			membership.setIsPublisher(false);
		}

		// Finally make changes in the database
		organizationMembershipService.addOrganizationMembership(membership);
	}

	@RequestMapping(value="/removeUserRole", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public void removeRole(@PathVariable String org, @RequestBody Map<String,String> userDetails) {

		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		int manageUserId = Integer.parseInt(userDetails.get("userid"));
		String removeRole = userDetails.get("removeRole");

		User user = userService.getUser(manageUserId);
		OrganizationMembership membership = organizationMembershipService.getUserOrganizationMembership(user, organization);

		if(removeRole.equals("Admin"))
		{
			membership.setIsAdmin(false);
		}
		else if(removeRole.equals("Publisher"))
		{
			membership.setIsPublisher(false);
		}

		// Finally make changes in the database
		organizationMembershipService.addOrganizationMembership(membership);
	}

	// Method to add a new user according to the details entered in the Modal Dialog Box
	@RequestMapping(value="/editUser", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public void editUser(@PathVariable String org, @RequestBody Map<String,String> currentUserDetails) {

		// Get the input parameters from AngularJS
		int manageUserId = Integer.parseInt(currentUserDetails.get("userid"));
		String name = currentUserDetails.get("name");
		String email = currentUserDetails.get("email");
		String phone = currentUserDetails.get("phone");
		String address = currentUserDetails.get("address");

		// Add the new User to database
		User user = userService.getUser(manageUserId);

		user.setName(name);
		user.setEmail(email);
		user.setAddress(address);
		userService.addUser(user);

		// First Remove the Previous Primary Phone Number
		UserPhoneNumber previousPrimaryPhoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(user);
		userPhoneNumberService.removeUserPhoneNumber(previousPrimaryPhoneNumber);
		
		// Then add the new Primary number to the database
		UserPhoneNumber newPrimaryPhoneNumber = new UserPhoneNumber(user, phone, true);
		userPhoneNumberService.addUserPhoneNumber(newPrimaryPhoneNumber);
	}

	// Method to get user details in a Modal Dialog Box
	@RequestMapping(value="/getUserDetails", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public Map<String, Vector<String> > getUserDetails(@PathVariable String org, @RequestBody int manageUserId) {

		Organization organization = organizationService.getOrganizationByAbbreviation(org);

		Vector<String> userGroupNames = new Vector<String>(10,2);
		Vector<String> userPhoneNumbers = new Vector<String>(10,2);

		// Add the new User to database
		User user = userService.getUser(manageUserId);

		List<Group> userGroups = groupMembershipService.getGroupListByUserAndOrganization(user, organization);

		for(Group userGroup : userGroups)
		{
			userGroupNames.add(userGroup.getName());
		}

		// Add the Primary Phone number for the user in the database
		UserPhoneNumber primaryPhoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(user);
		userPhoneNumbers.add(primaryPhoneNumber.getPhoneNumber() + " (Primary)" );

		List<UserPhoneNumber> userSecondaryPhoneNumbers = userPhoneNumberService.getUserSecondaryPhoneNumbers(user);

		if(userSecondaryPhoneNumbers != null)
		{
			for(UserPhoneNumber userSecondaryPhoneNumber : userSecondaryPhoneNumbers)
			{
				userPhoneNumbers.add(userSecondaryPhoneNumber.getPhoneNumber());
			}
		}

		Map<String, Vector<String> > jsonbody = new HashMap<String, Vector<String> >();
		jsonbody.put("groups", userGroupNames);
		jsonbody.put("phoneNumbers", userPhoneNumbers);

		return jsonbody;
	}

}

