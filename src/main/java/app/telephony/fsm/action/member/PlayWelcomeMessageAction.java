package app.telephony.fsm.action.member;

import in.ac.iitb.ivrs.telephony.base.IVRSession;

import java.util.HashMap;

import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.broadcast.BroadcastService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Voice;
import app.entities.WelcomeMessage;
import app.entities.broadcast.VoiceBroadcast;
import app.telephony.RuralictSession;
import app.telephony.config.Configs;
import app.telephony.fsm.RuralictStateMachine;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

public class PlayWelcomeMessageAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();
		RuralictSession ruralictSession = (RuralictSession) session;
		boolean isOutbound = ruralictSession.isOutbound();
		WelcomeMessage welcomeMessage;
		Voice voiceMessage=null;
		BroadcastService broadcastService= SpringContextBridge.services().getVoiceBroadcastService();
		UserPhoneNumberService userPhoneNumberService = SpringContextBridge.services().getUserPhoneNumberService();
		OrganizationService organizationService = SpringContextBridge.services().getOrganizationService();
		VoiceBroadcast broadcast;
		broadcast = (VoiceBroadcast) broadcastService.getTopBroadcast(userPhoneNumberService.getUserPhoneNumber(session.getUserNumber()).getUser(), organizationService.getOrganizationByIVRS(session.getIvrNumber()), "voice");

		if(broadcast == null){
			response.addPlayText("No broadcast message");
		}
		else{
			voiceMessage = broadcast.getVoice();
		}
		String userLang=userPhoneNumberService.getUserPhoneNumber(session.getUserNumber()).getUser().getCallLocale();

		if(userLang!=null && !userLang.equalsIgnoreCase("")){
			ruralictSession.setLanguage(userLang);
		}
		else{
			ruralictSession.setLanguage(null);
		}

		if(isOutbound){

			if(voiceMessage==null){
				response.addPlayText("No broadcast Message");
			}
			else{
				response.addPlayAudio(voiceMessage.getUrl());
			}
			
			if((broadcast.getAskOrder()==false)&(broadcast.getAskFeedback()==false)&(broadcast.getAskResponse()==false)){
				response.addPlayAudio(Configs.Voice.VOICE_DIR + "/thankYou_"+userLang+".mp3");
				response.addHangup();
				
			}
			else{
			ruralictSession.setOrderAllowed(broadcast.getAskOrder());
			ruralictSession.setFeedbackAllowed(broadcast.getAskFeedback());
			ruralictSession.setResponseAllowed(broadcast.getAskResponse());
			ruralictSession.setBroadcastID(broadcast.getBroadcastId());
			ruralictSession.setGroupID(broadcast.getGroup().getGroupId()+"");
			}
		}
		else{

			ruralictSession.setOrderAllowed(organizationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskOrder());
			ruralictSession.setFeedbackAllowed(organizationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskFeedback());
			ruralictSession.setResponseAllowed(organizationService.getOrganizationByIVRS(session.getIvrNumber()).getInboundCallAskResponse());

			if(ruralictSession.getLanguage()==null){

				welcomeMessage = organizationService.getWelcomeMessageByOrganization(organizationService.getOrganizationByIVRS(session.getIvrNumber()), "en");
			}
			else{
				welcomeMessage = organizationService.getWelcomeMessageByOrganization(organizationService.getOrganizationByIVRS(session.getIvrNumber()), ruralictSession.getLanguage());
			}
			response.addPlayAudio(welcomeMessage.getVoice().getUrl());
			
			if(organizationService.getOrganizationByIVRS(session.getIvrNumber()).getEnableBroadcasts()){
				if(broadcast == null){
					ruralictSession.setGroupID("0");
				}
				else {
					ruralictSession.setGroupID(broadcast.getGroup().getGroupId()+"");
					response.addPlayAudio(voiceMessage.getUrl());
				}
			}
			else{
				ruralictSession.setGroupID(((Integer)organizationService.getParentGroup(organizationService.getOrganizationByIVRS(session.getIvrNumber())).getGroupId()).toString());
			}

		}
		ruralictSession.setPublisher(false);

		/* INITIALIZING RUDIMENTARY VARIABLES */
		RuralictStateMachine.tempLanguageMap = new HashMap<String, String>();
		RuralictStateMachine.tempLanguageMap.put("1", "mr");
		RuralictStateMachine.tempLanguageMap.put("2", "hi");
		RuralictStateMachine.tempLanguageMap.put("3", "en");


		RuralictStateMachine.tempResponseMap = new HashMap<String, String>();
		RuralictStateMachine.tempResponseMap.put("1", "Order");
		RuralictStateMachine.tempResponseMap.put("2", "Feedback");
		RuralictStateMachine.tempResponseMap.put("3", "Response");




	}


}
