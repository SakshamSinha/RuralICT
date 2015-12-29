package app.business.controllers;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.BillLayoutSettingsService;
import app.business.services.OrderService;
import app.business.services.OrganizationService;
import app.entities.BillLayoutSettings;
import app.entities.Order;
import app.entities.OrderItem;
import app.entities.Organization;
import app.entities.message.Message;

@Controller
@RequestMapping("/web/{org}")
public class BillController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	BillLayoutSettingsService billLayoutSettingsService;
	
	@RequestMapping(value="/generateBill/{orderId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String generateBill(@PathVariable String org, @PathVariable int orderId, Model model) {
		
		Order order = orderService.getOrder(orderId);
		Message message = orderService.getMessageByOrder(order);
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		
		float totalCost = 0; 
		for(OrderItem orderItem : order.getOrderItems()) {
			totalCost += orderItem.getUnitRate() * orderItem.getQuantity();
		}
		BillLayoutSettings billLayoutSetting = billLayoutSettingsService.getBillLayoutSettingsByOrganization(organization);
		model.addAttribute("billLayout",billLayoutSetting);
		model.addAttribute("organization", organization);
		model.addAttribute("message", message);
		model.addAttribute("order", order);
		model.addAttribute("total", totalCost);
		return "generateBill";
	}

}
