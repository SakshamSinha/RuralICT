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
public class UserRowService {
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrganizationService organizationService;
	
	/*
	 * A Row class declared to return data to controller in nicer format
	 */
	static public class UserRow {
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
	public List<UserRow> getUserRowForOrganization(String org){
		
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		String role=null;
		
		List<OrganizationMembership> membershipList = organizationService.getOrganizationMembershipList(organization);
		
		List<UserRow> rows = new ArrayList<UserRow>();
		
		for (OrganizationMembership organizationMembership : membershipList) {
			User user = organizationMembership.getUser();
			UserPhoneNumber phoneNumber = userService.getUserPhoneNumberByPrimaryTrue(user);
			role = userService.getUserRole(user,organization);
			
			UserRow row = new UserRow(organizationMembership.getUser(), phoneNumber , role );
			rows.add(row);
		}
		
		
		return rows;
	}
}
