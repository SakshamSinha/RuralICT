package app.telephony.fsm.guards;

import java.util.HashMap;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.GotDTMFEvent;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.EventTypeGuard;
import com.continuent.tungsten.commons.patterns.fsm.Guard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnLanguageSelect extends EventTypeGuard<IVRSession> {

	HashMap<String,String> languages;
	
	public OnLanguageSelect(HashMap<String,String> languageMap) {
		
		super(GotDTMFEvent.class);
		languages = new HashMap<String,String>(languageMap);
	}	
	
	@Override
	public boolean accept(Event<Object> event, IVRSession session, State<?> state) {
		
		if(super.accept(event, session, state))
		{
			GotDTMFEvent ev = (GotDTMFEvent) event;
			String input=ev.getInput();
			System.out.println(input.equalsIgnoreCase("")+"=checking = "+ev.getInput());
			
			if(languages.containsKey(input)){
				session.setLanguage(languages.get(input));
				return true;
			}
			return false;
		}
		return false;
	}

}
