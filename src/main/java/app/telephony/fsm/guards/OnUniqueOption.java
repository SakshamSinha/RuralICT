package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
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

		String opts;
	    OrganizationService organisationService = SpringContextBridge.services().getOrganizationService();
		UserPhoneNumberService userPhoneNumberService = SpringContextBridge.services().getUserPhoneNumberService();
		if(optionsFor.equalsIgnoreCase("language"))
		{
			String lang="";
			String userLang=userPhoneNumberService.getUserPhoneNumber(session.getUserNumber()).getUser().getCallLocale();
			opts = organisationService.getOrganizationByIVRS(session.getIvrNumber()).getDefaultCallLocale();
			if(userLang.equalsIgnoreCase("")){
				return (allow==false);
			}
			session.setLanguage(userLang);
			return (allow==true);/*


// required this comment
			for(int i=1;a<2 && i<10;i++){
				if(opts.contains(i+"")){
					a++;
					lang = RuralictStateMachine.tempLanguageMap.get(i+"");
				}
			}
			if(a==1)
			{
				session.setLanguage(lang);
				return (allow==true);
			}
		} 
		else if(optionsFor.equalsIgnoreCase("response"))
		{
			String selected="";
			a=0;
			if(organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskFeedback())
			{
				selected="Feedback";
				a++;
			}
			if(organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskOrder())
			{
				selected="Order";
				a++;
			}
			if(organisationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskResponse())
			{
				selected="Response";
				a++;
			}
			if(a==1 && choice.equalsIgnoreCase(selected)){
				return (allow==true);
			}
			return (allow==false);*/
		}
		else if(optionsFor.equalsIgnoreCase("orderMenu")){
			if(organisationService.getOrganizationByIVRS(session.getIvrNumber()).getEnableOrderCancellation()){
				return (allow==false);
			}
			return (allow==true);
		}

		return (allow==false);
	}

}