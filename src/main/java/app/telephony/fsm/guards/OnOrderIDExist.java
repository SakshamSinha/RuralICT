package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.GotDTMFEvent;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import app.business.services.GroupMembershipService;
import app.business.services.GroupService;
import app.business.services.OrderService;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.message.MessageService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Order;
import app.telephony.RuralictSession;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.EventTypeGuard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnOrderIDExist extends EventTypeGuard<IVRSession> {

	OrderService orderService;

	boolean allow;

	ArrayList<String> groupsID;

	public OnOrderIDExist(boolean allow) {
		super(GotDTMFEvent.class);
		this.allow=allow;
	}


	/*	 returns true if:
	 1. groupId exists and allow=true
	 2. groupId doesn't exist and allow=false
	 returns false in all other cases*/	
	@Override
	public boolean accept(Event<Object>event, IVRSession session, State<?> state) {


		OrganizationService orgService = SpringContextBridge.services().getOrganizationService();
		UserPhoneNumberService userPhoneNumberService = SpringContextBridge.services().getUserPhoneNumberService();

		if (super.accept(event, session, state)) {
			GotDTMFEvent ev = (GotDTMFEvent) event;
			String input = ev.getInput().split("#")[0];
			int orderID = Integer.parseInt(input);
			orderService = new OrderService();
			OrderService orderService = SpringContextBridge.services().getOrderService();
			Order order=orderService.getOrder(orderID);
			boolean isOrderAccepted =order.getStatus().equalsIgnoreCase("reject") || order.getStatus().equalsIgnoreCase("processed");
			boolean isUserIdExistForOrderID = userPhoneNumberService.getUserPhoneNumber(session.getUserNumber()).getUser().getUserId() == 
					orderService.getOrder(orderID).getMessage().getUser().getUserId();
			boolean checkOrganization = order.getOrganization() == orgService.getOrganizationByIVRS(session.getIvrNumber());
			if(order!=null){
				if(!(isOrderAccepted) && checkOrganization &&  isUserIdExistForOrderID)
				{
					return (allow);
				}				
			}
			return (!allow);
		}

		return (!allow);
	}

}