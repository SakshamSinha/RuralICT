package app.business.controllers.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.GroupService;
import app.business.services.OrganizationService;
import app.business.services.OutboundCallService;
import app.business.services.UserPhoneNumberService;
import app.entities.Group;
import app.entities.OutboundCall;
import app.entities.User;


@RestController
@RequestMapping("/api/manageReportsOutboundCalls")
public class ManageReportsOutboundCallsRestController {

	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	OutboundCallService outboundCallService;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	UserPhoneNumberService userPhoneNumberService;
	
	public static class ManageReportsOutboundCalls {

		 private String dateTime;
		 private String status;
		 private String phone;	 
		 
		 public ManageReportsOutboundCalls(String dateTime, String status, String phone)
		 {
			 this.dateTime = dateTime;
			 this.status = status;
			 this.phone = phone;
		 }
		 
		public String getDateTime()
		{
			return dateTime;
		}
		
		public String getphone()
		{
			return phone;
		}
		
		public String getStatus()
		{
			return status;
		}		 	 
			
	}
	
	@RequestMapping(value="/getOutboundCallsList", method=RequestMethod.GET, produces = "application/json")
	public List<ManageReportsOutboundCalls> getOutboundCallsList(int grp) {
		
		List<ManageReportsOutboundCalls> outboundCallRows = new ArrayList<ManageReportsOutboundCalls>();
		
		//Organization organization = organizationService.getOrganizationByAbbreviation(org);
		Group group= groupService.getGroup(grp);
		

		List<OutboundCall> outboundCallList= outboundCallService.getOutboundCallList(group);

		String status;
		String phoneNum;
		String dateTime;
		
		//iterate through broadcast id and get time
		for(OutboundCall outboundCallRow : outboundCallList){
			
			//dateTime				
			dateTime = outboundCallRow.getBroadcastSchedule().getTime().toString();
			
			//status
			status= outboundCallRow.getStatus();
		
			//Phone Number of User
			User user= outboundCallRow.getBroadcastRecipient().getUser();
			phoneNum = userPhoneNumberService.getUserPrimaryPhoneNumber(user).getPhoneNumber();

			//Custom object
			ManageReportsOutboundCalls mroc= new ManageReportsOutboundCalls(dateTime, status, phoneNum);
			
				//Add to outboundCallRows
				outboundCallRows.add(mroc);
			}
			
			return outboundCallRows;
	}
}
