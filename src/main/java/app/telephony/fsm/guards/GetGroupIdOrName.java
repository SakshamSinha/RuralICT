package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Guard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class GetGroupIdOrName implements Guard<IVRSession,Object>{

	@Override
	public boolean accept(Event<Object> arg0, IVRSession arg1, State<?> arg2) {
		// TODO Auto-generated method stub
		
		
		
		return false;
	}

}
