package app.business.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.UserPhoneNumberRepository;
import app.data.repositories.UserRepository;
import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.User;
import app.entities.UserPhoneNumber;
import app.util.Utils;

/*
 * This service will cater all controllers to fetch user related data.
 */

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserPhoneNumberRepository userPhoneNumberRepository;
	
	
	/*
	 * Method to get organization memberships of user.
	 * Written currently just to help getAdminMembership method 
	 * to fetch data from current layer itself 
	 */
	public List<OrganizationMembership> getUserMembershipList(User user){
		
		List<OrganizationMembership> userMemberships = user.getOrganizationMemberships();
		return userMemberships;
	}
	
	/*
	 * Method to get list of user memberships where user is an admin.
	 */
	public List<OrganizationMembership> getAdminMembershipList(User user){
		
		List<OrganizationMembership> adminMemberships = new ArrayList<OrganizationMembership>();
		
		for (OrganizationMembership userMembership : this.getUserMembershipList(user)) {
			
			if (userMembership.getIsAdmin()){
				
				adminMemberships.add(userMembership);
			
			}
		}
		return adminMemberships;
	}
	
	/*
	 * Method to get list of user memberships for particular organization.
	 */
	public OrganizationMembership getUserMembership(User user, Organization organization){
		
		for (OrganizationMembership userMembership : this.getUserMembershipList(user)) {
			
			if (userMembership.getOrganization().equals(organization)){
				
				return userMembership;
			
			}
		}
		return null;
	}
	
	/*
	 * Service layer function to get details of current user object.
	 */
	public User getCurrentUser(){
		return Utils.getCurrentUser(userRepository);
	}
	
	/*
	 * Get user phone number only if primary is set as true.
	 */
	public UserPhoneNumber getUserPhoneNumberByPrimaryTrue(User user){
		
		return userPhoneNumberRepository.findByUserAndPrimaryTrue(user);
	}
	
	/*
	 * Decide on the role that user plays.
	 */
	public String getUserRole(User user, Organization organization){
		
		String role = null;
		
		OrganizationMembership organizationMembership = this.getUserMembership(user,organization);
		
		if(organizationMembership.getIsAdmin()==true && organizationMembership.getIsPublisher()==false){
			role="Admin";
		}
		else if(organizationMembership.getIsPublisher()==true && organizationMembership.getIsAdmin()==false){
			role="Publisher";
		}
		else if(organizationMembership.getIsAdmin()==false && organizationMembership.getIsPublisher()==false){
			role="User";
		}
		else if(organizationMembership.getIsPublisher()==true && organizationMembership.getIsAdmin()==true){
			role="Member Publisher";
		}
		
		return role;
	}
}
