package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.GotDTMFEvent;
import app.telephony.fsm.RuralictStateMachine;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.EventTypeGuard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnInvalidChoice extends EventTypeGuard<IVRSession>{

	String type;
	
	public OnInvalidChoice(String type) {
		super(GotDTMFEvent.class);
		this.type=type;
	}

	@Override
	public boolean accept(Event<Object> event, IVRSession session, State<?> state) {
		
		if(super.accept(event, session, state)){
			
			String input;
			String elem;
			switch(type){
			case "response":
				input = ((GotDTMFEvent)event).getInput();
				if(RuralictStateMachine.tempResponseMap.containsKey(input))
				{
					elem = RuralictStateMachine.tempResponseMap.get(input);
					return (elem==null);
				}
				else
					return true;
			case "language":
				input = ((GotDTMFEvent)event).getInput();
				if(RuralictStateMachine.tempLanguageMap.containsKey(input))
				{
					elem = RuralictStateMachine.tempLanguageMap.get(input);
					return (elem==null);
				}
				else
					return true;
			default:
				return false;
			}
		}
		return false;
	}

}
