package app.business.services.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.MessageRepository;
import app.entities.Group;
import app.entities.message.Message;

@Service
public class TextMessageService extends MessageService {
	
	@Autowired
	MessageRepository messageRepository;
	
	/*
	 * Returns text messages for a group  
	 */
	List<Message> getTextMessageList(Group group) {
		return getMessageListByFormat(group, "text");
	}
	
	/*
	 * Returns inbox text messages for a group  
	 */
	List<Message> getInboxTextMessageList(Group group) {
		return getMessageListByOrderStatus(group, "text", "new");
	}
	
	/*
	 * Returns accepted voice messages for a group  
	 */
	List<Message> getAcceptedVoiceMessageList(Group group) {
		return getMessageListByOrderStatus(group, "text", "accepted");
	}
	
	/*
	 * Returns rejected voice messages for a group  
	 */
	List<Message> getRejectedVoiceMessageList(Group group) {
		return getMessageListByOrderStatus(group, "text", "rejected");
	}
}