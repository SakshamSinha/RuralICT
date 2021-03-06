package app.telephony.fsm.action.member;

import in.ac.iitb.ivrs.telephony.base.IVRSession;

import in.ac.iitb.ivrs.telephony.base.events.RecordEvent;
import app.entities.Voice;
import app.telephony.RuralictSession;
import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;

public class DoAskPlayFeedbackMessagesAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		RecordEvent recordEvent = (RecordEvent) event;
		RuralictSession ruralictSession = (RuralictSession) session;
		Voice recordedMessage = new Voice(recordEvent.getFileURL(),false);
		recordedMessage.setUrl(recordEvent.getFileURL());
		ruralictSession.setVoiceMessage(recordedMessage);
		ruralictSession.setRecordEvent(recordEvent);

	}
}
