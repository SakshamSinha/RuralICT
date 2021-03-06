package app.telephony.fsm.action;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.LatestRecordedVoiceService;
import app.business.services.VoiceService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.InboundCall;
import app.entities.Organization;
import app.entities.Voice;
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

		RuralictSession ruralictSession = (RuralictSession) session;
		String messageURL=ruralictSession.getMessageURL();
		
		InboundCall inboundCall = ruralictSession.getCall();
		String url = "http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav";
		VoiceService voiceService = SpringContextBridge.services().getVoiceService();
		Voice voice=new Voice(url,false);
		voice = voiceService.addVoice(voice);
		inboundCall.setDuration(ruralictSession.getRecordEvent().getDuration());
			
		Organization organization = SpringContextBridge.services().getOrganizationService().getOrganizationByIVRS(session.getIvrNumber());
		LatestRecordedVoiceService latestRecordedVoiceService = SpringContextBridge.services().getLatestBroadcastableVoiceService();
		latestRecordedVoiceService.updateLatestRecordedVoice(organization,voice);
	}

}
