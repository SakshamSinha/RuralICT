package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.entities.Organization;
import app.entities.UserPhoneNumber;
import app.telephony.RuralictSession;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Guard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnIsPublisher implements Guard<IVRSession,Object>{
	

	boolean allow;
	
	public OnIsPublisher(boolean allow) {
		
		this.allow=allow;
	}
	

	@Override
	public boolean accept(Event<Object> event, IVRSession session, State<?> state) {
		RuralictSession ictSession = (RuralictSession) session;
		UserPhoneNumberService userPhoneNumberService = ictSession.getUserPhoneNumberService();
		OrganizationService orgService = ictSession.getOrganizationService();
		UserService userService = ictSession.getUserService();
		
		String userNumber = session.getUserNumber();
		String orgNumber = session.getIvrNumber();
		
	    UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPhoneNumber(userNumber);
		Organization organization = orgService.getOrganizationByIVRS(orgNumber);
		
		System.out.println("userService: " + (userService != null));
		System.out.println("userPhoneNumber: " + (userPhoneNumberService != null));
		System.out.println("organization: " + (organization != null));
		String userRole= userService.getUserRole(userPhoneNumber.getUser(), organization);
		
		if(userRole.contains("Publisher")==allow){
			
			return true;
			
		}
		
		return false;
	}
	
	

}
