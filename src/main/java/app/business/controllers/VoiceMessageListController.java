package app.business.controllers;

import java.util.ArrayList;
import java.util.HashMap;
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
import app.business.services.message.VoiceMessageService;
import app.entities.PresetQuantity;
import app.entities.Product;
import app.entities.message.Message;


@Controller
@RequestMapping("/web/{org}")
public class VoiceMessageListController {

	@Autowired
	GroupService groupService;
	
	@Autowired
	VoiceMessageService voiceMessageService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrganizationService organizationService;

	@Autowired
	PresetQuantityService presetQuantityService;

	@RequestMapping(value="/voiceMessage/feedback/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceFeedbackMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> voiceFeedbackMessageList=voiceMessageService.getFeedbackList(groupService.getGroup(groupId),"voice");
		model.addAttribute("message",voiceFeedbackMessageList);
		return "voiceFeedbackMessage";
	}
	
	@RequestMapping(value="/voiceMessage/response/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceResponseMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		model.addAttribute(groupService.getGroup(groupId));
		return "voiceResponseMessage";
	}
	
	@RequestMapping(value="/voiceMessage/response/positive/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voicePositiveResponseMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		HashMap<String,ArrayList<Message>> voiceResponseMessageMap=voiceMessageService.getPositiveResponseList(groupService.getGroup(groupId),"voice");
		HashMap<String,Integer> responseCount=voiceMessageService.getPositiveResponseListCount(groupService.getGroup(groupId), "voice");
		System.out.println("VMs: "+ responseCount.get("http://ruralict.cse.iitb.ac.in/Downloads/voices/2015-08-05T05-00-16.523Z.wav"));
		model.addAttribute("messagemap",voiceResponseMessageMap);
		model.addAttribute("responseCount",responseCount);
		return "voicePositiveResponseMessage";
	}
	
	@RequestMapping(value="/voiceMessage/response/negative/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textNegativeResponseMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		HashMap<String,ArrayList<Message>> voiceResponseMessageMap=voiceMessageService.getNegativeResponseList(groupService.getGroup(groupId),"voice");
		HashMap<String,Integer> responseCount=voiceMessageService.getNegativeResponseListCount(groupService.getGroup(groupId), "voice");
		model.addAttribute("messagemap",voiceResponseMessageMap);
		model.addAttribute("responseCount",responseCount);
		return "voiceNegativeResponseMessage";
	}
	
	@RequestMapping(value="/voiceMessage/response/all/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String textAllResponseMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		HashMap<String,ArrayList<Message>> voiceResponseMessageMap = voiceMessageService.getResponseList(groupService.getGroup(groupId),"voice");
		HashMap<String,Integer> responseCountYes=voiceMessageService.getPositiveResponseListCount(groupService.getGroup(groupId), "voice");
		HashMap<String,Integer> responseCountNo=voiceMessageService.getNegativeResponseListCount(groupService.getGroup(groupId), "voice");
		model.addAttribute("responseCountYes",responseCountYes);
		model.addAttribute("responseCountNo",responseCountNo);
		model.addAttribute("messagemap",voiceResponseMessageMap);
		return "voiceAllResponseMessage";
	}
	

	@RequestMapping(value="/voiceMessage/inbox/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceInboxMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> voiceInboxMessageList=voiceMessageService.getInboxVoiceMessageList(groupService.getGroup(groupId));
		List<Product> productList= productService.getProductList(organizationService.getOrganizationByAbbreviation(org));
		List<PresetQuantity> presetQuantityList= presetQuantityService.getPresetQuantityList(organizationService.getOrganizationByAbbreviation(org));
		model.addAttribute("message",voiceInboxMessageList);
		model.addAttribute("products", productList);
		model.addAttribute("presetQuantity", presetQuantityList);
		return "voiceInboxMessage";
	}
	
	@RequestMapping(value="/voiceMessage/processed/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceProcessedMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> voiceProcessedMessageList=voiceMessageService.getProcessedVoiceMessageList(groupService.getGroup(groupId));
		model.addAttribute("message",voiceProcessedMessageList);
		return "voiceProcessedMessage";
	}
	
	@RequestMapping(value="/voiceMessage/saved/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceSavedMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> voiceSavedMessageList=voiceMessageService.getSavedVoiceMessageList(groupService.getGroup(groupId));
		List<Product> productList= productService.getProductList(organizationService.getOrganizationByAbbreviation(org));
		List<PresetQuantity> presetQuantityList= presetQuantityService.getPresetQuantityList(organizationService.getOrganizationByAbbreviation(org));
		model.addAttribute("products", productList);
		model.addAttribute("presetQuantity", presetQuantityList);
		model.addAttribute("message",voiceSavedMessageList);
		return "voiceSavedMessage";
	}
	
	@RequestMapping(value="/voiceMessage/cancelled/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceCancelledMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> voiceCancelledMessageList=voiceMessageService.getCancelledVoiceMessageList(groupService.getGroup(groupId));
		model.addAttribute("message",voiceCancelledMessageList);
		return "voiceCancelledMessage";
	}
}
