package app.telephony.fsm.action.member;


import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.broadcast.BroadcastService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Voice;
import app.entities.broadcast.Broadcast;
import app.entities.broadcast.VoiceBroadcast;
import app.entities.message.Message;
import app.telephony.fsm.config.Configs;

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

		BroadcastService broadcastService= SpringContextBridge.services().getVoiceBroadcastService();
		UserPhoneNumberService userPhoneNumberService = SpringContextBridge.services().getUserPhoneNumberService();
		OrganizationService organizationService = SpringContextBridge.services().getOrganizationService();
		
/*		VoiceBroadcast b;
		b = (VoiceBroadcast) broadcastService.getTopBroadcast(userPhoneNumberService.getUserPhoneNumber(session.getUserNumber()).getUser(), organizationService.getOrganizationByIVRS(session.getIvrNumber()));
		Voice v = b.getVoice();
		session.setGroupID(b.getGroup().getGroupId()+"");*/
		
		/*b= broadcastService.getTopBroadcast(userPhoneNumberService.getUserPhoneNumber(session.getUserNumber()).getUser(), organizationService.getOrganizationByIVRS(session.getIvrNumber()));*/
		
		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/welcomeMessage.wav");
	/*	response.addPlayAudio(v.getUrl());
*/
		session.setPublisher(false);
				
	}


}
