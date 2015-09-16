package app.business.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.GroupService;
import app.business.services.OrganizationService;
import app.business.services.PresetQuantityService;
import app.business.services.ProductService;
import app.business.services.message.TextMessageService;
import app.entities.PresetQuantity;
import app.entities.Product;
import app.entities.message.Message;


@Controller
@RequestMapping("/web/{org}")
public class TextMessageListController {
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	TextMessageService textMessageService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrganizationService organizationService;

	@Autowired
	PresetQuantityService presetQuantityService;
	
	@RequestMapping(value="/textMessage/feedback/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textFeedbackMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> textFeedbackMessageList=textMessageService.getFeedbackList(groupService.getGroup(groupId),"text");
		model.addAttribute("message",textFeedbackMessageList);
		return "textFeedbackMessage";
	}
	
	@RequestMapping(value="/textMessage/response/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textResponseMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		model.addAttribute(groupService.getGroup(groupId));
		return "textResponseMessage";
	}
	
	@RequestMapping(value="/textMessage/response/positive/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textPositiveResponseMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> textResponseMessageList=textMessageService.getTextPositiveResponseList(groupService.getGroup(groupId),"text");
		model.addAttribute("message",textResponseMessageList);
		return "textPositiveResponseMessage";
	}
	
	@RequestMapping(value="/textMessage/response/negative/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textNegativeResponseMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> textResponseMessageList=textMessageService.getTextNegativeResponseList(groupService.getGroup(groupId),"text");
		model.addAttribute("message",textResponseMessageList);
		return "textNegativeResponseMessage";
	}
	
	@RequestMapping(value="/textMessage/response/all/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textAllResponseMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> textResponseMessageList = textMessageService.getTextResponseList(groupService.getGroup(groupId),"text");
		model.addAttribute("message",textResponseMessageList);
		return "textAllResponseMessage";
	}
	
	@RequestMapping(value="/textMessage/inbox/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textInboxMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> textInboxMessageList= textMessageService.getInboxTextMessageList(groupService.getGroup(groupId));
		List<Product> productList= productService.getProductList(organizationService.getOrganizationByAbbreviation(org));
		List<PresetQuantity> presetQuantityList= presetQuantityService.getPresetQuantityList(organizationService.getOrganizationByAbbreviation(org));
		model.addAttribute("message",textInboxMessageList);
		model.addAttribute("products", productList);
		model.addAttribute("presetQuantity", presetQuantityList);
		return "textInboxMessage";
	}
	
	@RequestMapping(value="/textMessage/accepted/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textProcessedMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> textProcessedMessageList= textMessageService.getAcceptedTextMessageList(groupService.getGroup(groupId));
		model.addAttribute("message",textProcessedMessageList);
		return "textAcceptedMessage";
	}
	
	@RequestMapping(value="/textMessage/rejected/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textSavedMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> textRejectedMessageList=textMessageService.getRejectedTextMessageList(groupService.getGroup(groupId));
		model.addAttribute("message",textRejectedMessageList);
		return "textRejectedMessage";
	}
	
	@RequestMapping(value="/textMessage/default/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String defaultMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> textResponseMessageList=textMessageService.getTextMessageList(groupService.getGroup(groupId));
		model.addAttribute("message",textResponseMessageList);
		return "defaultMessage";
	}
}
