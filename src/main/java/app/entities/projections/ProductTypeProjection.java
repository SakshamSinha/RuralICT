package app.entities.projections;

import org.springframework.data.rest.core.config.Projection;

import app.entities.ProductType;

@Projection(name="producttype",types = { ProductType.class })
public interface ProductTypeProjection {

	public int getProductTypeId();
	public String getName();
	public String getDescription();
}
