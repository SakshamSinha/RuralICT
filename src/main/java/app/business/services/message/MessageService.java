package app.business.services.message;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	public List<Message> getPositiveResponseList(Group group, String format) {
		return messageRepository.findByGroupAndResponseAndTypeAndFormat(group, true, "response", format);
	}

	/*
	 * Returns messages with 'No' Response for a group  
	 */
	public List<Message> getNegativeResponseList(Group group, String format) {
		return messageRepository.findByGroupAndResponseAndTypeAndFormat(group, false, "response", format);
	}

	/*
	 * Returns messages which are of type 'response' for a group
	 */
	public List<Message> getResponseList(Group group, String format) {
		return messageRepository.findByGroupAndTypeAndFormat(group, "response", format);
	}

	/*
	 * Return messages which are of type 'feedback' for a group
	 */
	public List<Message> getFeedbackList(Group group, String format) {
		return messageRepository.findByGroupAndTypeAndFormat(group, "feedback", format);
	}

	/*
	 * Returns messages which are of type 'order' for a group
	 */
	public List<Message> getOrderList(Group group, String format) {
		return messageRepository.findByGroupAndTypeAndFormat(group, "order", format);
	}

	/*
	 * Adds a new message to database
	 */
	public void addMessage(Message message){
		messageRepository.save(message);
	}

	/*
	 * Removes a message from the database
	 */
	public void removeMessage(Message message){
		messageRepository.delete(message);
	}				

	/*
	 * Returns message according to the messageID
	 */
	public Message getMessage(int messageId){
		return messageRepository.findOne(messageId);
	}

	public Message updateMessageComment(String comment, Message message){
		message.setComments(comment);
		return messageRepository.save(message);
	}

	/*
	 * Returns all messages for a group 
	 */
	public List<Message> getAllMessageList(Group group){
		return messageRepository.findByGroup(group);
	}

	/*
	 * Returns messages of a given format for a group 
	 */
	public List<Message> getMessageListByFormat(Group group, String format){
		return messageRepository.findByGroupAndFormat(group, format);
	}

	/*
	 * Returns messages of a given order status for a group 
	 */
	public List<Message> getMessageListByOrderStatus(Group group, String format, String status){
		return messageRepository.findByGroupAndFormatAndOrder_Status(group, format, status, new Sort(Direction.DESC, "time"));
	}

	public List<Message> getRejectedTextMessageList(Group group) {
		return getMessageListByOrderStatus(group, "text", "rejected");
	}
}