package app.business.services;

import java.security.Timestamp;
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
	
	public List<OrderItem> getOrderItemsByOrganisationProductName(Organization organization, String name, Timestamp from, Timestamp to){
		return orderItemRepository.findByOrder_OrganizationAndProduct_NameAndOrder_Message_TimeBetween(organization, name, from, to);
	}
	
	public List<OrderItem> getOrderItemsByOrganisationGroupName(Organization organization, String name, Timestamp from, Timestamp to){
		return orderItemRepository.findByOrder_OrganizationAndOrder_Message_Group_NameAndOrder_Message_TimeBetween(organization, name, from, to);
	}
}
