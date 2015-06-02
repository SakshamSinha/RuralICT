package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.ProductTypeRepository;
import app.entities.ProductType;

@Service
public class ProductTypeService {
	
	@Autowired
	ProductTypeRepository productTypeRepository;
	
	public void addProductType(ProductType productType){
		productTypeRepository.save(productType);
	}
	
	public void removeProductType(ProductType productType){
		productTypeRepository.delete(productType);
	}
	
	public void getProductTypeById(int productTypeId){
		productTypeRepository.findOne(productTypeId);
		
	}
	
	public List<ProductType> getAllProductTypeList(){
		return productTypeRepository.findAll();
	}
	
	public List<ProductType> getAllProductTypeListSortedByName(){
		/*
		 * The query can also be done by the statement new Sort(Sort.Direction.ASC, "name") in the repository as well. 
		 */
		return productTypeRepository.findAllByOrderByNameAsc();
	}

}
