package app.business.controllers.rest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.json.*;

import app.business.services.OrderService;
import app.business.services.ProductService;
import app.business.services.UserService;
import app.data.repositories.BillLayoutSettingsRepository;
import app.data.repositories.BinaryMessageRepository;
import app.data.repositories.GroupRepository;
import app.data.repositories.OrderItemRepository;
import app.data.repositories.OrderRepository;
import app.data.repositories.OrganizationRepository;
import app.entities.BillLayoutSettings;
import app.entities.Order;
import app.entities.OrderItem;
import app.entities.Organization;
import app.entities.Product;
import app.entities.message.BinaryMessage;
import app.util.SendBill;
import app.util.SendMail;

@RestController
@RequestMapping("/api")
public class OrderRestController {
	
	@Autowired
	ProductService productService;
	
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
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	BillLayoutSettingsRepository billLayoutSettingsRepository;
	
	@RequestMapping(value = "/orders/add",method = RequestMethod.POST )
	public HashMap<String,String> addOrders(@RequestBody String requestBody){
		HashMap<String,String> response= new HashMap<String, String>();
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
			    Product product=productService.getProductByNameAndOrg(productname,organization);
			    orderItem.setProduct(product);
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
		response.put("orderId",new Integer(order.getOrderId()).toString());
		response.put("Status", "Success");
		String email=order.getMessage().getUser().getEmail();
		SendMail.sendMail(email, "Cottage Industry App: Order Placed Successfully" , "Your order has been placed successfully with order id is: " + new Integer(order.getOrderId()).toString());
		return response;
	}

	@RequestMapping(value = "/orders/update/{orderId}",method = RequestMethod.POST ,produces="application/json" )
	public String updateOrders(@PathVariable int orderId,@RequestBody String requestBody)
	{
		JSONObject response= new JSONObject();
		JSONObject jsonObject = null;
		String status=null;
		String comments=null;
		String orgabr=null;
		JSONArray orderItemsJSON = null;
		try {
			jsonObject = new JSONObject(requestBody);
			status=jsonObject.getString("status");
			//comments=jsonObject.getString("comments");
			orderItemsJSON = jsonObject.getJSONArray("orderItems");
			//orgabr= jsonObject.getString("orgabbr");
		} catch (JSONException e) {
			//Uncaught but not untamed :-)			
			//return "Error";
		}
		if(orderRepository.findOne(orderId)==null)
		{
			try {
				response.put("Status", "Error");
				response.put("Error", "No Order of the following Id found");
				return response.toString();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		Order order = orderRepository.findOne(orderId);
		Organization organization= order.getOrganization();
		/*if(status!=null)
		{
			order.setStatus(status);
			if(status.equals("cancelled"))
				SendMail.sendMail(order.getMessage().getUser().getEmail(),"Cottage Industry App: Order Cancellation Acknowledgement","Dear User\nYour Order with order id : "+order.getOrderId()+" has been successfully cancelled.\n Thankyou for using our service.");
		}
		if(comments!=null)
		{
			BinaryMessage message=(BinaryMessage)order.getMessage();
			message.setComments(comments);
			binaryMessageRepository.save(message);
		}*/
		if(orderItemsJSON!=null)
		{
			for( OrderItem orderitem : order.getOrderItems())
			{
				orderItemRepository.delete(orderitem);
			}
			List<OrderItem> orderItems= new ArrayList<OrderItem>();
			try {
				orderItemsJSON = jsonObject.getJSONArray("orderItems");
				for (int i = 0; i < orderItemsJSON.length(); i++) {
				    OrderItem orderItem= new OrderItem();
					JSONObject row = orderItemsJSON.getJSONObject(i);
					System.out.println("Inside orderItems");
				    String productName=row.getString("name");
				    float productQuantity =(float)row.getDouble("quantity");
				    Product product=productService.getProductByNameAndOrg(productName, organization);
				    orderItem.setProduct(product);
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
		try {
			response.put("Status", "Success");
			order.setStatus(status);
			if(status.equals("cancelled"))
			{
				SendMail.sendMail(order.getMessage().getUser().getEmail(), "Cottage Industry App: Order Cancellation Acknowledgement", "Dear User,\nYour order with order id "+order.getOrderId()+" has been successfully cancelled.\nWe hope to serve you again.");
			}
			else {
				SendMail.sendMail(order.getMessage().getUser().getEmail(), "Cottage Industry App: Order Modification Acknowledgement", "Dear User,\nYour order with order id "+order.getOrderId()+" has been successfully modified.\n");
			}
			orderRepository.save(order);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return response.toString();
	}
	
	@RequestMapping(value = "/orders/updatestatus/{orderId}",method = RequestMethod.GET, produces="text/plain" )
	public String updateOrderStatus(@PathVariable int orderId)
	{
		System.out.println("In update status with order id = "+orderId);
		Order order = orderRepository.findOne(orderId);
		Organization organization= order.getOrganization();
		if(order.getStatus().equals("processed"))
		{
			System.out.println("In processed");
			BillLayoutSettings billLayoutSetting = billLayoutSettingsRepository.findByOrganization(organization);
			SendBill.sendMail(order, organization, billLayoutSetting);
		}
		else if (order.getStatus().equals("saved"))
		{
			System.out.println("In saved");
			SendMail.sendMail(order.getMessage().getUser().getEmail(), "Cottage Industry App: Order recieved", "Organization "+organization.getName()+" has recieved your order. Order processing might take couple of days. Once your order is processed, you will receive an email confirming the same.\n\n Thankyou for shopping with us.");
		}
		System.out.println("Mail sent");
		return "Success";
	}
}