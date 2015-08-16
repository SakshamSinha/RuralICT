package app.business.controllers.rest;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;

import app.business.services.ProductService;
import app.business.services.UserService;
import app.business.services.UserViewService.UserView;
import app.data.repositories.BinaryMessageRepository;
import app.data.repositories.GroupRepository;
import app.data.repositories.OrderItemRepository;
import app.data.repositories.OrderRepository;
import app.data.repositories.OrganizationRepository;
import app.entities.Order;
import app.entities.OrderItem;
import app.entities.Organization;
import app.entities.Product;
import app.entities.message.BinaryMessage;
import app.entities.message.Message;

//@RequestMapping("/api")
@RestController
@RequestMapping("/api")
public class OrderRestController {
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(value = "/hellow",method = RequestMethod.GET)
	public String hello()
	{
		return "hewllo world";
	}
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	@Autowired
	BinaryMessageRepository binaryMessageRepository;
	
	@Autowired
	GroupRepository groupRepository;

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/orders/add",method = RequestMethod.POST )
	public String addOrders(@RequestBody String requestBody){
		System.out.println("Inside custom add orders");
		JSONObject jsonObject = null;
		String organizationabbr = null;
		String groupname=null;
		try {
			jsonObject = new JSONObject(requestBody);
			organizationabbr=jsonObject.getString("orgabbr");
			groupname=jsonObject.getString("groupname");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		Order order = new Order();
		Organization organization= organizationRepository.findByAbbreviation(organizationabbr);
		order.setOrganization(organization);
		order.setStatus("saved");
		order=orderRepository.save(order);
		List<OrderItem> orderItems= new ArrayList<OrderItem>();
		try {
			JSONArray orderItemsJSON = jsonObject.getJSONArray("orderItems");
			for (int i = 0; i < orderItemsJSON.length(); i++) {
			    OrderItem orderItem= new OrderItem();
				JSONObject row = orderItemsJSON.getJSONObject(i);
			    String productname=row.getString("name");
			    float productQuantity =(float)row.getDouble("quantity");
			    Product product=productService.getProductByName(productname);
			    orderItem.setProduct(productService.getProductByName(productname));
			    orderItem.setQuantity(productQuantity);	
			    orderItem.setUnitRate(product.getUnitRate());
			    orderItem.setOrder(order);
			    orderItem=orderItemRepository.save(orderItem);
			    orderItems.add(orderItem);
			}
			
			System.out.println(orderItemsJSON.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		order.setOrderItems(orderItems);
		BinaryMessage bmessage= new BinaryMessage();
		bmessage.setTime(new Timestamp((new Date()).getTime()));
		bmessage.setOrder(order);
		bmessage.setGroup(groupRepository.findByNameAndOrganization(groupname, organization));
		bmessage.setUser( userService.getCurrentUser());
		bmessage.setMode("app");
		bmessage.setType("order");
		binaryMessageRepository.save(bmessage);
		order.setMessage(bmessage);
		orderRepository.save(order);
		return "hello";
	}

	@RequestMapping(value = "/orders/update/{orderId}",method = RequestMethod.POST )
	public String updateOrders(@PathVariable int orderId,@RequestBody String requestBody)
	{
		System.out.println("Inside Custom Update Controllers");
		JSONObject jsonObject = null;
		//String organizationabbr = null;
		//String groupname=null;
		String status=null;
		String comments=null;
		JSONArray orderItemsJSON = null;
		try {
			jsonObject = new JSONObject(requestBody);
			//organizationabbr=jsonObject.getString("orgabbr");
			//groupname=jsonObject.getString("groupname");
			status=jsonObject.getString("status");
			comments=jsonObject.getString("comments");
			orderItemsJSON = jsonObject.getJSONArray("orderItems");
		} catch (JSONException e) {
			//Uncaught but not untamed :-)
			
			//return "Error";
		}
		if(orderRepository.findOne(orderId)==null)
		{
			System.out.println("No order of the given OrderId");
			return "Error";
		}
		//Organization organization= organizationRepository.findByAbbreviation(organizationabbr);
		Order order = orderRepository.findOne(orderId);
		if(status!=null)
		{
			order.setStatus(status);
		}
		if(comments!=null)
		{
			BinaryMessage message=(BinaryMessage)order.getMessage();
			message.setComments(comments);
			binaryMessageRepository.save(message);
		}
		if(orderItemsJSON!=null)
		{
			List<OrderItem> orderItems= new ArrayList<OrderItem>();
			try {
				orderItemsJSON = jsonObject.getJSONArray("orderItems");
				for (int i = 0; i < orderItemsJSON.length(); i++) {
				    OrderItem orderItem= new OrderItem();
					JSONObject row = orderItemsJSON.getJSONObject(i);
				    String productname=row.getString("name");
				    float productQuantity =(float)row.getDouble("quantity");
				    Product product=productService.getProductByName(productname);
				    orderItem.setProduct(productService.getProductByName(productname));
				    orderItem.setQuantity(productQuantity);	
				    orderItem.setUnitRate(product.getUnitRate());
				    orderItem.setOrder(order);
				    orderItem=orderItemRepository.save(orderItem);
				    orderItems.add(orderItem);
				}
				System.out.println(orderItemsJSON.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			order.setOrderItems(orderItems);
		}
		orderRepository.save(order);
		return "Success";
	}
	
	
}