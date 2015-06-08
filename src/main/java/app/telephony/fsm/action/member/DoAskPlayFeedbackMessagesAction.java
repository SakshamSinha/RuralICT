package app.telephony.fsm.action.member;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.RecordEvent;
import app.entities.Voice;
import app.entities.message.VoiceMessage;
import app.telephony.RuralictSession;
import app.telephony.fsm.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

public class DoAskPlayFeedbackMessagesAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		/*Response response = session.getResponse();   
		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/recordedFeedbackMessageIs"+session.getLanguage()+".wav");
		String feedback = session.getMessageURL();
		response.addPlayAudio("http://recordings.kookoo.in/vishwajeet/"+feedback+".wav");*/
		
		RecordEvent recordEvent = (RecordEvent) event;
		RuralictSession ruralictSession = (RuralictSession) session;
		Voice recordedMessage = new Voice();
     	recordedMessage.setUrl(recordEvent.getFileURL());
        ruralictSession.setVoiceMessage(recordedMessage);
		
 	}

}
