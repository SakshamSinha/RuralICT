package app.telephony.fsm.action.member;

import org.springframework.beans.factory.annotation.Autowired;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.VoiceService;
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
	@Autowired
	VoiceService voiceService;
	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();
		String messageURL=session.getMessageURL();
		voice = new Voice(messageURL , false);
		voiceService.addVoice(voice);
		   
		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/feedbackMessageConfirmed"+session.getLanguage()+".wav");
		
		
 	}


}
