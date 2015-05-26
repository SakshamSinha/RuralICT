package app.telephony.fsm.action;

import java.util.HashMap;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.telephony.fsm.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

public class PlayGroupIDsAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();

		//response.addPlayText("Your message has been cancelled.", Configs.Telephony.TTS_SPEED);
		response.addPlayAudio(Configs.Voice.VOICE_DIR + "/theGroupIDsAre.wav");
		
		HashMap<String,String> groups = new HashMap<String, String>(); // TODO: call the service
		
		for(String k:groups.keySet()){
			response.addPlayText(k, Configs.Telephony.TTS_SPEED);				// Group ID
			response.addPlayText(groups.get(k), Configs.Telephony.TTS_SPEED);   // Group Name
		}
		
	}

}
