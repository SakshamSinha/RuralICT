package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.OrganizationMembershipService;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.User;
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


		UserPhoneNumberService userPhoneNumberService = SpringContextBridge.services().getUserPhoneNumberService();
		String userNumber = session.getUserNumber();
		System.out.println("userNumber "+userNumber);
		//userNumber="91"+userNumber;
		UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPhoneNumber(userNumber);
		if(userPhoneNumber == null )
		{
			return allow;
		}
		User user= userPhoneNumber.getUser();
		String ivrs =session.getIvrNumber();
		OrganizationService organizationService = SpringContextBridge.services().getOrganizationService();
		Organization organization= organizationService.getOrganizationByIVRS(ivrs);
		OrganizationMembershipService membershipService= SpringContextBridge.services().getOrganizationMembershipService();
		OrganizationMembership membership= membershipService.getUserOrganizationMembership(user, organization);
		if(membership==null)
			return (allow);
		if(membership.getStatus()==0){
			return (allow);
		}
		return (!allow);
	}

}
