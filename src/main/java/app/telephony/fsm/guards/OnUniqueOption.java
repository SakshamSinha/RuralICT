package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.OrganizationService;
import app.business.services.springcontext.SpringContextBridge;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Guard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnUniqueOption implements Guard<IVRSession,Object> {

	String optionsFor;
	String choice;
	boolean allow;

	public OnUniqueOption(String optionsFor,String choice,boolean allow) {

		this.optionsFor = optionsFor;
		this.allow = allow;
		this.choice = choice;
	}	

	@Override
	public boolean accept(Event<Object> event, IVRSession session, State<?> state) {

		OrganizationService organisationService = SpringContextBridge.services().getOrganizationService();
		if(optionsFor.equalsIgnoreCase("language"))
		{
			if(session.getLanguage()==null){
				return (!allow);
			}
			return (allow);
		}
		else if(optionsFor.equalsIgnoreCase("orderMenu")){
			if(organisationService.getOrganizationByIVRS(session.getIvrNumber()).getEnableOrderCancellation()){
				return (!allow);
			}
			return (allow);
		}

		return (!allow);
	}

}