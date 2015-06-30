package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.springcontext.SpringContextBridge;
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

		OrganizationService orgService = SpringContextBridge.services().getOrganizationService();
		UserService userService =SpringContextBridge.services().getUserService();
		UserPhoneNumberService userPhoneNumberService = SpringContextBridge.services().getUserPhoneNumberService();
		String userNumber = session.getUserNumber();
		String orgNumber = session.getIvrNumber();
		UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPhoneNumber(userNumber);
		Organization organization = orgService.getOrganizationByIVRS(orgNumber);
		RuralictSession ruralictSession = (RuralictSession) session;
		if(userPhoneNumber == null){
			return (!allow);
		}
		else {

			System.out.println(userPhoneNumber+"-----------------------"+organization);
			String userRole= userService.getUserRole(userPhoneNumber.getUser(), organization);
			if(ruralictSession.isOutbound()){
				return (!allow);
			}

			if(userRole.contains("Publisher")){
				
				return (allow);

			}
          
			return (!allow);
		}
	}
}
