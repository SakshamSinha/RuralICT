package app.telephony.fsm.guards;

import java.util.HashMap;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.GotDTMFEvent;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Guard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnLanguageSelect implements Guard<IVRSession, Object> {

	HashMap<String,String> languages;
	
	public OnLanguageSelect(HashMap<String,String> languageMap) {
		// TODO Auto-generated constructor stub
		languages = new HashMap<String,String>(languageMap);
	}	
	
	@Override
	public boolean accept(Event<Object> event, IVRSession session, State<?> state) {
		// TODO Auto-generated method stub
		GotDTMFEvent ev = (GotDTMFEvent) event;
		String input=ev.getInput();
		
		if(languages.containsKey(input)){
			session.setLanguage(languages.get(input));
			return true;
		}
		return false;
	}

}
