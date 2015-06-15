package app.data.eventhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.business.services.OrderService;
import app.business.services.message.VoiceMessageService;
import app.entities.Order;
import app.entities.message.TextMessage;
import app.entities.message.VoiceMessage;

@RepositoryEventHandler
public class MessageEventHandler {

	@Autowired
	OrderService orderService;
	
	@Autowired
	VoiceMessageService voiceMessageService;

	/**
	 * When a user is added to a group, also add that user to the group's parent group if any.
	 * @param membership The group membership that was created.
	 */
	@HandleAfterCreate
	public void handleVoiceMessageCreate(VoiceMessage voiceMessage) {
		if(voiceMessage.getType().equals("order")) {
			Order order = new Order();
			order.setStatus("new");
			order.setOrganization(voiceMessage.getGroup().getOrganization());
	
			orderService.addOrder(order);
			
			voiceMessage.setOrder(order);
			voiceMessageService.addMessage(voiceMessage);
		}
	}
	
	public void handleTextMessageCreate(TextMessage textMessage) {
		if(textMessage.getType().equals("order")) {
			Order order = new Order();
			order.setStatus("new");
			order.setOrganization(textMessage.getGroup().getOrganization());
	
			orderService.addOrder(order);
			
			textMessage.setOrder(order);
			voiceMessageService.addMessage(textMessage);
		}
	}

}
