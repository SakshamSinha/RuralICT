package app.telephony.fsm.action;



import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.LatestRecordedVoiceService;
import app.business.services.TelephonyService;
import app.business.services.VoiceService;
import app.business.services.broadcast.BroadcastService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.InboundCall;
import app.entities.LatestRecordedVoice;
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
		
		RuralictSession ruralictSession = (RuralictSession) session;
		InboundCall inboundCall = ruralictSession.getCall();
		String url = "http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav";
		VoiceService voiceService = SpringContextBridge.services().getVoiceService();
		Voice voice=new Voice(url,false);
		voice = voiceService.addVoice(voice);
		inboundCall.setDuration(ruralictSession.getRecordEvent().getDuration());
			
		Organization organization = SpringContextBridge.services().getOrganizationService().getOrganizationByIVRS(session.getIvrNumber());
		LatestRecordedVoice latestRecordedVoice = new LatestRecordedVoice(organization, voice);

		LatestRecordedVoiceService latestRecordedVoiceService = SpringContextBridge.services().getLatestBroadcastableVoiceService();
		//TODO
		latestRecordedVoiceService.updateLatestRecordedVoice(organization,voice);
	}

}
