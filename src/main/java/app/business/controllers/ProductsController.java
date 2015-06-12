package app.business.controllers;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.business.services.OrganizationService;
import app.business.services.ProductService;
import app.entities.Organization;
import app.entities.Product;
import app.entities.ProductType;

@Controller
public class ProductsController {

	@Autowired
	OrganizationService organizationService;
	@Autowired
	ProductService productService;
	
	public class ProductView {
		List<Product> products;
		//List<ProductType> productTypes;
		public ProductView() {
			super();
		}
		public ProductView(String org) {
			List<Product> products=getProductsList(org);
			this.products=products;
		}
		public List<Product> getProductsList(String org) {
			Organization organization = organizationService.getOrganizationByAbbreviation(org);
			List<ProductType> productTypes = productService.getProductTypeList(organization);
			List<Product> products = productService.getProductList(productTypes);
			return products;
		}
		public List<Product> getProducts() {
			return products;
		}
		public void setProducts(List<Product> products) {
			this.products = products;
		}
		/*public List<ProductType> getProductTypes() {
			return productTypes;
		}
		public void setProductTypes(List<ProductType> productTypes) {
			this.productTypes = productTypes;
		}*/
	}
	
	@RequestMapping(value="/web/{org}/productsPage",method = RequestMethod.GET)
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String productsPageInitial(@PathVariable String org, Model model) {
		//model.addAttribute("productTypes",new ArrayList<ProductType>());
		//model.addAttribute("products",new ArrayList<Product>());
		//model.addAttribute("productTypes",productTypes);
		System.out.println("This function get has been called");
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<ProductType> productTypes = productService.getProductTypeList(organization);
		List<Product> products = productService.getProductList(productTypes);
		model.addAttribute("organization",organization);
		model.addAttribute("productTypes",productTypes);
		model.addAttribute("products",products);
		return "productList";
	}
	/*
	@RequestMapping(value="web/{org}/productsPage",params={"deleteRow"},method = RequestMethod.POST)
	//@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String productsPageSubmit(@PathVariable String org, Model model,final HttpServletRequest req) {
		//Organization organization = organizationService.getOrganizationByAbbreviation(org);
		//List<ProductType> productTypes = productService.getProductTypeList(organization);
		System.out.println("This function post has been called");
		//Product product = new Product("Batata", new ProductType(), 0.0F, 2, null,null);
		//productService.addProduct(product);
		final String rowId = String.valueOf(req.getParameter("deleteRow"));
	    System.out.println("The rowID is:"+rowId);
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		Product deleteProduct = productService.getProductById(Integer.parseInt(rowId));
		productService.removeProduct(deleteProduct);
		ProductView productView = new ProductView(org);
		model.addAttribute("organization",organization);
		model.addAttribute("productView", productView);
		return "productList";
	}
	/*
	@RequestMapping(value="/processProductsPage",params= {"editRow"},method=RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String productsPageSubmit(@PathVariable String org, @ModelAttribute List<Product> products, final BindingResult bindingResult, Model model) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<ProductType> productTypes = productService.getProductTypeList(organization);
		products = productService.getProductList(productTypes);
		
		model.addAttribute("productTypes", productTypes);
		model.addAttribute("products", products);
		return "productList";
	}
	*/
}
