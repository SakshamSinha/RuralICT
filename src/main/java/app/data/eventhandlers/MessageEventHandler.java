package app.data.eventhandlers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.business.services.UserPhoneNumberService;
import app.business.services.message.MessageService;
import app.entities.UserPhoneNumber;
import app.entities.message.Message;

@RepositoryEventHandler(Message.class)
public class MessageEventHandler {
	
	@Autowired
	MessageService messageService;
	@Autowired
	UserPhoneNumberService userPhoneNumberService; 
	
	@HandleAfterCreate
	public void handleOrderCreate(Message message){
		int orderId = message.getOrder().getOrderId();
		UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(message.getUser());
		String SMSmessage = "Your order with orderID "+orderId +" has been received. You will be corresponded once the order has been processed.";
		
				
	}

}
