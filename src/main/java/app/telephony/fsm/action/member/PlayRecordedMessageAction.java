package app.telephony.fsm.action.member;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.telephony.RuralictSession;
import app.telephony.fsm.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

public class PlayRecordedMessageAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		RuralictSession ruralictSession = (RuralictSession) session;
		Response response = session.getResponse();
		
		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/recordedFeedbackMessageIs"+session.getLanguage()+".wav");
		response.addPlayAudio(ruralictSession.getVoiceMessage().getUrl());
	}

}
