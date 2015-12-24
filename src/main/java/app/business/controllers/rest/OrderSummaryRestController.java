package app.business.controllers.rest;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.GroupService;
import app.business.services.OrderSummaryService;
import app.business.services.OrderSummaryService.OrderSummary;
import app.business.services.OrganizationService;
import app.business.services.ProductService;

@RestController
@RequestMapping("/api/orderSummaries")
public class OrderSummaryRestController {

	@Autowired
	OrderSummaryService orderSummaryService;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrganizationService OrganizationService;
	
	@RequestMapping(value="/groupwise", method=RequestMethod.GET)
	public @ResponseBody List<OrderSummary> getOrderSummaryGroupwise(int group, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
		List<OrderSummary> orderSummaryList = orderSummaryService.getOrderSummaryListForGroup(groupService.getGroup(group), fromDate, toDate);
		return orderSummaryList;
	}
	
	@RequestMapping(value="/orgwise", method=RequestMethod.GET)
	public @ResponseBody List<OrderSummary> getOrderSummaryOrgwise(int organization, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
		List<OrderSummary> orderSummaryList = orderSummaryService.getOrderSummaryListForOrganization(OrganizationService.getOrganizationById(organization), fromDate, toDate);
		return orderSummaryList;
	}
	
	@RequestMapping(value="/productwise", method=RequestMethod.GET)
	public @ResponseBody List<OrderSummary> getOrderSummaryProductwise(int product, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
		List<OrderSummary> orderSummaryList = orderSummaryService.getOrderSummaryListForProduct(productService.getProductById(product), fromDate, toDate);
		return orderSummaryList;
	}
}
