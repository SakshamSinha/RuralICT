package app.business.controllers;

import in.ac.iitb.ivrs.telephony.base.util.IVRUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import app.business.services.OrganizationService;
import app.business.services.UserService;
import app.business.services.VoiceService;
import app.business.services.broadcast.BroadcastService;
import app.data.repositories.LatestBroadcastableVoiceRepository;
import app.entities.BroadcastRecipient;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.LatestBroadcastableVoice;
import app.entities.Organization;
import app.entities.User;
import app.entities.UserPhoneNumber;
import app.entities.Voice;
import app.entities.broadcast.Broadcast;
import app.entities.broadcast.VoiceBroadcast;
import app.telephony.config.Configs;


@Controller
@RequestMapping("/web/{org}")
public class BroadcastVoiceController {

	@Autowired
	OrganizationService organizationService;
	@Autowired
	GroupService groupService;
	@Autowired
	GroupMembershipService groupMembershipService;
	@Autowired
	UserService userService;
	@Autowired
	VoiceService voiceService;
	@Autowired
	BroadcastService broadcastService;
	@Autowired
	BroadcastRecipientService broadcastRecipientService;
	@Autowired
	LatestBroadcastableVoiceRepository latestBroadcastableVoiceRepository; 

	@RequestMapping(value="/broadcastVoiceMessages/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String broadcastVoiceMessages(@PathVariable String org, @PathVariable int groupId, Model model) {

		Group group = groupService.getGroup(groupId);
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		User publisher = userService.getCurrentUser();
		
		//Ask what to do when user is not a publisher do we prevent it on UI side.
		String role = userService.getUserRole(publisher, organization);
		List<GroupMembership> groupMembershipList = groupMembershipService.getGroupMembershipListByGroup(group);
		
		//TODO change repository to service
		LatestBroadcastableVoice broadcast = latestBroadcastableVoiceRepository.findTopByOrganizationAndGroupOrderByTimeDesc(organization, group);
		model.addAttribute("broadcast", broadcast);
		
		List<User> users = new ArrayList<User>();
		for(GroupMembership groupMembership : groupMembershipList) {
			users.add(groupMembership.getUser());
		}
		
		model.addAttribute("users",users);
		model.addAttribute("organization",organization);
		model.addAttribute("group",group);
		model.addAttribute("publisher",publisher);
		model.addAttribute("role", role);
		
		return "broadcastVoice";
	}
	
	@RequestMapping(value = "/broadcastVoiceMessages/{groupId}", method = RequestMethod.POST)
	@ResponseBody
	public void logs(@RequestBody Map<String,String> body) {
	    System.out.println("We have received the body"+body);
	    Organization organization = organizationService.getOrganizationById(Integer.parseInt(body.get("organizationId")));
	    Group group = groupService.getGroup(Integer.parseInt(body.get("groupId")));
	    User publisher = userService.getUser(Integer.parseInt(body.get("publisherId")));
	    String mode = body.get("mode");
	    //Converting string to integer and converting to boolean
	    boolean askOrder = (Integer.parseInt(body.get("askOrder")) !=0);
	    boolean askFeedback = (Integer.parseInt(body.get("askFeedback")) !=0);;
	    boolean askResponse = (Integer.parseInt(body.get("askResponse")) !=0);
	    
	    String broadcastedTime = body.get("broadcastedTime");
	    System.out.println(broadcastedTime);
	    
	    boolean appOnly = (Integer.parseInt(body.get("appOnly")) !=0);
	    Voice voice = voiceService.getVoice(Integer.parseInt(body.get("voiceId")));
	    String voiceUrl = voice.getUrl();
	    boolean voiceBroadcastDraft = (Integer.parseInt(body.get("voiceBroadcastDraft")) !=0);
	     
	    VoiceBroadcast broadcast = new VoiceBroadcast(organization, group, publisher, mode, askFeedback,  askOrder, askResponse, appOnly, voice, voiceBroadcastDraft);
	    broadcastService.addBroadcast(broadcast);
	    
	    String userIdString = body.get("userIds");
	    String[] userIdList = userIdString.split(",");
	    List<BroadcastRecipient> broadcastRecipients = new ArrayList<BroadcastRecipient>();
	    for(int i=0 ; i<userIdList.length;i++)
	    {	
	    	System.out.println(userIdList[i]);
	    	User user = userService.getUser(Integer.parseInt(userIdList[i]));
	    	BroadcastRecipient broadcastRecipient = new BroadcastRecipient(broadcast, user);
	    	broadcastRecipients.add(broadcastRecipient);
	    	broadcastRecipientService.addBroadcastRecipient(broadcastRecipient);
	    }
	    
	    //TODO have to shift this function to thread. Also have to ask where is the Broadcast object mentioned here.
	    for(BroadcastRecipient recipient: broadcastRecipients)
		{
			User user=recipient.getUser();
			System.out.println("User:"+user.getName());
			List<UserPhoneNumber> phoneNumbers=user.getUserPhoneNumbers();
			for(UserPhoneNumber no:phoneNumbers)
			{
				System.out.println("User:"+no.getPhoneNumber());
				if(IVRUtils.makeOutboundCall(no.getPhoneNumber(), Configs.Telephony.IVR_NUMBER, voiceUrl));
				{
					break;
				}
			}
		}
	    
	}
	
	@RequestMapping(value = "/latestBroadcastVoiceMessages/{groupId}", method = RequestMethod.POST)
	@ResponseBody
	public void latestBroadcastableLogs(@RequestBody Map<String,String> body) {
	    System.out.println("We have received the latest body"+body);
	    Organization organization = organizationService.getOrganizationById(Integer.parseInt(body.get("organizationId")));
	    Group group = groupService.getGroup(Integer.parseInt(body.get("groupId")));
	    Voice voice = voiceService.getVoice(Integer.parseInt(body.get("voiceId")));
	  
	    String broadcastedTime = body.get("broadcastedTime");
	    
	    Timestamp timestamp = Timestamp.valueOf(broadcastedTime);
  
        LatestBroadcastableVoice latestBroadcastableVoice =  latestBroadcastableVoiceRepository.findByGroupAndOrganization(group, organization);
        if (latestBroadcastableVoice.equals(null))
        {
        	latestBroadcastableVoice = new LatestBroadcastableVoice(organization, group, timestamp, voice);
        }
        else
        {
        	latestBroadcastableVoice.setTime(timestamp);
        	latestBroadcastableVoice.setVoice(voice);
        }
        latestBroadcastableVoiceRepository.save(latestBroadcastableVoice);
    
	    
	}


}
