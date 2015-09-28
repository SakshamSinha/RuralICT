package app.business.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import in.ac.iitb.ivrs.telephony.base.util.IVRUtils;
import app.business.services.GroupMembershipService;
import app.business.services.OrganizationMembershipService;
import app.business.services.OrganizationService;
import app.business.services.UserService;
import app.entities.GroupMembership;
import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.User;

@Controller
@RequestMapping("/web/{org}")
public class HomeController {

	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	OrganizationMembershipService organizationMembershipService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	GroupMembershipService groupMembershipService;
	
	@RequestMapping(value="/homePage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String homePage(@PathVariable String org, Model model) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		model.addAttribute("organization",organization);
		return "home";
	}
	@RequestMapping(value="/homePage/approve", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	@ResponseBody
	public void approveUser(@PathVariable String org, @RequestBody Map<String,String> userDetails) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		int userId = Integer.parseInt(userDetails.get("userid"));
		String phoneno= userDetails.get("phno");
		User user = userService.getUser(userId);
		OrganizationMembership membership= organizationMembershipService.getUserOrganizationMembership(user, organization);
		membership.setStatus(1);
		organizationMembershipService.addOrganizationMembership(membership);
		IVRUtils.sendSMS(phoneno, "Congratualtions!!! You have been approved by "+organization.getName()+" organization.",null,null);
	}
	
	@RequestMapping(value="/homePage/reject", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	@ResponseBody
	public void rejectUser(@PathVariable String org, @RequestBody Map<String,String> userDetails) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		int userId = Integer.parseInt(userDetails.get("userid"));
		String phoneno= userDetails.get("phno");
		User user = userService.getUser(userId);
		for(GroupMembership groupMembership: user.getGroupMemberships()) {
			if(groupMembership.getGroup().getOrganization().getName().equals(organization.getName()))
				groupMembershipService.removeGroupMembership(groupMembership);
		}
		OrganizationMembership organizationMembership= organizationMembershipService.getUserOrganizationMembership(user, organization);
		organizationMembershipService.removeOrganizationMembership(organizationMembership);
	}
}
