package app.telephony.fsm.action.member;

import in.ac.iitb.ivrs.telephony.base.IVRSession;

import com.continuent.tungsten.commons.patterns.fsm.Action;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Transition;
import com.continuent.tungsten.commons.patterns.fsm.TransitionFailureException;
import com.continuent.tungsten.commons.patterns.fsm.TransitionRollbackException;

public class AskConfirmOrderAction implements Action<IVRSession> {

	@Override
	public void doAction(Event<?> arg0, IVRSession arg1,
			Transition<IVRSession, ?> arg2, int arg3)
			throws TransitionRollbackException, TransitionFailureException {
		// TODO Auto-generated method stub

	}

}
