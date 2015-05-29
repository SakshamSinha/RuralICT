package app.business.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.User;
import app.entities.UserPhoneNumber;

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
		public UserView(User user, UserPhoneNumber primaryPhoneNo , String role) {
			this.user = user;
			this.phone = primaryPhoneNo;
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
	public List<UserView> getUserViewByOrganization(String org){
		
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
	
	public void addUserView(UserView userView) {

			userService.addUser(userView.getUser());
			userPhoneNumberService.addUserPhoneNumber(userView.getPhone());
	}
	
	public void removeUserView(UserView userView) {

			userService.removeUser(userView.getUser());
			userPhoneNumberService.removeUserPhoneNumber(userView.getPhone());
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
