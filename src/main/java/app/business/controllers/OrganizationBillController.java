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

import app.business.services.BillLayoutSettingsService;
import app.business.services.GroupService;
import app.business.services.OrderService;
import app.business.services.OrganizationService;
import app.data.repositories.UserRepository;
import app.entities.BillLayoutSettings;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.Order;
import app.entities.OrderItem;
import app.entities.Organization;
import app.entities.User;
import app.entities.message.Message;
import app.util.Utils;

@Controller
@RequestMapping("/web/{org}")
public class OrganizationBillController {

	@Autowired
	UserRepository userrepository;
	
	@Autowired
	OrderService orderservice;
	
	@Autowired
	OrganizationService organizationservice;
	
	@Autowired
	BillLayoutSettingsService billLayoutSettingsService;
	
	@Autowired
	GroupService groupservice;
	
	@RequestMapping(value="/generateOrganizationBill")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String generateBill(@PathVariable String org, Model model) {
		List<Order> orderList = orderservice.getOrderByOrganizationProcessed(organizationservice.getOrganizationByAbbreviation(org));
		//Removing orders with No message associated to it.
		List<Order> nullOrders=new ArrayList<Order>();
		List<Float> totalCost= new ArrayList<Float>();
 		for(Order order:orderList){
			if(order.getMessage()==null)
				nullOrders.add(order);
			else
			{
				for(OrderItem orderItem : order.getOrderItems()) {
					totalCost.add(orderItem.getUnitRate() * orderItem.getQuantity());
				}
			}
		}
		orderList.removeAll(nullOrders);
		
		BillLayoutSettings billLayoutSetting = billLayoutSettingsService.getBillLayoutSettingsByOrganization(organizationservice.getOrganizationByAbbreviation(org));
		model.addAttribute("billLayout",billLayoutSetting);
		for(Order order:orderList){
			System.out.println("Status:"+order.getStatus());
			System.out.println("OrderId:"+order.getOrderId());
			System.out.println(order.getMessage());
		}
	
		List<Integer> indexes= new ArrayList<Integer>(orderList.size());
		for(int index=0;index<orderList.size();index++)
		{
			indexes.add(index);
		}
		model.addAttribute("total",totalCost);
		model.addAttribute("orders",orderList);
		return "generateBillMultiple";
	}
	
	@RequestMapping(value="/generateBillGroup/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String generateBillGroup(@PathVariable String org, @PathVariable Integer groupId,Model model) {
		List<Order> orderList = orderservice.getOrderByGroupProcessed(groupservice.getGroup(groupId));
		//Removing orders with No message associated to it.
		List<Order> nullOrders=new ArrayList<Order>();
		List<Float> totalCost= new ArrayList<Float>(0);
 		for(Order order:orderList){
			if(order.getMessage()==null)
				nullOrders.add(order);
			else
			{
				float sum=0;
				for(OrderItem orderItem : order.getOrderItems()) {
					sum+=orderItem.getUnitRate() * orderItem.getQuantity();
				}
				totalCost.add(sum);
			}
		}
		orderList.removeAll(nullOrders);
		
		BillLayoutSettings billLayoutSetting = billLayoutSettingsService.getBillLayoutSettingsByOrganization(organizationservice.getOrganizationByAbbreviation(org));
		model.addAttribute("billLayout",billLayoutSetting);
		for(Order order:orderList){
			System.out.println("Status:"+order.getStatus());
			System.out.println("OrderId:"+order.getOrderId());
			System.out.println(order.getMessage());
		}
	
		List<Integer> indexes= new ArrayList<Integer>(orderList.size());
		for(int index=0;index<orderList.size();index++)
		{
			indexes.add(index);
		}
		model.addAttribute("total",totalCost);
		model.addAttribute("orders",orderList);
		return "generateBillMultiple";
	}
	@RequestMapping(value="/generateBill")	
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String generateBillPage(@PathVariable String org,Model model) {
		System.out.println("Bill Page");
		Organization organization=organizationservice.getOrganizationByAbbreviation(org);
		List<Group> groupList = organization.getGroups();
		
		model.addAttribute("groups", groupList);
		return "generateBillLandingPage";
	}
	
}
