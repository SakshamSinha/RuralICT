package app.business.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
		HashMap<String,OrderItem> map= new HashMap<String,OrderItem>();
	    for(OrderItem orderitem: order.getOrderItems())
	    {
   		 if(!map.containsKey(orderitem.getProduct().getName()))
   			 map.put(orderitem.getProduct().getName(), orderitem);
   		 else
   		 {
   			 OrderItem orderItem=new OrderItem();
			 float qty=map.get(orderitem.getProduct().getName()).getQuantity()+orderitem.getQuantity();
			 orderItem.setOrder(order);
			 orderItem.setProduct(orderitem.getProduct());
			 orderItem.setQuantity(qty);
			 orderItem.setUnitRate(orderitem.getUnitRate());
			 map.put(orderitem.getProduct().getName(),orderItem);
   		 }
   	    }
	    Order temp_order= new Order();
	    Set<String> product= map.keySet();
	    Iterator<String> i= product.iterator();
	    List<OrderItem> orderItems= new ArrayList<OrderItem>();
	    while(i.hasNext())
	    {
	    	OrderItem orderitem= map.get(i.next());
	    	orderItems.add(orderitem);
	    }
	    temp_order.setOrderId(orderId);
	    temp_order.setOrderItems(orderItems);
	    temp_order.setOrganization(organization);
 		BillLayoutSettings billLayoutSetting = billLayoutSettingsService.getBillLayoutSettingsByOrganization(organization);
		model.addAttribute("billLayout",billLayoutSetting);
		model.addAttribute("organization", organization);
		model.addAttribute("message", message);
		model.addAttribute("order", temp_order);
		model.addAttribute("total", totalCost);
		map.clear();
		return "generateBill";
	}

}
