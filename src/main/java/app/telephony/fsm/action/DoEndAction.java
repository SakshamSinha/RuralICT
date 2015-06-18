package app.telephony.fsm.action;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.DisconnectEvent;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;

public class DoEndAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> event, IVRSession session, Transition<IVRSession, ?> transition, int actionType)
			throws TransitionRollbackException, TransitionFailureException {

		DisconnectEvent disconnectEvent = (DisconnectEvent) event;
		session.finish(disconnectEvent.getDuration());
	}

}
