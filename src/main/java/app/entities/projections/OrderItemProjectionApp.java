package app.entities.projections;

import org.springframework.beans.factory.annotation.Value;

import app.entities.OrderItem;
import app.entities.Product;

//@Projection(name = "defaultorderitem", types = { OrderItem.class  })
public interface OrderItemProjectionApp {
	
	float getQuantity();
	
	float getUnitRate();
	
	@Value("#{target.getProduct().getName()}")
	String getProductName();
	
	@Value("#{target.getProduct().getProductId()}")
	int getProductId();

}