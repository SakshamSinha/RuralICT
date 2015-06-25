package app.telephony.fsm.guards;

import in.ac.iitb.ivrs.telephony.base.IVRSession;
import in.ac.iitb.ivrs.telephony.base.events.GotDTMFEvent;
import in.ac.iitb.ivrs.telephony.base.util.IVRUtils;

import java.util.ArrayList;

import app.business.services.OrderService;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Order;

import com.continuent.tungsten.commons.patterns.fsm.Event;
import com.continuent.tungsten.commons.patterns.fsm.EventTypeGuard;
import com.continuent.tungsten.commons.patterns.fsm.State;

public class OnOrderIDExist extends EventTypeGuard<IVRSession> {

	boolean allow;

	public OnOrderIDExist(boolean allow) {
		super(GotDTMFEvent.class);
		this.allow=allow;
	}


	/**	 returns true if:
	 1. groupId exists and allow=true
	 2. groupId doesn't exist and allow=false
	 returns false in all other cases*/	
	@Override
	public boolean accept(Event<Object>event, IVRSession session, State<?> state) {

		if (super.accept(event, session, state)) {
		
			GotDTMFEvent ev = (GotDTMFEvent) event;
			String input = ev.getInput().split("#")[0];
			int orderID = Integer.parseInt(input);
			OrderService orderService = SpringContextBridge.services().getOrderService();
			OrganizationService orgService = SpringContextBridge.services().getOrganizationService();
			UserPhoneNumberService userPhoneNumberService = SpringContextBridge.services().getUserPhoneNumberService();
			Order order=orderService.getOrder(orderID);
			boolean isOrderAccepted =order.getStatus().equalsIgnoreCase("reject") || order.getStatus().equalsIgnoreCase("processed")||order.getStatus().equalsIgnoreCase("cancelled");
			boolean isUserIdExistForOrderID = (userPhoneNumberService.getUserPhoneNumber(session.getUserNumber()).getUser().getUserId()) == (orderService.getOrder(orderID).getMessage().getUser().getUserId());
			boolean checkOrganization = order.getOrganization() == orgService.getOrganizationByIVRS(session.getIvrNumber());
			if(orderService.getOrder(orderID).getMessage().equals(null)){
				return (!allow);
			}
			else if(order!=null){
				if(!(isOrderAccepted) && checkOrganization &&  isUserIdExistForOrderID)
				{
					orderService.cancelOrder(order);
					String message = "Your Order " + orderID+" is cancelled";
					try {
						IVRUtils.sendSMS(session.getUserNumber(),message);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return (allow);
				}				
			}
			return (!allow);
		}

		return (false);
	}

}