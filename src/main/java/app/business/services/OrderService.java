package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.OrderRepository;
import app.entities.Order;
import app.entities.Organization;
import app.entities.message.Message;

@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	/*
	 * Returns list of Orders by an Organization  
	 */
	public List<Order> getOrderByOrganization(Organization organization) {
		return orderRepository.findByOrganization(organization);
	}
	
	/*
	 * changes the status of an order to 'saved'
	 */
	public void saveOrder(Order order)
	{
		order.setStatus("saved");
		orderRepository.save(order);
	}
	
	/*
	 * changes the status of an order to 'accepted'
	 */
	public void acceptOrder(Order order)
	{
		order.setStatus("accepted");
		orderRepository.save(order);
	}
	
	/*
	 * changes the status of an order to 'rejected'
	 */
	public void rejectOrder(Order order)
	{
		order.setStatus("rejected");
		orderRepository.save(order);
	}
	
	/*
	 * changes the status of an order to 'processed'
	 */
	public void processOrder(Order order)
	{
		order.setStatus("processed");
		orderRepository.save(order);
	}
	
	/*
	 * Return List of messages for a particular order
	 */
	public List<Message> getMessageListByOrder(Order order)
	{
		return order.getMessages();
	}
	
	public Order getOrder(int orderId) {
		
		return orderRepository.findOne(orderId);
	}
}