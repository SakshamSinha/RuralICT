package app.business.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.OrderItemService;
import app.business.services.OrganizationService;
import app.business.services.ProductService;
import app.entities.Group;
import app.entities.Organization;
import app.entities.Product;
import app.entities.ProductType;

@Controller
@RequestMapping("/web/{org}")
public class OrderSummaryGroupsController {
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	OrganizationService organizationService;
	
	@RequestMapping(value="/orderSummaryGroupPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String orderSummaryGroup(@PathVariable String org, Model model) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<Group> groups = organizationService.getOrganizationGroupList(organization);		
		model.addAttribute("groups", groups);
		model.addAttribute("orgAbbrevation", org);
		return "orderSummaryGroups";
	}
}