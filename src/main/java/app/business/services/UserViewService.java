package app.business.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.User;
import app.entities.UserPhoneNumber;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
 * This service is separated from organization service for simple reason that the former approach 
 * will serve nothing but addition or complexity to the class. Moreover this service derives data 
 * from three different repos which makes managing of code little cumbersome. 
 */



@Service
public class UserViewService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserPhoneNumberService userPhoneNumberService;
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	GroupMembershipService groupMembershipService;
	
	@Autowired
	OrganizationMembershipService organizationMembershipService;
	
	@Autowired
	GroupService groupService;
	
	/*
	 * A Row class declared to return data to controller in nicer format
	 */
	public static class UserView {
		private User user;
		private UserPhoneNumber phone;

		private String role;
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		
		@JsonCreator
		public UserView(@JsonProperty("user") User user, @JsonProperty("phone") UserPhoneNumber phone , @JsonProperty("role") String role) {
			this.user = user;
			this.phone = phone;
			this.role=role;
		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public UserPhoneNumber getPhone() {
			return phone;
		}
		public void setPhone(UserPhoneNumber phone) {
			this.phone = phone;

		}
	}
	
	/*
	 * This method generates the list of user rows for a particular organization  
	 */
	public List<UserView> getUserViewListByOrganization(String org){
		
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		String role=null;
		
		List<OrganizationMembership> membershipList = organizationService.getOrganizationMembershipList(organization);
		
		List<UserView> rows = new ArrayList<UserView>();
		
		for (OrganizationMembership organizationMembership : membershipList) {
			User user = organizationMembership.getUser();
			UserPhoneNumber phoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(user);
			role = userService.getUserRole(user,organization);
			
			UserView row = new UserView(organizationMembership.getUser(), phoneNumber , role );
			rows.add(row);
		}
		
		
		return rows;
	}
	
	public List<UserView> getUserViewListByGroup(int groupId){
		
		Group group = groupService.getGroup(groupId);
		String role=null;
		
		List<GroupMembership> membershipList = group.getGroupMemberships();
		
		List<UserView> rows = new ArrayList<UserView>();
		
		for (GroupMembership groupMembership : membershipList) {
			User user = groupMembership.getUser();
			UserPhoneNumber phoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(user);
			role = userService.getUserRole(user,group.getOrganization());
			
			UserView row = new UserView(groupMembership.getUser(), phoneNumber , role );
			rows.add(row);
		}
		
		
		return rows;
	}
	
	@Transactional
	public UserView addUserView(UserView userView) {
			UserPhoneNumber userPhoneNumber = userView.getPhone();
			
			/**
			 * Just to check if userPhoneNumber doesn't exist
			 */
			if(userPhoneNumberService.getUserPhoneNumber(userPhoneNumber.getPhoneNumber())==null) {
				User user = userService.addUser(userView.getUser());
				userPhoneNumber.setUser(user);
				userPhoneNumberService.addUserPhoneNumber(userPhoneNumber);
				return (new UserView(user, userPhoneNumber, userView.getRole()));
			}			
			return null;
	}
	
	@Transactional
	public void removeUserView(UserView userView) {
		
			userPhoneNumberService.removeUserPhoneNumber(userView.getUser());
			for(GroupMembership groupMembership: userView.getUser().getGroupMemberships()) {
				groupMembershipService.removeGroupMembership(groupMembership);
			}
			for(OrganizationMembership organizationMembership: userView.getUser().getOrganizationMemberships()) {
				organizationMembershipService.removeOrganizationMembership(organizationMembership);
			}
			userService.removeUser(userView.getUser());
			
	}
	
	/*
	 * Returns UserView for a given pair of user and organization
	 */
	public UserView getUserView(User user, Organization organization) {
		return new UserView(user, userPhoneNumberService.getUserPrimaryPhoneNumber(user), userService.getUserRole(user,organization));
	} 
	 
	/*
	 * Returns UserView for a given user
	 */
	public UserView getUserView(User user) {
		return new UserView(user, userPhoneNumberService.getUserPrimaryPhoneNumber(user), "");
	}
}