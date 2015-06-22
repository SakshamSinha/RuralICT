package app.business.controllers;

import in.ac.iitb.ivrs.telephony.base.util.IVRUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import app.util.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.business.services.BroadcastRecipientService;
import app.business.services.GroupMembershipService;
import app.business.services.GroupService;
import app.business.services.OrganizationMembershipService;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.broadcast.BroadcastService;
import app.entities.BroadcastRecipient;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.User;
import app.entities.UserPhoneNumber;
import app.entities.broadcast.Broadcast;
import app.entities.broadcast.TextBroadcast;

@Controller
@RequestMapping("/web/{org}")
public class TextBroadcastController {
	
	@Autowired
	BroadcastRecipientService broadcastRecipientService;
	
	@Autowired
	BroadcastService broadcastService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserPhoneNumberService userPhoneNumberService;
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	GroupMembershipService groupMembershipService;
	
	@RequestMapping(value="/textBroadcast/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String groupPage(@PathVariable String org, @PathVariable int groupId, Model model) {
		Group group = groupService.getGroup(groupId);
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		User user = userService.getCurrentUser();
		
		List<GroupMembership> groupMembershipList = groupMembershipService.getGroupMembershipListByGroup(group);
		List<User> users = new ArrayList<User>();
		
		for(GroupMembership groupMembership : groupMembershipList) {
			users.add(groupMembership.getUser());
		}
		
		model.addAttribute("users",users);
		model.addAttribute("publisher",user);
		model.addAttribute("group", group);
		model.addAttribute("organization", organization);
		
		return "textBroadcast";
	}
	
	@RequestMapping(value = "/textBroadcast/create/{groupId}", method = {RequestMethod.POST})
	@ResponseBody
	@Transactional
	public void createBroadcast(@RequestBody Map<String,String> body) {
		
		// Get the required variables from the Broadcast JSON object passed through Angular JS
		Organization organization = organizationService.getOrganizationById(Integer.parseInt(body.get("organization")));
		Group group = groupService.getGroup(Integer.parseInt(body.get("group")));
		User publisher = userService.getUser(Integer.parseInt(body.get("publisher")));
		String mode = body.get("mode");
		boolean askOrder = Boolean.parseBoolean(body.get("askOrder"));
		boolean askFeedback = Boolean.parseBoolean(body.get("askFeedback"));
		boolean askResponse = Boolean.parseBoolean(body.get("askResponse"));
		boolean appOnly = Boolean.parseBoolean(body.get("appOnly"));
		String textContent = body.get("textContent");
		
		
		// Create a new Text Broadcast and add it to the database
		TextBroadcast broadcast = new TextBroadcast(organization, group, publisher, mode, askFeedback,  askOrder, askResponse, appOnly, textContent);
		broadcastService.addBroadcast(broadcast);
		 
		// Get the Broadcast Recipients
		String userIdString = body.get("userIds");
		String[] userIdList = userIdString.split(",");
		
		// Parse the string containing User Id of the Recipients and send SMS to them
		for(int i=0 ; i<userIdList.length;i++)
		{	
			System.out.println(userIdList[i]);
			User user = userService.getUser(Integer.parseInt(userIdList[i]));
			BroadcastRecipient recipientUser = new BroadcastRecipient(broadcast, user);
			broadcastRecipientService.addBroadcastRecipient(recipientUser);
			UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(user);
			
			// Call the SendSMS function from IVRUtils
			try {
				IVRUtils.sendSMS(userPhoneNumber.getPhoneNumber(),textContent);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Get the current Timestamp
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());  
		 
		// Set the Timestamp in the broadcast table after sending SMS to all the recipients
		broadcastService.setBroadcastTime(timestamp, broadcast);
		
	}
}