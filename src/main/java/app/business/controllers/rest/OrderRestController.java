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

import app.business.services.ProductService;
import app.business.services.UserService;
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
	
	@RequestMapping(value = "/orders/add",method = RequestMethod.POST )
	public HashMap<String,String> addOrders(@RequestBody String requestBody){
		System.out.println("Inside custom add orders");
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
		return response;
	}

	@RequestMapping(value = "/orders/update/{orderId}",method = RequestMethod.POST )
	public HashMap<String,String> updateOrders(@PathVariable int orderId,@RequestBody String requestBody)
	{
		HashMap<String,String> response= new HashMap<String, String>();
		JSONObject jsonObject = null;
		String status=null;
		String comments=null;
		JSONArray orderItemsJSON = null;
		try {
			jsonObject = new JSONObject(requestBody);
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
			response.put("Status", "Error");
			response.put("Error", "No Order of the following Id found");
			return response;
		}
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
				    String productId=row.getString("id");
				    float productQuantity =(float)row.getDouble("quantity");
				    Product product=productService.getProductById(Integer.parseInt(productId));
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
		orderRepository.save(order);
		response.put("Status", "Success");
		return response;
	}
	
	
}