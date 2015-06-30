package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import app.business.services.UserPhoneNumberService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.UserPhoneNumber;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.Guard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnIsUnRegisteredUser implements Guard<IVRSession, Object> {


	boolean allow;

	public OnIsUnRegisteredUser(boolean allow){

		this.allow=allow;
	}

	@Override
	public boolean accept(Event<Object> event, IVRSession session, State<?> state) {


		UserPhoneNumberService userPhoneNumberService = SpringContextBridge.services().getUserPhoneNumberService();
		String userNumber = session.getUserNumber();
		UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPhoneNumber(userNumber);
		
		if(userPhoneNumber == null){

			return (allow);
		}

		return (!allow);
	}

}
