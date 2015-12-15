package app.business.services;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.ProductRepository;
import app.entities.Organization;
import app.entities.Product;
import app.entities.ProductType;
import app.entities.projections.ProductProjection;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	public List<ProductType> getProductTypeList(Organization organization){
		return organization.getProductTypes(); 
	}
	
	public List<Product> getProductList(List<ProductType> productTypes){
		List<Product> products = new ArrayList<Product>();
		for (ProductType productType : productTypes){
			products.addAll(productType.getProducts());
		}
		return products;
	}
	
	public List<Product> getProductList(Organization organization){
		return getProductList(getProductTypeList(organization));
	}
	
	public void addProduct(Product product){
		productRepository.save(product);
	}
	
	public void removeProduct(Product product){
		productRepository.delete(product);
	}
	
	public Product getProductById(int productId){
		return productRepository.findOne(productId);
	}
	
	public Product getProductByNameAndOrg(String productName, Organization org){
		 return productRepository.findByNameAndProductType_Organization(productName,org);
	}
	
	public List<Product> getAllProductList(){
		return productRepository.findAll();
	}
	
	public List<Product> getAllProductListSortedByName(){
		/*
		 * The query can also be done by the statement new Sort(Sort.Direction.ASC, "name") in the repository as well. 
		 */
		return productRepository.findAllByOrderByNameAsc();
	}
	
	public List<Product> getProductByName(String name)
	{
		return productRepository.findByName(name);
	}
}
