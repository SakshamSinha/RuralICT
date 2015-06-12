package app.telephony.fsm.action;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.telephony.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

public class PlayMessageDiscardedAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();

		//response.addPlayText("Your message has been cancelled.", Configs.Telephony.TTS_SPEED);
		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/broadcastMessageDiscared.wav");
	}

}
