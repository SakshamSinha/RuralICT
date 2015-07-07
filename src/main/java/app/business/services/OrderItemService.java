package app.business.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.OrderItemRepository;
import app.entities.Group;
import app.entities.OrderItem;
import app.entities.Product;

@Service
public class OrderItemService {
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	public List<OrderItem> getOrderItemListByProductAndTime(Product product, Date fromDate, Date toDate){
		return orderItemRepository.findByProductAndOrder_Message_TimeBetween(product, fromDate, toDate);
	}
	
	public List<OrderItem> getOrderItemListByGroupAndTime(Group group, Date fromDate, Date toDate){
		return orderItemRepository.findByOrder_Message_GroupAndOrder_Message_TimeBetween(group, fromDate, toDate);
	}
	
	public OrderItem addOrderItem(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}
}
