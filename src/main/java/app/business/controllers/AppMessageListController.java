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
import app.business.services.message.BinaryMessageService;
import app.business.services.message.VoiceMessageService;
import app.entities.PresetQuantity;
import app.entities.Product;
import app.entities.message.Message;


@Controller
@RequestMapping("/web/{org}")
public class AppMessageListController {

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
	
	@Autowired
	BinaryMessageService binaryMessageService;
	
	@RequestMapping(value="/appMessage/processed/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceProcessedMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> appProcessedMessageList=binaryMessageService.getProcessedVoiceMessageList(groupService.getGroup(groupId));		
		model.addAttribute("message",appProcessedMessageList);
		return "appProcessedMessage";
	}
	
	@RequestMapping(value="/appMessage/saved/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceSavedMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> appSavedMessageList= binaryMessageService.getSavedVoiceMessageList(groupService.getGroup(groupId));
		List<Product> productList= productService.getProductList(organizationService.getOrganizationByAbbreviation(org));
		List<PresetQuantity> presetQuantityList= presetQuantityService.getPresetQuantityList(organizationService.getOrganizationByAbbreviation(org));
		model.addAttribute("products", productList);
		model.addAttribute("presetQuantity", presetQuantityList);
		model.addAttribute("message",appSavedMessageList);
		return "appSavedMessage";
	}
	
	@RequestMapping(value="/appMessage/cancelled/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String voiceCancelledMessage(@PathVariable String org, @PathVariable int groupId, Model model) {
		List<Message> appCancelledMessageList = binaryMessageService.getCancelledVoiceMessageList(groupService.getGroup(groupId));
		model.addAttribute("message",appCancelledMessageList);
		return "appCancelledMessage";
	}
}

