package app.business.services.message;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.MessageRepository;
import app.entities.Group;
import app.entities.message.Message;

@Service
public class MessageService {
	
	@Autowired
	MessageRepository messageRepository;
	
	/*
	 * Returns messages with 'Yes' Response for a group  
	 */
	List<Message> getPositiveResponseList(Group group) {
		return (new ArrayList<Message>(messageRepository.findByGroupAndResponseAndType(group, true, "response")));
	}
	
	/*
	 * Returns messages with 'No' Response for a group  
	 */
	List<Message> getNegativeResponseList(Group group) {
		return (new ArrayList<Message>(messageRepository.findByGroupAndResponseAndType(group, false, "response")));
	}
	
	/*
	 * Returns messages which are of type 'response' for a group
	 */
	List<Message> getResponseList(Group group, String format) {
		return (new ArrayList<Message>(messageRepository.findByGroupAndTypeAndFormat(group, "response", format)));
	}
	
	/*
	 * Return messages which are of type 'feedback' for a group
	 */
	List<Message> getFeedbackList(Group group, String format) {
		return (new ArrayList<Message>(messageRepository.findByGroupAndTypeAndFormat(group, "feedback", format)));
	}
	
	/*
	 * Returns messages which are of type 'order' for a group
	 */
	List<Message> getOrderList(Group group, String format) {
		return (new ArrayList<Message>(messageRepository.findByGroupAndTypeAndFormat(group, "order", format)));
	}
	
	/*
	 * Adds a new message to database
	 */
	void addMessage(Message message){
		messageRepository.save(message);
	}
	
	/*
	 * Removes a message from the database
	 */
	void removeMessage(Message message){
		messageRepository.delete(message);
	}				
	
	/*
	 * Returns message according to the messageID
	 */
	Message getMessage(int messageId){
		return messageRepository.findOne(messageId);
	}
	
	/*
	 * Returns all messages for a group 
	 */
	List<Message> getAllMessageList(Group group){
		return (new ArrayList<Message>(messageRepository.findByGroup(group)));
	}
	
	/*
	 * Returns messages of a given format for a group 
	 */
	List<Message> getMessageListByFormat(Group group, String format){
		return (new ArrayList<Message>(messageRepository.findByGroupAndFormat(group, format)));
	}
}