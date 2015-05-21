package app.business.services;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import app.entities.Organization;
import app.entities.Product;
import app.entities.ProductType;

@Service
public class ProductService {
	public List<ProductType> getProductTypeList(Organization organization){
		return new ArrayList<ProductType>(organization.getProductTypes()); 
	}
	public List<Product> getProductList(List<ProductType> productTypes){
		List<Product> products = new ArrayList<Product>();
		for (ProductType productType : productTypes){
			products.addAll(productType.getProducts());
		}
		return products;
	}

}
