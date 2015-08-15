package app.entities.projections;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import app.entities.PresetQuantity;
import app.entities.ProductType;

@Projection(name="default",types = { ProductType.class })
public interface ProductTypeProjection {

	int getProductTypeId();
	
	String getDescription();
}
