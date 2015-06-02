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

import app.business.services.GroupService;
import app.business.services.message.MessageService;
import app.entities.Group;
import app.entities.message.Message;


@Controller
@RequestMapping("/web/{org}")
public class VoiceMessageListController {
	@Autowired
	GroupService groupService;
	@Autowired
	MessageService messageService;

	@RequestMapping(value="/voiceMessage/feedback/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceFeedbackMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
			List<Message> voiceFeedbackMessageList=messageService.getFeedbackList(groupService.getGroup(groupId),"voice");
			model.addAttribute("message",voiceFeedbackMessageList);
		return "voiceMessage";
	}
	
	@RequestMapping(value="/voiceMessage/response/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceResponseMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
			List<Message> voiceResponseMessageList=messageService.getResponseList(groupService.getGroup(groupId),"voice");
			model.addAttribute("message",voiceResponseMessageList);
		return "voiceMessage";
	}

	@RequestMapping(value="/voiceInboxMessages/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceInboxMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> voiceInboxMessageList=messageService.getOrderList(groupService.getGroup(groupId),"voice");
		model.addAttribute("message",voiceInboxMessageList);
		return "voiceInboxMessage";
	}
}
