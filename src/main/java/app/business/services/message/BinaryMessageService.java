package app.business.services.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.MessageRepository;
import app.entities.Group;
import app.entities.message.Message;

@Service
public class BinaryMessageService extends MessageService {
	
	@Autowired
	MessageRepository messageRepository;
	
	/*
	 * Returns binary messages for a group  
	 */
	public List<Message> getVoiceMessageList(Group group) {
		return getMessageListByFormat(group, "binary");
	}
	
	/*
	 * Returns inbox binary messages for a group  
	 */
	public List<Message> getInboxVoiceMessageList(Group group) {
		return getMessageListByOrderStatus(group, "binary", "new");
	}
	
	/*
	 * Returns saved binary messages for a group  
	 */
	public List<Message> getSavedVoiceMessageList(Group group) {
		return getMessageListByOrderStatus(group, "binary", "saved");
	}
	
	/*
	 * Returns processed binary messages for a group  
	 */
	public List<Message> getProcessedVoiceMessageList(Group group) {
		return getMessageListByOrderStatus(group, "binary", "processed");
	}
	
	/*
	 * Returns rejected binary messages for a group  
	 */
	public List<Message> getRejectedVoiceMessageList(Group group) {
		return getMessageListByOrderStatus(group, "binary", "rejected");
	}
	
	public List<Message> getCancelledVoiceMessageList(Group group) {
		return getMessageListByOrderStatus(group, "binary", "cancelled");
	}
}