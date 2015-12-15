package app.business.controllers;

import in.ac.iitb.ivrs.telephony.base.util.IVRUtils;

import java.sql.Timestamp;
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

import app.business.services.BroadcastDefaultSettingsService;
import app.business.services.BroadcastRecipientService;
import app.business.services.BroadcastScheduleService;
import app.business.services.GroupMembershipService;
import app.business.services.GroupService;
import app.business.services.LatestRecordedVoiceService;
import app.business.services.OrganizationMembershipService;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.VoiceService;
import app.business.services.broadcast.BroadcastService;
import app.data.repositories.UserRepository;
import app.entities.BroadcastDefaultSettings;
import app.entities.BroadcastRecipient;
import app.entities.BroadcastSchedule;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.LatestRecordedVoice;
import app.entities.Organization;
import app.entities.User;
import app.entities.UserPhoneNumber;
import app.entities.Voice;
import app.entities.broadcast.VoiceBroadcast;
import app.telephony.config.Configs;
import app.util.Utils;


@Controller
@RequestMapping("/web/{org}")
public class BroadcastVoiceController {

	@Autowired
	OrganizationService organizationService;
	@Autowired
	OrganizationMembershipService organizationMembershipService;	
	@Autowired
	GroupService groupService;
	@Autowired
	GroupMembershipService groupMembershipService;
	@Autowired
	UserService userService;
	@Autowired
	UserPhoneNumberService userPhoneNumberService;
	@Autowired
	VoiceService voiceService;
	@Autowired
	BroadcastService broadcastService;
	@Autowired
	BroadcastScheduleService broadcastScheduleService;
	@Autowired
	BroadcastRecipientService broadcastRecipientService;
	@Autowired
	BroadcastDefaultSettingsService broadcastDefaultSettingService;
	@Autowired
	LatestRecordedVoiceService latestRecordedVoiceService;
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value="/broadcastVoiceMessages/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String broadcastVoiceMessages(@PathVariable String org, @PathVariable int groupId, Model model) {

		Group group = groupService.getGroup(groupId);
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		User publisher = userService.getCurrentUser();
		
		
		List<GroupMembership> groupMembershipList = new ArrayList<GroupMembership>(groupMembershipService.getGroupMembershipListByGroupSortedByUserName(group));
		
		//called latest recorded voice according to time
		LatestRecordedVoice broadcast = latestRecordedVoiceService.getLatestRecordedVoiceByOrganization(organization);
		model.addAttribute("broadcast", broadcast);
		
		List<User> users = new ArrayList<User>();
		for(GroupMembership groupMembership : groupMembershipList) {
			User user=groupMembership.getUser();
			int status=organizationMembershipService.getOrganizationMembershipStatus(user, organization);
			if(status==1)
				users.add(groupMembership.getUser());
		}
		
		BroadcastDefaultSettings broadcastDefaultSettings = broadcastDefaultSettingService.getBroadcastDefaultSettingByOrganization(organization);
		
		model.addAttribute("users",users);
		model.addAttribute("organization",organization);
		model.addAttribute("group",group);
		model.addAttribute("publisher",publisher);
		model.addAttribute("broadcastDefaultSettings", broadcastDefaultSettings);
		
		//TODO Ask what to do when user is not a publisher do we prevent it on UI side.
		String role = userService.getUserRole(publisher, organization);
		model.addAttribute("role", role);
		
