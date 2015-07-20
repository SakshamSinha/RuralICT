package app.telephony.fsm.action;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.telephony.RuralictSession;
import app.telephony.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.Response;

public class PlayThankYouMessageAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		RuralictSession ruralictSession = (RuralictSession) session;

		Response response = session.getResponse();
        String language = ruralictSession.getLanguage();
        if(language==null)
        	language = "hi";

		if(session.getInvalidTries()>=5){
			response.addPlayAudio(Configs.Voice.VOICE_DIR+"/invalidTriesExceeded_"+language+".wav");	
		}
		
		else {
			response.addPlayAudio(Configs.Voice.VOICE_DIR + "/thankYou_"+language+".wav");
		}
		
		response.addHangup();

	}
}
