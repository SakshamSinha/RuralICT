package app.business.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entities.Group;
import app.entities.OrderItem;
import app.entities.Organization;
import app.entities.Product;

@Service
public class OrderSummaryService {
	
	@Autowired
	OrderItemService orderItemService;
	
	@Autowired
	OrganizationService OrganizationService;
	
	public static class OrderSummary{
		private String productName;
		private String groupName;
		private float quantity;
		private float unitRate;
		private float collection;
		
		public OrderSummary(String productName, String groupName, float quantity, float unitRate){
			this.productName = productName;
			this.groupName = groupName;
			this.quantity = quantity;
			this.unitRate = unitRate;
			this.collection = quantity * unitRate;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		
		public String getProductName() {
			return this.productName;
		}
		
		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
		
		public String getGroupName() {
			return this.groupName;
		}
		
		public void setQuantity(float quantity) {
			this.quantity = quantity;
		}
		
		public float getQuantity() {
			return this.quantity;
		}
		
		public void setUnitRate(float unitRate) {
			this.unitRate = unitRate;
		}
		
		public float getunitRate() {
			return this.unitRate;
		}
		
		public float getCollection() {
			return this.collection;
		}
	}
	
	public List<OrderSummary> getOrderSummaryListForGroup(Group group, Date fromDate, Date toDate){
		List<OrderItem> orderItemList = orderItemService.getOrderItemListByGroupAndTime(group, fromDate, toDate);
		
		//Defining Hash map for faster data handling
		HashMap<String, float[]> orderSummaryHashList = new HashMap<String, float[]>();
		
		for(OrderItem orderItem: orderItemList) {
			
			if(!orderSummaryHashList.containsKey(orderItem.getProduct().getName())) {
				
				//Storing all float values in array for convinience
				float summaryValues[] = {orderItem.getQuantity(), orderItem.getUnitRate()}; 
				orderSummaryHashList.put(orderItem.getProduct().getName(), summaryValues);
			}
			else {
				float summaryValues[] = {
					orderSummaryHashList.get(orderItem.getProduct().getName())[0] + orderItem.getQuantity(), 
					orderSummaryHashList.get(orderItem.getProduct().getName())[1] + orderItem.getUnitRate()
				};
				
				orderSummaryHashList.put(orderItem.getProduct().getName(), summaryValues);
				
			}
		}
		
		//Placing values from hashmap to return it to controller
		List<OrderSummary> orderSummaryList = new ArrayList<OrderSummary>();
		for(String key : orderSummaryHashList.keySet()) {
			orderSummaryList.add(new OrderSummary(key, group.getName(), orderSummaryHashList.get(key)[0], orderSummaryHashList.get(key)[1]));
		}
		
		return orderSummaryList;
	}

	public List<OrderSummary> getOrderSummaryListForOrganization(Organization organization, Date fromDate, Date toDate){
		List<Group> groupList=OrganizationService.getOrganizationGroupList(organization);
		HashMap<String, float[]> orderSummaryHashList = new HashMap<String, float[]>();
		List<OrderSummary> orderSummaryList = new ArrayList<OrderSummary>();
		for(Group group: groupList)
		{
			List<OrderItem> orderItemList = orderItemService.getOrderItemListByGroupAndTime(group, fromDate, toDate);
			
			for(OrderItem orderItem: orderItemList) {
				
				if(!orderSummaryHashList.containsKey(orderItem.getProduct().getName())) {
					
					//Storing all float values in array for convinience
					float summaryValues[] = {orderItem.getQuantity(), orderItem.getUnitRate()}; 
					orderSummaryHashList.put(orderItem.getProduct().getName(), summaryValues);
				}
				else {
					float summaryValues[] = {
						orderSummaryHashList.get(orderItem.getProduct().getName())[0] + orderItem.getQuantity(), 
						orderSummaryHashList.get(orderItem.getProduct().getName())[1] + orderItem.getUnitRate()
					};
					
					orderSummaryHashList.put(orderItem.getProduct().getName(), summaryValues);
					
				}
			}	
		}
		for(String key : orderSummaryHashList.keySet()) {
			orderSummaryList.add(new OrderSummary(key, organization.getName(), orderSummaryHashList.get(key)[0], orderSummaryHashList.get(key)[1]));
		}
		return orderSummaryList;
	}
	
	public List<OrderSummary> getOrderSummaryListForProduct(Product product, Date fromDate, Date toDate){
		List<OrderItem> orderItemList = orderItemService.getOrderItemListByProductAndTime(product, fromDate, toDate);
		
		//Defining Hash map for faster data handling
		HashMap<String, float[]> orderSummaryHashList = new HashMap<String, float[]>();
		
		for(OrderItem orderItem: orderItemList) {
			
			if(!orderSummaryHashList.containsKey(orderItem.getOrder().getMessage().getGroup().getName())) {
				
				//Storing all float values in array for convinience
				float summaryValues[] = {orderItem.getQuantity(), orderItem.getUnitRate()}; 
				orderSummaryHashList.put(orderItem.getOrder().getMessage().getGroup().getName(), summaryValues);
			}
			else {
				float summaryValues[] = {
					orderSummaryHashList.get(orderItem.getOrder().getMessage().getGroup().getName())[0] + orderItem.getQuantity(), 
					orderSummaryHashList.get(orderItem.getOrder().getMessage().getGroup().getName())[1] + orderItem.getUnitRate()
				};
				
				orderSummaryHashList.put(orderItem.getOrder().getMessage().getGroup().getName(), summaryValues);
				
			}
		}
		
		//Placing values from hashmap to return it to controller
		List<OrderSummary> orderSummaryList = new ArrayList<OrderSummary>();
		for(String key : orderSummaryHashList.keySet()) {
			orderSummaryList.add(new OrderSummary(product.getName(), key, orderSummaryHashList.get(key)[0], orderSummaryHashList.get(key)[1]));
		}
		
		return orderSummaryList;
	}
}