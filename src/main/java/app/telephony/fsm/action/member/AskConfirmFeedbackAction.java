package app.telephony.fsm.action.member;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.telephony.RuralictSession;
import app.telephony.config.Configs;

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
		RuralictSession ruralictSession = (RuralictSession) session;

		Response response = session.getResponse();
		CollectDtmf cd = new CollectDtmf();
		cd.addPlayAudio(Configs.Voice.VOICE_DIR + "/press1ToConfirmFeedback_"+ruralictSession.getLanguage()+".wav");
		cd.addPlayAudio(Configs.Voice.VOICE_DIR + "/press2ToRerecordFeedback_"+ruralictSession.getLanguage()+".wav");
		cd.setMaxDigits(1);
		cd.setTimeOut(Configs.Telephony.DTMF_TIMEOUT);
		response.addCollectDtmf(cd);
	}

}
