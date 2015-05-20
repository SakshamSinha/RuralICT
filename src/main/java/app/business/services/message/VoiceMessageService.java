// Service for VoiceMessageListController //

package app.business.services.message;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entities.Group;
import app.data.repositories.GroupRepository;
import app.entities.message.Message;

@Service
public class VoiceMessageService { 
	
	@Autowired
	GroupRepository  groupRepository;
	
	// Function to get voice messages //
	@Transactional
	public List<Message> getVoiceMessage(int groupId, String type) { 
	    // Try changing generic raw list to parameterized list  //
		Group group = groupRepository.findOne(groupId);
		List<Message> messageList = group.getMessages();
		List<Message> voiceMessageList = new ArrayList<>();
		for(Message message: messageList){

			if(message.getFormat().equalsIgnoreCase("voice") && message.getType().equalsIgnoreCase("order") && type.equalsIgnoreCase("order") ){
				voiceMessageList.add(message);
				
			}
			else if(message.getFormat().equalsIgnoreCase("voice") && message.getType().equalsIgnoreCase("feedback") && type.equalsIgnoreCase("feedback")){

				voiceMessageList.add(message);
				
			}
			else if(message.getFormat().equalsIgnoreCase("voice") && message.getType().equalsIgnoreCase("response") && type.equalsIgnoreCase("response")){

				voiceMessageList.add(message);
				
			}
		
		}
		
		return voiceMessageList;
		
	
	}
	
	// Function to get inbox voice messages //
	@Transactional
	public List<Message> getVoiceInboxMessage(int groupId, String type) { 
		   
		Group group = groupRepository.findOne(groupId);
		List<Message> messageList = group.getMessages();
		List<Message> voiceInboxMessageList = new ArrayList<>();
		for(Message message: messageList){

			if(message.getFormat().equalsIgnoreCase("voice") && message.getType().equalsIgnoreCase("order")){

				voiceInboxMessageList.add(message);
			}

        }
		
		return voiceInboxMessageList;
		
	
	}
	
}