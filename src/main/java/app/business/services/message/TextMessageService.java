// Service for TextMesageListController and OutgoingSmsList//

package app.business.services.message;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entities.Group;
import app.data.repositories.GroupRepository;
import app.entities.broadcast.Broadcast;
import app.entities.message.Message;

@Service
public class TextMessageService { 
	
	@Autowired
	GroupRepository  groupRepository;
	
	// Function to get text message //
	@Transactional
	public List<Message> getTextMessage(int groupId, String type) { 
	   
		System.out.println("voice inbox message="+ type);
		Group group = groupRepository.findOne(groupId);
		System.out.println(group);
		List<Message> messageList = group.getMessages();
		System.out.println(messageList);
		List<Message> messagesList = new ArrayList<>();
		for(Message message: messageList){

			if(message.getFormat().equalsIgnoreCase("text") && message.getType().equalsIgnoreCase("feedback") && type.equalsIgnoreCase("feedback")){
				messagesList.add(message);
			}
			else if(message.getFormat().equalsIgnoreCase("text") && message.getType().equalsIgnoreCase("response") && type.equalsIgnoreCase("response")){
				messagesList.add(message);
			}
			else if(message.getFormat().equalsIgnoreCase("text") && type.equalsIgnoreCase("default") && (message.getType().equalsIgnoreCase("order") || message.getType().equalsIgnoreCase("feedback") || message.getType().equalsIgnoreCase("response") )){
				messagesList.add(message);
			}
			}

		return messageList;
	 }
	
	// Function to get text messages for newly placed orders //
	@Transactional
	public List<Message> getTextOrderMessage(int groupId, String type) { 
		   
		Group group = groupRepository.findOne(groupId);
		System.out.println(group);
		List<Message> messageList = group.getMessages();
		System.out.println(messageList);
		List<Message> orderList = new ArrayList<>();
		for(Message message: messageList){

			if(message.getType().equalsIgnoreCase("order") && message.getFormat().equalsIgnoreCase("text") && message.getOrder().getStatus().equalsIgnoreCase("new")){
				orderList.add(message);
			}
		}
		return orderList;
	}
	
	// Function to get text messages for order which have been accepted or rejected //
	@Transactional
	public List<Message> getTextAcceptRejectMessage(int groupId, String type) { 
		   
		Group group = groupRepository.findOne(groupId);
		List<Message> messageList = group.getMessages();
		List<Message> textMessageList = new ArrayList<>();
		for(Message message: messageList){

			if(message.getType().equalsIgnoreCase("order") && message.getFormat().equalsIgnoreCase("text") && message.getOrder().getStatus().equals("Accept")&& type.equalsIgnoreCase("Accept")){
				textMessageList.add(message);
			}
			else if(message.getType().equalsIgnoreCase("order") && message.getFormat().equalsIgnoreCase("text") && type.equalsIgnoreCase("Reject") && message.getOrder().getStatus().equalsIgnoreCase("Reject")){
				textMessageList.add(message);
			}

		}

		return textMessageList;
	}
	
	// Function to get outgoing SMS list// 
	@Transactional
	public List<Broadcast> outgoingSmsList(int groupId) { 
		
		Group group = groupRepository.findOne(groupId);
		List<Broadcast> broadcastList = group.getBroadcasts();
		List<Broadcast> broadcastedMessage =  new ArrayList<>();
		for(Broadcast message : broadcastList){
			System.out.println(message.getFormat());
			
			if(message.getFormat().equalsIgnoreCase("text")){
				broadcastedMessage.add(message);
			}
        }
		return broadcastedMessage;
		
	}
		
}
	
	
	
	
	
	
