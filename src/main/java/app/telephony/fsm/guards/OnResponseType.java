package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.GotDTMFEvent;
import app.telephony.fsm.RuralictStateMachine;
import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.EventTypeGuard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnResponseType extends EventTypeGuard<IVRSession> {

	String responseType;
	
	public OnResponseType(String reponseType) {
		// TODO Auto-generated constructor stub
		super(GotDTMFEvent.class);
		this.responseType = reponseType;
	}
	
	@Override
	public boolean accept(Event<Object> event, IVRSession session, State<?> state) {
		// TODO Auto-generated method stub
		if(super.accept(event, session, state))
		{
			GotDTMFEvent ev = (GotDTMFEvent) event;
			String input = ev.getInput();
			String value = RuralictStateMachine.tempResponseMap.get(input);
			
			if(value.equalsIgnoreCase(responseType)){
				return true;
			}
			return false;
		}
		return false;
	}

}
