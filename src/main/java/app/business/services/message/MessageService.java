package app.business.services.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.MessageRepository;
import app.entities.Group;
import app.entities.Order;
import app.entities.message.Message;

@Service
public class MessageService {
	
	@Autowired
	MessageRepository messageRepository;
	
	// gives unchecked list errors
	List<Message> getPositiveResponseList(Group group) {
		return messageRepository.findByGroupAndResponseAndType(group, true,"response");
		// should it be new List<Messages>
	}
	
	List<Message> getNegativeResponseList(Group group) {
		return messageRepository.findByGroupAndResponseAndType(group, false,"response");
	}
	
	List<Message> getResponseList(Group group) {
		return messageRepository.findByGroupAndType(group,"response");
	}
	
	List<Message> getFeedbackList(Group group) {
		return messageRepository.findByGroupAndType(group,"feedback");
	}
	
	List<Message> getOrderList(Group group) {
		return messageRepository.findByGroupAndType(group,"order");
	}
	
	void addMessage(Message message){
		messageRepository.save(message);
		
	}
	
	void removeMessage(Message message){
		messageRepository.delete(message);
	}
	
	Message getMessage(int messageId){
		return messageRepository.findOne(messageId);
	}
	
	List<Message> getAllMessageList(Group group){
		return messageRepository.findByGroup(group);
	}
	
	int getMessageId(Message message){   // duplicate method
		return message.getMessageId();
	}
	
	String getMessageType(Message message){ // duplicate method
		return message.getType();
	}
	
	String getMessageFormat(Message message) {  // duplicate method
		return message.getFormat(); 
	}
	
	Order getOrder(Message message){    // duplicate method
		return message.getOrder();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
    
	
	
}