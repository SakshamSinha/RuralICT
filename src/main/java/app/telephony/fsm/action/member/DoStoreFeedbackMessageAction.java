package app.telephony.fsm.action.member;

import org.springframework.beans.factory.annotation.Autowired;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.TelephonyService;
import app.business.services.VoiceService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.InboundCall;
import app.entities.Voice;
import app.entities.broadcast.Broadcast;
import app.entities.broadcast.VoiceBroadcast;
import app.telephony.RuralictSession;
import app.telephony.fsm.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

public class DoStoreFeedbackMessageAction implements Action<IVRSession> {

	
	@Autowired
	Voice voice;

	
	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		RuralictSession ruralictSession = (RuralictSession) session;
		Response response = session.getResponse();
		String messageURL=session.getMessageURL();
		InboundCall inboundCall= new InboundCall();
		Broadcast broadcast  = new VoiceBroadcast();
		broadcast.setBroadcastId(ruralictSession.getBroadcastID());
		Voice voiceMessage = new Voice();
		
		boolean isOutboundCall = ruralictSession.isOutbound();
			
		//	RecordEvent recordEvent = (RecordEvent) event;
		//inboundCall.setDuration(recordEvent.getDuration());
        String mode = "web";
        String type ="feedback";
        String url = "http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav";
		
      	voiceMessage.setUrl(messageURL);
		TelephonyService telephonyService = SpringContextBridge.services().getTelephonyService();
		
		if(isOutboundCall){
			telephonyService.addVoiceMessage(session.getUserNumber(), mode , type , false ,url,broadcast,inboundCall);
		}
		else{
		telephonyService.addVoiceMessage(session.getUserNumber(), mode , type , false ,url, inboundCall);
		}
		// response.addPlayAudio(Configs.Voice.VOICE_DIR + "/feedbackMessageConfirmed"+session.getLanguage()+".wav");
		
		
 	}


}
