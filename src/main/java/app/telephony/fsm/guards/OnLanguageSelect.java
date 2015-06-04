package app.telephony.fsm.guards;

import java.util.HashMap;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.GotDTMFEvent;
import app.telephony.fsm.RuralictStateMachine;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.EventTypeGuard;
import com.continuent.tungsten.commons.patterns.fsm.Guard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnLanguageSelect extends EventTypeGuard<IVRSession> {

	public OnLanguageSelect() {
		
		super(GotDTMFEvent.class);
	}	
	
	@Override
	public boolean accept(Event<Object> event, IVRSession session, State<?> state) {
		
		if(super.accept(event, session, state))
		{
			GotDTMFEvent ev = (GotDTMFEvent) event;
			String input=ev.getInput();
			
			if(RuralictStateMachine.tempLanguageMap.containsKey(input)){
				session.setLanguage(RuralictStateMachine.tempLanguageMap.get(input));
				return true;
			}
			return false;
		}
		return false;
	}

}
