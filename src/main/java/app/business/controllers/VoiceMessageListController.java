package app.business.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.data.repositories.GroupRepository;
import app.entities.Group;
import app.entities.message.Message;


@Controller
@RequestMapping("/web/{org}")
public class VoiceMessageListController {
	@Autowired
	GroupRepository groupRepository;

	@RequestMapping(value="/voiceMessage/{type}/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceMessage(@PathVariable String org, @PathVariable int groupId, Model model , @PathVariable String type) {

		Group group = groupRepository.findOne(groupId);
		List<Message> messageList = group.getMessages();
		List voiceMessageList = new ArrayList<>();
		for(Message message: messageList){

			if(message.getFormat().equalsIgnoreCase("voice") && message.getType().equalsIgnoreCase("order") && type.equalsIgnoreCase("order") ){
				voiceMessageList.add(message);
				model.addAttribute("message",voiceMessageList);
			}
			else if(message.getFormat().equalsIgnoreCase("voice") && message.getType().equalsIgnoreCase("feedback") && type.equalsIgnoreCase("feedback")){

				voiceMessageList.add(message);
				model.addAttribute("message", voiceMessageList);
			}
			else if(message.getFormat().equalsIgnoreCase("voice") && message.getType().equalsIgnoreCase("response") && type.equalsIgnoreCase("response")){

				voiceMessageList.add(message);
				model.addAttribute("message", voiceMessageList);
			}

		}


		return "voiceMessage";
	}

	@RequestMapping(value="/voiceInboxMessages/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceInboxMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		Group group = groupRepository.findOne(groupId);
		List<Message> messageList = group.getMessages();
		List voiceInboxMessageList = new ArrayList<>();
		for(Message message: messageList){

			if(message.getFormat().equalsIgnoreCase("voice") && message.getType().equalsIgnoreCase("order")){

				voiceInboxMessageList.add(message);
				model.addAttribute("message",voiceInboxMessageList);
			}


		}
		return "voiceInboxMessage";
	}


}
