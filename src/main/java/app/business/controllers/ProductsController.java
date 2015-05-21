package app.business.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.business.services.OrganizationService;
import app.business.services.ProductService;
import app.entities.Organization;
import app.entities.Product;
import app.entities.ProductType;

@Controller
@RequestMapping("/web/{org}")
public class ProductsController {

	@Autowired
	OrganizationService organizationService;
	@Autowired
	ProductService productService;

	@RequestMapping(value="/productsPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String productsPage(@PathVariable String org, Model model) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<ProductType> productTypes = productService.getProductTypeList(organization);
		List<Product> products = productService.getProductList(productTypes);
		
		model.addAttribute("productTypes", productTypes);
		model.addAttribute("products", products);
		return "productList";
	}

}
