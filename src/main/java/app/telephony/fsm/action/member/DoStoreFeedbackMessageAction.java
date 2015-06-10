package app.telephony.fsm.action.member;

import org.springframework.beans.factory.annotation.Autowired;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.TelephonyService;
import app.business.services.VoiceService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.InboundCall;
import app.entities.Voice;
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

		VoiceService voiceService = SpringContextBridge.services().getVoiceService();
		Response response = session.getResponse();
		String messageURL=session.getMessageURL();
		InboundCall inboundCall= new InboundCall();
		Voice voiceMessage = new Voice();
		//	RecordEvent recordEvent = (RecordEvent) event;
		//inboundCall.setDuration(recordEvent.getDuration());
        String mode = "web";
        String type ="feedback";
        String url = "http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav";
		
      	voiceMessage.setUrl(messageURL);
		
			
	//	voice = new Voice("http://recordings.kookoo.in/vishwajeet/"+messageURL+".wav" , false);
				
		TelephonyService telephonyService = SpringContextBridge.services().getTelephonyService();
		telephonyService.addVoiceMessage(session.getUserNumber(), mode , type , false ,url, inboundCall);
       // response.addPlayAudio(Configs.Voice.VOICE_DIR + "/feedbackMessageConfirmed"+session.getLanguage()+".wav");
		
		
 	}


}
