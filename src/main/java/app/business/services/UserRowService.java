package app.business.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entities.User;
import app.entities.UserPhoneNumber;

/*
 * This service is separated from organization service for simple reason that the former approach 
 * will serve nothing but addition or complexity to the class. Moreover this service derives data 
 * from three different repos which makes managing of code little cumbersome. 
 */

@Service
public class UserRowService {
	
	@Autowired
	UserService userService;
	
	/*
	  
	 Coming Soon
	 
	@Autowired
	OrganizationService organizationService;
	
	*/
	
	/*
	 * A Row class declared to return data to controller in nicer format
	 */
	static private class UserRow {
		private User user;
		private UserPhoneNumber phone;

		private String role;
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public UserRow(User user, UserPhoneNumber primaryPhoneNo , String role) {
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
	public List<UserRow> getUserRowForOrg(String org){
		
		/*
		 
		Coming Soon 
		Organization organization = organizationService.getOrganizationByAbbr(org);
		
		*/
		
		String role=null;
		
		/* 
		 
		Coming Soon
		List<OrganizationMembership> membershipList = organization.getOrganizationMembershipList(organization);
		int organizationId = organizationService.getOrganizationId(organization);
		
		*/
		
		List<UserRow> rows = new ArrayList<UserRow>();
		
		
		/*
		for (OrganizationMembership organizationMembership : membershipList) {
			User user = organizationMembership.getUser();
			UserPhoneNumber phoneNumber = userService.getUserPhoneNumberByPrimaryTrue(user);
			role = userService.getRole(user,organizationId);
			
			UserRow row = new UserRow(organizationMembership.getUser(), phoneNumber , role );
			rows.add(row);
		}
		*/
		
		return rows;
	}
}
