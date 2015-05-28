package app.telephony.fsm.guards;

import org.springframework.beans.factory.annotation.Autowired;




import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.entities.Organization;
import app.entities.User;
import app.entities.UserPhoneNumber;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Guard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnIsPublisher implements Guard<IVRSession,Object>{
	
	@Autowired
	UserPhoneNumberService userPhoneNumberService;
	@Autowired
	OrganizationService orgService;
	@Autowired
	UserService userService;
	
	boolean allow;
	
	public OnIsPublisher(boolean allow) {
		
		this.allow=allow;
	}
	

	@Override
	public boolean accept(Event<Object> event, IVRSession session, State<?> state) {
		
		String userNumber = session.getUserNumber();
		String orgNumber = session.getIvrNumber();
		
	    UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPhoneNumber(userNumber);
		Organization organization = orgService.getOrganizationByIVRS(orgNumber);
		
		String userRole= userService.getUserRole(userPhoneNumber.getUser(), organization);
		
		if(userRole.contains("Publisher")==allow){
			
			return true;
			
		}
		
		return false;
	}
	
	

}
