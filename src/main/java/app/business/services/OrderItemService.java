package app.business.services;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.OrderItemRepository;
import app.entities.OrderItem;
import app.entities.Organization;

@Service
public class OrderItemService {
	
	@Autowired
	OrderItemRepository orderItemRepository;
	
	public List<OrderItem> getOrderItemsByOrganisationProductName(String org, String name, Date from, Date to){
		return orderItemRepository.findByOrder_Organization_AbbreviationAndProduct_NameAndOrder_Message_TimeBetween(org, name, from, to);
	}
	
	public List<OrderItem> getOrderItemsByOrganisationGroupName(String org, String name, Date from, Date to){
		return orderItemRepository.findByOrder_Organization_AbbreviationAndOrder_Message_Group_NameAndOrder_Message_TimeBetween(org, name, from, to);
	}
	
	public OrderItem addOrderItem(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}
}
