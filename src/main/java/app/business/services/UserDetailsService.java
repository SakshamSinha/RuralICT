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
public class UserDetailsService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserPhoneNumberService userPhoneNumberService;
	
	@Autowired
	OrganizationService organizationService;
	
	/*
	 * A Row class declared to return data to controller in nicer format
	 */
	static public class UserDetails {
		private User user;
		private UserPhoneNumber phone;

		private String role;
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public UserDetails(User user, UserPhoneNumber primaryPhoneNo , String role) {
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
	public List<UserDetails> getUserDetailsByOrganization(String org){
		
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		String role=null;
		
		List<OrganizationMembership> membershipList = organizationService.getOrganizationMembershipList(organization);
		
		List<UserDetails> rows = new ArrayList<UserDetails>();
		
		for (OrganizationMembership organizationMembership : membershipList) {
			User user = organizationMembership.getUser();
			UserPhoneNumber phoneNumber = userPhoneNumberService.getUserPhoneNumberByPrimaryTrue(user);
			role = userService.getUserRole(user,organization);
			
			UserDetails row = new UserDetails(organizationMembership.getUser(), phoneNumber , role );
			rows.add(row);
		}
		
		
		return rows;
	}
	
	public void addUserDetails(UserDetails userDetails) {

			userService.addUser(userDetails.getUser());
			userPhoneNumberService.addUserPhoneNumber(userDetails.getPhone());
	}
	
	public void removeUserDetails(UserDetails userDetails) {

			userService.removeUser(userDetails.getUser());
			userPhoneNumberService.removeUserPhoneNumber(userDetails.getPhone());
	}
	
	/*
	 * Returns UserDetails for a given pair of user and organization
	 */
	public UserDetails getUserDetails(User user, Organization organization) {
		return new UserDetails(user, userPhoneNumberService.getUserPhoneNumberByPrimaryTrue(user), userService.getUserRole(user,organization));
	} 
	 
	/*
	 * Returns UserDetails for a given user
	 */
	public UserDetails getUserDetails(User user) {
		return new UserDetails(user, userPhoneNumberService.getUserPhoneNumberByPrimaryTrue(user), "");
	}
}
