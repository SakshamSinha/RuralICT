package app.telephony.fsm.action;


import org.springframework.beans.factory.annotation.Autowired;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.GroupService;
import app.business.services.TelephonyService;
import app.business.services.VoiceService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Group;
import app.entities.InboundCall;
import app.entities.Voice;
import app.telephony.RuralictSession;
import app.telephony.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

public class DoStoreBroadcastMessageAction implements Action<IVRSession> {

	@Autowired
	Voice voice;


	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		String messageURL=session.getMessageURL();
		Voice voiceMessage = new Voice();
		RuralictSession ruralictSession = (RuralictSession) session;
		InboundCall inboundCall = ruralictSession.getCall();
		String mode = "web";
		String type ="voice";
		String url = "http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav";
		voiceMessage.setUrl(url);
		inboundCall.setDuration(ruralictSession.getRecordEvent().getDuration());
		TelephonyService telephonyService = SpringContextBridge.services().getTelephonyService();
		telephonyService.addVoiceMessage(session.getUserNumber(),null,null, mode , type , false ,url, inboundCall);



	}

}