		return "broadcastVoice";
	}
	
	@RequestMapping(value="/broadcastDefaultSettings")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String broadcastDefaultSettings(@PathVariable String org,  Model model){
		
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		model.addAttribute("organization", organization);
		return "broadcastDefaultSettings";
	}
	
	@RequestMapping(value = "/broadcastVoiceMessages/{groupId}", method = RequestMethod.POST,produces="application/json")
	@ResponseBody
	public HashMap<String,String> logs(@RequestBody Map<String,String> body) {
		Organization organization = organizationService.getOrganizationById(Integer.parseInt(body.get("organizationId")));
		Group group = groupService.getGroup(Integer.parseInt(body.get("groupId")));
		User publisher = userService.getUser(Integer.parseInt(body.get("publisherId")));
		HashMap<String,String> response= new HashMap<String,String>();
		int voicebroadcastlimit=publisher.getVoicebroadcastlimit();
		String[] broadcastRecipentsPrior=body.get("userIds").split(",");
		if(voicebroadcastlimit>=0)
		{
			if(voicebroadcastlimit==0)
			{
				response.put("status", "error");
				response.put("cause","BroadcastExhausted");
				return response;
			}
			else if(broadcastRecipentsPrior.length>voicebroadcastlimit)
			{
				response.put("status", "error");
				response.put("cause","LimitExceeded");
				response.put("broadcast", Integer.toString(voicebroadcastlimit));
				return response;
			}
		}
		response.put("status", "success");
		response.put("cause","Broadcast Successful");
		publisher.setVoicebroadcastlimit(publisher.getVoicebroadcastlimit()-broadcastRecipentsPrior.length);
		String mode = body.get("mode");
		//Converting string to integer and converting to boolean
		boolean askOrder = (Integer.parseInt(body.get("askOrder")) !=0);
		boolean askFeedback = (Integer.parseInt(body.get("askFeedback")) !=0);
		boolean askResponse = (Integer.parseInt(body.get("askResponse")) !=0);
		
		String broadcastedTime = body.get("broadcastedTime");
		Timestamp timestamp = Timestamp.valueOf(broadcastedTime);
		
		boolean appOnly = (Integer.parseInt(body.get("appOnly")) !=0);
		Voice voice = voiceService.getVoice(Integer.parseInt(body.get("voiceId")));
		String voiceUrl = voice.getUrl();
		boolean voiceBroadcastDraft = (Integer.parseInt(body.get("voiceBroadcastDraft")) !=0);
		
		/*
		 * Broadcast, Broadcast Recipient and Broadcast Schedule will be added
		 * here in this module even when scheduling will be done from separate thread.
		*/
		//Adding Broadcast to the Broadcast table
		VoiceBroadcast broadcast = new VoiceBroadcast(organization, group, publisher, mode, askFeedback,  askOrder, askResponse, appOnly, voice, voiceBroadcastDraft);
		broadcastService.addBroadcast(broadcast);
		//Adding Broadcast Recipient to Broadcast Recipients table
		String userIdString = body.get("userIds");
		String[] userIdList = userIdString.split(",");
		List<BroadcastRecipient> broadcastRecipients = new ArrayList<BroadcastRecipient>();
		for(int i=0 ; i<userIdList.length;i++)
		{	
			
			User user = userService.getUser(Integer.parseInt(userIdList[i]));
			BroadcastRecipient broadcastRecipient = new BroadcastRecipient(broadcast, user);
			broadcastRecipients.add(broadcastRecipient);
			broadcastRecipientService.addBroadcastRecipient(broadcastRecipient);
		}
		
		/*
		 * TODO Updation of time and call to each Broadcast Recipient needs to be done from separate thread 
		*/
		java.util.Date date= new java.util.Date();
		Timestamp currentTimestamp= new Timestamp(date.getTime());
		broadcast.setBroadcastedTime(currentTimestamp);
		
		//Adding Broadcast schedule 
		//TODO set the time at which you have actually send the schedule and set the send to all field as well.
		BroadcastSchedule broadcastSchedule = new BroadcastSchedule(broadcast, currentTimestamp, false);
		broadcastScheduleService.addBroadcastSchedule(broadcastSchedule);
		
		//Calling each of the Broadcast Recipient
		for(BroadcastRecipient recipient: broadcastRecipients)
		{
			User user=recipient.getUser();
			System.out.println("Broadcast Recipient:"+user.getName());
			UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(user);
			String phoneNumber = userPhoneNumber.getPhoneNumber().substring(2);
			IVRUtils.makeOutboundCall(phoneNumber, organization.getIvrNumber(), Configs.Telephony.OUTBOUND_APP_URL);
		}
		userRepository.save(publisher);
		return response;
	}
	
	@RequestMapping(value = "/latestBroadcastVoiceMessages/{groupId}", method = RequestMethod.POST)
	@ResponseBody
	public void latestRecordedLogs(@RequestBody Map<String,String> body) {
		System.out.println("The json body has been received from uploader in Angular js part"+body);
		Organization organization = organizationService.getOrganizationById(Integer.parseInt(body.get("organizationId")));
		Voice voice = voiceService.getVoice(Integer.parseInt(body.get("voiceId")));
		
		latestRecordedVoiceService.updateLatestRecordedVoice(organization, voice);
	}
	
	@RequestMapping(value="/voicebroadcastsleft",method=RequestMethod.GET,produces="text/plain")
	@ResponseBody
	public String voicebroadcastsLeft() {
		User publisher = Utils.getCurrentUser(userRepository);
		
		int voicebroadcastlimit=publisher.getVoicebroadcastlimit();
		if(voicebroadcastlimit<=-1)
		{
			System.out.println("Returning unlimited voicebroadcastsleft");
			return "Unlimited";
		}
		return new Integer(voicebroadcastlimit).toString();
	}
	
}
