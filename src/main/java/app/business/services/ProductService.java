package app.business.services;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import app.entities.Organization;
import app.entities.Product;
import app.entities.ProductType;
import app.data.repositories.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepository;
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
	
	public List<Product> getProductList(Organization organization){
		List<ProductType>productTypes = getProductTypeList(organization);
		List<Product> products = getProductList(productTypes);
		return products;
	}
	
	public void addProduct(Product product){
		productRepository.save(product);
		
	}
	public void removeProduct(Product product){
		productRepository.delete(product);
	}
	
	public Product getProductById(int productId){
		Product product = productRepository.findOne(productId);
		return product;
	}
	
	public List<Product> getAllProductList(){
		return (new ArrayList<Product> (productRepository.findAll()));
	}
	public List<Product> getAllProductListSortedByName(){
		/*
		 * The query can also be done by the statement public List<Product> findAllByOrderByNameAsc(); in the repository as well. 
		 */
		List<Product> productSorted = productRepository.findAll(sortByName());
		return productSorted;
	}
	
	private Sort sortByName(){
		return new Sort(Sort.Direction.ASC, "name");
	}
	
	public int getProductId(Product product){
		return product.getProductId();
	}
	

}
