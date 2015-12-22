package app.business.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.InboundCallService;
import app.business.services.OrderItemService;
import app.business.services.OrderService;
import app.business.services.OrganizationService;
import app.business.services.OutboundCallService;
import app.business.services.broadcast.TextBroadcastService;
import app.business.services.broadcast.VoiceBroadcastService;
import app.business.services.message.MessageService;
import app.entities.Group;
import app.entities.Organization;
import app.util.Utils;

@Controller
@RequestMapping("/web/{org}")
public class GenerateLogController {
	
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	OrderService orderService;
	@Autowired
	MessageService messageService;
	@Autowired
	TextBroadcastService textBroadcastService;
	@Autowired
	VoiceBroadcastService voiceBroadcastService;
	@Autowired
	InboundCallService inboundCallService;
	@Autowired
	OutboundCallService outboundCallService;

	@RequestMapping(value="/generateLog")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String generateLog(@PathVariable String org, Model model) throws ParseException {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<Group> groups = organizationService.getOrganizationGroupList(organization);		
		//int orderprocessedcount=orderService.getOrderByOrganizationProcessed(organization).size();
		String url=Utils.getLogDirURL()+"Log"+organization.getName()+".txt";
		model.addAttribute("groups", groups);
		model.addAttribute("orgAbbrevation", org);
		model.addAttribute("organization",organization);
		model.addAttribute("url",url);
		return "generateLog";
	}
	
}
