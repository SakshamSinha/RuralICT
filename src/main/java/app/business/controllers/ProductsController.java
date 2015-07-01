package app.business.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.business.services.OrganizationService;
import app.business.services.ProductService;
import app.entities.Organization;
import app.entities.Product;
import app.entities.ProductType;

@Controller
@RequestMapping("/web/{org}/")
public class ProductsController {

	@Autowired
	OrganizationService organizationService;
	@Autowired
	ProductService productService;
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@RequestMapping(value="/productsPage",method = RequestMethod.GET)
	public String productsPageInitial(@PathVariable String org, Model model) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<ProductType> productTypes = productService.getProductTypeList(organization);
		List<Product> products = productService.getProductList(productTypes);
		model.addAttribute("organization",organization);
		model.addAttribute("productTypes",productTypes);
		model.addAttribute("products",products);
		return "productList";
	}

}
