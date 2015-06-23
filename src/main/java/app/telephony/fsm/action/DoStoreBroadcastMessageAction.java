package app.telephony.fsm.action;



import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.TelephonyService;
import app.business.services.broadcast.BroadcastService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.InboundCall;
import app.entities.Organization;
import app.entities.Voice;
import app.entities.broadcast.VoiceBroadcast;
import app.telephony.RuralictSession;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;


public class DoStoreBroadcastMessageAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		String messageURL=session.getMessageURL();
		Voice voice = new Voice();
		RuralictSession ruralictSession = (RuralictSession) session;
		InboundCall inboundCall = ruralictSession.getCall();
		String url = "http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav";
		voice.setUrl(url);
		inboundCall.setDuration(ruralictSession.getRecordEvent().getDuration());
		
		Organization organization = SpringContextBridge.services().getOrganizationService().getOrganizationByIVRS(session.getIvrNumber());
		VoiceBroadcast voicebroadcast = new VoiceBroadcast(
				organization,
				SpringContextBridge.services().getGroupService().getGroup(Integer.parseInt(session.getGroupID())),
				SpringContextBridge.services().getUserPhoneNumberService().getUserPhoneNumber(session.getUserNumber()).getUser(),
				"web",
				organization.getBroadcastDefaultSettings().get(0).getAskFeedback(),
				organization.getBroadcastDefaultSettings().get(0).getAskOrder(),
				organization.getBroadcastDefaultSettings().get(0).getAskResponse(),
				false,
				ruralictSession.getVoiceMessage(),
				true);

		BroadcastService broadcastService = SpringContextBridge.services().getVoiceBroadcastService();
		// Add row for broadcast
		broadcastService.addBroadcast(voicebroadcast);
		
	}

}
