package app.business.services.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.MessageRepository;
import app.entities.Group;
import app.entities.message.Message;

@Service
public class VoiceMessageService extends MessageService {
	
	@Autowired
	MessageRepository messageRepository;
	
	/*
	 * Returns voice messages for a group  
	 */
	public List<Message> getVoiceMessageList(Group group) {
		return getMessageListByFormat(group, "voice");
	}
	
	/*
	 * Returns inbox voice messages for a group  
	 */
	public List<Message> getInboxVoiceMessageList(Group group) {
		return getMessageListByOrderStatus(group, "voice", "new");
	}
	
	/*
	 * Returns saved voice messages for a group  
	 */
	public List<Message> getSavedVoiceMessageList(Group group) {
		return getMessageListByOrderStatus(group, "voice", "saved");
	}
	
	/*
	 * Returns processed voice messages for a group  
	 */
	public List<Message> getProcessedVoiceMessageList(Group group) {
		return getMessageListByOrderStatus(group, "voice", "processed");
	}
	
	/*
	 * Returns rejected voice messages for a group  
	 */
	public List<Message> getRejectedVoiceMessageList(Group group) {
		return getMessageListByOrderStatus(group, "voice", "rejected");
	}
}