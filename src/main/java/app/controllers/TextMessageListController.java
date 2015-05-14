package app.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


import app.entities.Group;
import app.entities.message.Message;
import app.rest.repositories.GroupRepository;


@Controller
@RequestMapping("/web/{org}")
public class TextMessageListController {
	@Autowired
	GroupRepository groupRepository;

	@RequestMapping(value="/textMessage/{type}/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textMessage(@PathVariable String org, @PathVariable int groupId, Model model , @PathVariable String type) {


		System.out.println("voice inbox message="+ type);
		Group group = groupRepository.findOne(groupId);
		System.out.println(group);
		List<Message> messageList = group.getMessages();
		System.out.println(messageList);
		List messagesList = new ArrayList<>();
		for(Message message: messageList){

			if(message.getFormat().equalsIgnoreCase("text") && message.getType().equalsIgnoreCase("feedback") && type.equalsIgnoreCase("feedback")){
				messagesList.add(message);
				model.addAttribute("textMessage", messagesList);
			}
			else if(message.getFormat().equalsIgnoreCase("text") && message.getType().equalsIgnoreCase("response") && type.equalsIgnoreCase("response")){
				messagesList.add(message);
				model.addAttribute("textMessage", messagesList);
			}
			else if(message.getFormat().equalsIgnoreCase("text") && type.equalsIgnoreCase("default") && (message.getType().equalsIgnoreCase("order") || message.getType().equalsIgnoreCase("feedback") || message.getType().equalsIgnoreCase("response") )){
				messagesList.add(message);
				model.addAttribute("textMessage", messagesList);
			}

		}


		return "textMessages";
	}


	@RequestMapping(value="/textInboxMessage/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textOrderMessage(@PathVariable String org, @PathVariable int groupId, Model model) {


		Group group = groupRepository.findOne(groupId);
		System.out.println(group);
		List<Message> messageList = group.getMessages();
		System.out.println(messageList);
		List orderList = new ArrayList();
		for(Message message: messageList){

			if(message.getType().equalsIgnoreCase("order") && message.getFormat().equalsIgnoreCase("text") && message.getFormat().equalsIgnoreCase("text") && message.getOrder().getStatus().equalsIgnoreCase("order")){
				orderList.add(message);
				model.addAttribute("textMessage",orderList);
			}
		}

		return "textInboxMessage";
	}

	@RequestMapping(value="/textAcceptRejectMessage/{type}/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textAcceptRejectMessage(@PathVariable String org, @PathVariable int groupId,@PathVariable String type, Model model) {

		Group group = groupRepository.findOne(groupId);
		List<Message> messageList = group.getMessages();
		List textMessageList = new ArrayList();
		for(Message message: messageList){

			if(message.getType().equalsIgnoreCase("order") && message.getFormat().equalsIgnoreCase("text") && message.getOrder().getStatus().equals("Accept")&& type.equalsIgnoreCase("Accept")){
				textMessageList.add(message);
				model.addAttribute("textMessage",textMessageList);
			}
			else if(message.getType().equalsIgnoreCase("order") && message.getFormat().equalsIgnoreCase("text") && type.equalsIgnoreCase("Reject") && message.getOrder().getStatus().equalsIgnoreCase("Reject")){
				textMessageList.add(message);
				model.addAttribute("textMessage",textMessageList);
			}

		}

		return "textMessages";
	}
}
