package app.business.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.UserPhoneNumberRepository;
import app.data.repositories.UserRepository;
import app.entities.Group;
import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.User;
import app.util.Utils;

/*
 * This service will cater all controllers to fetch user related data.
 */

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	OrganizationMembershipService organizationMembershipService;
	
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
	 * Service layer function to get details of current user object.
	 */
	public User getCurrentUser(){
		return userRepository.findOne(Utils.getSecurityPrincipal().getUserId());
	}
	
	/*
	 * Decide on the role that user plays.
	 */
	public String getUserRole(User user, Organization organization){
		
		String role = null;
		
		OrganizationMembership organizationMembership = organizationMembershipService.getUserOrganizationMembership(user,organization);
		
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

	/*
	 * add a user to database
	 */
	public void addUser(User user) {
			
		userRepository.save(user);		
	}
		
	/*
	 * delete a user from database
	 */
	public void removeUser(User user) {
			
			userRepository.delete(user);
		
	}
	
	/*
	 * Get UserPhoneNumber object by phoneNumber
	 */
	public User getUser(int userId) {
		
		return userRepository.findOne(userId);
	}
	
	/*
	 * Get all users from database
	 */
	public List<User> getAllUserList() {
		
		return userRepository.findAll();
	}
	
	/*
	 * Returns userId of the user
	 */
	public int getUserId(User user) {
		
		return user.getUserId();
	}
}
