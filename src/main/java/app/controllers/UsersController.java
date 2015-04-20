package app.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.User;
import app.entities.UserPhoneNumber;
import app.rest.repositories.OrganizationRepository;
import app.rest.repositories.UserPhoneNumberRepository;
import app.rest.repositories.UserRepository;

@Controller
@RequestMapping("/web/{org}")
public class UsersController {
	
	@Autowired
	OrganizationRepository organizationRepository;

	@Autowired
	UserPhoneNumberRepository userphonenumberrepository;
	@Autowired
	UserRepository userrepository;
	
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

	
	@RequestMapping(value="/usersPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String usersPage(@PathVariable String org, Model model) {
					
			Organization organization = organizationRepository.findByAbbreviation(org);
			System.out.println( organization.getName());
			String admin=null;
			List<OrganizationMembership> getOrganizationMemberships = organization.getOrganizationMemberships();
		
			List<UserRow> phoneNumber = new ArrayList<UserRow>();
			for (OrganizationMembership OrganizationMembership :getOrganizationMemberships) {
				
					UserPhoneNumber users = userphonenumberrepository.findByUserAndPrimaryTrue(OrganizationMembership.getUser());
	       			System.out.println(OrganizationMembership.getIsAdmin()==true && OrganizationMembership.getIsPublisher()==false);
	       			if(OrganizationMembership.getIsAdmin()==true && OrganizationMembership.getIsPublisher()==false){
	       				 admin="Admin";
	       			}
	       			else if(OrganizationMembership.getIsPublisher()==true && OrganizationMembership.getIsAdmin()==false){
	       				admin="Publisher";
	       			}
	       			else if(OrganizationMembership.getIsAdmin()==false && OrganizationMembership.getIsPublisher()==false){
	       				 admin="User";
	       			}
	       			else if(OrganizationMembership.getIsPublisher()==true && OrganizationMembership.getIsAdmin()==true){
	       				admin="Member Publisher";
	       			}
	       			System.out.println(OrganizationMembership.getOrganization());
	       			UserRow row = new UserRow(OrganizationMembership.getUser(), users , admin );
	       			phoneNumber.add(row);
	       			
				}
			model.addAttribute("organizationmemberships",phoneNumber);
			
	return "users";
	}
	
	
}