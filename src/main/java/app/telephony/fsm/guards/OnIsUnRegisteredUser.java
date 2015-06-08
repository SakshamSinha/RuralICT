package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Organization;
import app.entities.UserPhoneNumber;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Guard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnIsUnRegisteredUser implements Guard<IVRSession, Object> {

	
	boolean allow;
	
	public OnIsUnRegisteredUser(boolean allow){
		
		this.allow=allow;
	}
	
	@Override
	public boolean accept(Event<Object> event, IVRSession session, State<?> state) {
		
		OrganizationService orgService = SpringContextBridge.services().getOrganizationService();
		UserService userService =SpringContextBridge.services().getUserService();
		
		UserPhoneNumberService userPhoneNumberService = SpringContextBridge.services().getUserPhoneNumberService();
	
		String userNumber = session.getUserNumber();
		String orgNumber = session.getIvrNumber();
		
	    UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPhoneNumber(userNumber);
		Organization organization = orgService.getOrganizationByIVRS(orgNumber);
				
		if(userPhoneNumber == null){
			
			return true;
		}
				
		return false;
	}
	
}
