package app.business.controllers;

import in.ac.iitb.ivrs.telephony.base.util.IVRUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.business.services.BroadcastRecipientService;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.broadcast.BroadcastService;
import app.entities.BroadcastRecipient;
import app.entities.User;
import app.entities.UserPhoneNumber;
import app.entities.broadcast.Broadcast;

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
	
	@RequestMapping(value="/textBroadcast",method=RequestMethod.POST)
	@ResponseBody
	public void sendTextMessage(@RequestParam("message") String message,@RequestParam("broadcastid") int broadcastid) {
	                    	
		     Broadcast bc = broadcastService.getBroadcast(broadcastid);
		     
		     List<BroadcastRecipient> br = bc.getBroadcastRecipients();
		     
		     for(BroadcastRecipient brc : br)
		     {
		    	 User user = brc.getUser();
		    	 UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(user); 
		    	// IVRUtils.sendSMS(userPhoneNumber.getPhoneNumber(),message);
		    	 
		     }
		
		System.out.println("sendSMS was called successfully from the frontend");
		
	}

}