package app.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "productproj", types = { Product.class }) 
public interface ProductListProjection {

	int getProductId();
	String getName();
	int getQuantity();
	float getUnitRate();
	public String getImageUrl();
}