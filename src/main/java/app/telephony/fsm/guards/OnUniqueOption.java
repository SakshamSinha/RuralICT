package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.OrganizationService;
import app.business.services.springcontext.SpringContextBridge;
import app.telephony.RuralictSession;

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
		RuralictSession ruralictSession = (RuralictSession) session;
		if(optionsFor.equalsIgnoreCase("language"))
		{
			if(ruralictSession.getLanguage()==null){
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
		else if(ruralictSession.isOutbound()){
			if(choice.equalsIgnoreCase("feedback") && ruralictSession.isOrderAllowed()==false && ruralictSession.isFeedbackAllowed()==true && ruralictSession.isResponseAllowed()==false){
				return(allow);

			}
			else if(choice.equalsIgnoreCase("order")  && ruralictSession.isOrderAllowed()==true && ruralictSession.isFeedbackAllowed()==false && ruralictSession.isResponseAllowed()==false){
				return(allow);

			}
			else if(choice.equalsIgnoreCase("response") && ruralictSession.isOrderAllowed()==false && ruralictSession.isFeedbackAllowed()==false && ruralictSession.isResponseAllowed()==true){
				return(allow);

			}
		}
		else {
			if(choice.equalsIgnoreCase("feedback") && organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskFeedback()==true &&  organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskOrder()==false && organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskResponse()==false ){
				return(allow);

			}
			else if(choice.equalsIgnoreCase("order") && organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskFeedback()==false &&  organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskOrder()==true && organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskResponse()==false ){
				return(allow);

			}
			else if(choice.equalsIgnoreCase("response") && organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskFeedback()==false &&  organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskOrder()==false && organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskResponse()==true ){
				return(allow);

			}
		}
		return (!allow);
	}

}