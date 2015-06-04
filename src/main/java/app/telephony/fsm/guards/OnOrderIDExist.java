package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.GotDTMFEvent;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import app.business.services.GroupMembershipService;
import app.business.services.GroupService;
import app.business.services.OrderService;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.entities.Order;
import app.telephony.RuralictSession;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.EventTypeGuard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnOrderIDExist extends EventTypeGuard<IVRSession> {


	@Autowired
	OrderService orderService;

	boolean allow;

	ArrayList<String> groupsID;

	public OnOrderIDExist(boolean allow) {
		super(GotDTMFEvent.class);
		this.allow=allow;
	}


	// returns true if:
	// 1. groupId exists and allow=true
	// 2. groupId doesn't exist and allow=false
	// returns false in all other cases	
	@Override
	public boolean accept(Event<Object>event, IVRSession session, State<?> state) {
		
		RuralictSession ictSession = (RuralictSession) session;
		OrganizationService orgService = ictSession.getOrganizationService();
		
		
		if (super.accept(event, session, state)) {
			GotDTMFEvent ev = (GotDTMFEvent) event;
			String input = ev.getInput().split("#")[0];
			int orderID = Integer.parseInt(input);
			orderService = new OrderService();
			Order o=orderService.getOrder(orderID);
			if(o!=null){
				if(!(o.getStatus().equalsIgnoreCase("reject") || o.getStatus().equalsIgnoreCase("processed"))
						&& o.getOrganization() == orgService.getOrganizationByIVRS(ictSession.getIvrNumber())
						&& true
						/*userPhoneNumberService.getUserPhoneNumber(ictSession.getUserNumber()).getUser() == */ 
						){
					return (true==allow);
				}				
			}
			return (false==allow);
		}

		return (false==allow);
	}

}