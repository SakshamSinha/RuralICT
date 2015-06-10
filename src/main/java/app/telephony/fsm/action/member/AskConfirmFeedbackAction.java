package app.telephony.fsm.action.member;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.telephony.fsm.config.Configs;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;
import com.ozonetel.kookoo.CollectDtmf;
import com.ozonetel.kookoo.Response;

public class AskConfirmFeedbackAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		Response response = session.getResponse();
		CollectDtmf cd = new CollectDtmf();
		 
		/*response.addPlayAudio(Configs.Voice.VOICE_DIR + "/recordedFeedbackMessageIs"+session.getLanguage()+".wav");
		String feedback = session.getMessageURL();
		response.addPlayAudio("http://recordings.kookoo.in/vishwajeet/"+feedback+".wav");*/
		
		cd.addPlayAudio(Configs.Voice.VOICE_DIR + "/press1ToConfirmFeedback_"+session.getLanguage()+".wav");
		cd.addPlayAudio(Configs.Voice.VOICE_DIR + "/press2ToRerecordFeedback_"+session.getLanguage()+".wav");

		cd.setMaxDigits(1);
		cd.setTimeOut(Configs.Telephony.DTMF_TIMEOUT);
		response.addCollectDtmf(cd);
	}

}
