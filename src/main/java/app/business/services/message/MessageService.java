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
	
	// Returns messages with 'Yes' Response for a group
	List<Message> getPositiveResponseList(Group group) {
		return messageRepository.findByGroupAndResponseAndType(group, true,"response");
	}
	
	// Return messages with 'No' Response for a group
	List<Message> getNegativeResponseList(Group group) {
		return messageRepository.findByGroupAndResponseAndType(group, false,"response");
	}
	
	// returns messages which are of type 'response' for a group
	List<Message> getResponseList(Group group) {
		return messageRepository.findByGroupAndType(group,"response");
	}
	
	// return messages which are of type 'feedback' for a group
	List<Message> getFeedbackList(Group group) {
		return messageRepository.findByGroupAndType(group,"feedback");
	}
	
	// returns messages which are of type 'order' for a group
	List<Message> getOrderList(Group group) {
		return messageRepository.findByGroupAndType(group,"order");
	}
	
	// adds new message to database
	void addMessage(Message message){
		messageRepository.save(message);
	}
	
	// removes message from the database
	void removeMessage(Message message){
		messageRepository.delete(message);
	}
	
	// returns message according to the messageID
	Message getMessage(int messageId){
		return messageRepository.findOne(messageId);
	}
	
	// returns all messages for a group 
	List<Message> getAllMessageList(Group group){
		return messageRepository.findByGroup(group);
	}
	
	// returns messageId for a given message
	int getMessageId(Message message){   
		return message.getMessageId();
	}
	
	// returns type of the message
	String getMessageType(Message message){ 
		return message.getType();
	}
	
	// returns format of the message
	String getMessageFormat(Message message) {  
		return message.getFormat(); 
	}
	
	// returns order created from the message
	Order getOrder(Message message){  
		return message.getOrder();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
    
	
	
}