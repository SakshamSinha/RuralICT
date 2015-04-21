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
	UserPhoneNumberRepository userPhoneNumberRepository;
	@Autowired
	UserRepository userRepository;

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
		String role=null;
		List<OrganizationMembership> membershipList = organization.getOrganizationMemberships();

		List<UserRow> rows = new ArrayList<UserRow>();
		for (OrganizationMembership organizationMembership :membershipList) {

			UserPhoneNumber phoneNumber = userPhoneNumberRepository.findByUserAndPrimaryTrue(organizationMembership.getUser());

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

			UserRow row = new UserRow(organizationMembership.getUser(), phoneNumber , role );
			rows.add(row);

		}
		model.addAttribute("organizationMemberships",rows);
		return "users";
	}


}