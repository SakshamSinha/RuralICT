package app.business.controllers;

import in.ac.iitb.ivrs.telephony.base.util.IVRUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import app.business.services.BroadcastRecipientService;
import app.business.services.GroupMembershipService;
import app.business.services.GroupService;
import app.business.services.OrganizationMembershipService;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.broadcast.BroadcastService;
import app.data.repositories.UserRepository;
import app.entities.BroadcastRecipient;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.Organization;
import app.entities.User;
import app.entities.UserPhoneNumber;
import app.entities.broadcast.TextBroadcast;
import app.util.Utils;

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
	
	@Autowired
	OrganizationMembershipService organizationMembershipService;

	private int sendSMS;
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value="/textBroadcast/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String groupPage(@PathVariable String org, @PathVariable int groupId, Model model) {
		Group group = groupService.getGroup(groupId);
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		User pubuser = userService.getCurrentUser();
		
		List<GroupMembership> groupMembershipList = new ArrayList<GroupMembership>(groupMembershipService.getGroupMembershipListByGroupSortedByUserName(group));
		
		List<User> users = new ArrayList<User>();
		for(GroupMembership groupMembership : groupMembershipList) {
			User user=groupMembership.getUser();
			int status=organizationMembershipService.getOrganizationMembershipStatus(user, organization);
			if(status==1)
				users.add(groupMembership.getUser());
		}
		
		model.addAttribute("users",users);
		model.addAttribute("publisher",pubuser);
		model.addAttribute("group", group);
		model.addAttribute("organization", organization);
		
		return "textBroadcast";
	}
	
	@RequestMapping(value = "/textBroadcast/create/{groupId}", method = {RequestMethod.POST})
	@ResponseBody
	@Transactional
	public HashMap<String, String> createBroadcast(@RequestBody Map<String,String> body) {
		
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
		
		HashMap<String,String> response= new HashMap<String,String>();
		int textbroadcastlimit=publisher.getTextbroadcastlimit();
		String[] broadcastRecipentsPrior=body.get("userIds").split(",");
		if(textbroadcastlimit>=0)
		{
			if(textbroadcastlimit==0)
			{
				response.put("status", "error");
				response.put("cause","BroadcastExhausted");
				return response;
			}
			else if(broadcastRecipentsPrior.length>textbroadcastlimit)
			{
				response.put("status", "error");
				response.put("cause","LimitExceeded");
				response.put("broadcast", Integer.toString(textbroadcastlimit));
				return response;
			}
		}
		response.put("status", "success");
		response.put("cause","Broadcast Successful");
		publisher.setTextbroadcastlimit(publisher.getTextbroadcastlimit()-broadcastRecipentsPrior.length);
		
		// Create a new Text Broadcast and add it to the database
		TextBroadcast broadcast = new TextBroadcast(organization, group, publisher, mode, askFeedback,  askOrder, askResponse, appOnly, textContent);
		broadcastService.addBroadcast(broadcast);
		 
		// Get the Broadcast Recipients
		String userIdString = body.get("userIds");
		String[] userIdList = userIdString.split(",");
		
		// Parse the string containing User Id of the Recipients and send SMS to them
		for(int i=0 ; i<userIdList.length;i++)
		{	
			User user = userService.getUser(Integer.parseInt(userIdList[i]));
			BroadcastRecipient recipientUser = new BroadcastRecipient(broadcast, user);
			broadcastRecipientService.addBroadcastRecipient(recipientUser);
			UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(user);
			
			// Call the SendSMS function from IVRUtils
			try {
				IVRUtils.sendSMS(userPhoneNumber.getPhoneNumber(), textContent, organization.getIncomingSmsCode(), null);
			} 
			catch (Exception e) {
				e.printStackTrace();
				//return -1;
			}
		}
		
		// Get the current Timestamp
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());  
		 
		// Set the Timestamp in the broadcast table after sending SMS to all the recipients
		broadcastService.setBroadcastTime(timestamp, broadcast);
		userRepository.save(publisher);
		
		return response;
		//return 0;
		
	}
	
	@RequestMapping(value="/textbroadcastsleft",method=RequestMethod.GET,produces="text/plain")
	@ResponseBody
	public String voicebroadcastsLeft() {
		User publisher = Utils.getCurrentUser(userRepository);
		int textbroadcastlimit=publisher.getTextbroadcastlimit();
		
		if(textbroadcastlimit<=-1)
			return "Unlimited";
		return new Integer(textbroadcastlimit).toString();
	}
}