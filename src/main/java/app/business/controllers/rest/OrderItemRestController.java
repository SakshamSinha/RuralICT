package app.business.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.OrderItemService;
import app.business.services.OrderService;
import app.business.services.ProductService;
import app.entities.OrderItem;

@RestController
@RequestMapping("/rest/orderItems")
public class OrderItemRestController {
	
	@Autowired
	OrderItemService orderItemService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	ProductService productService;
	
	/*@RequestMapping(value="/add", method=RequestMethod.POST)
	public OrderItem add(@RequestBody OrderItem orderItem) {
		System.out.println("OrderItem");
		return orderItemService.addOrderItem(orderItem);
	}*/
	
	@RequestMapping(value="/add/{productid}/{quantity}/{orderid}", method=RequestMethod.GET)
	public OrderItem add(@PathVariable int productid, @PathVariable int quantity, @PathVariable int orderid) {
		System.out.println("OrderItem");
		return orderItemService.addOrderItem(new OrderItem(orderService.getOrder(orderid), productService.getProductById(productid),quantity, productService.getProductById(productid).getUnitRate()));
	}
}
