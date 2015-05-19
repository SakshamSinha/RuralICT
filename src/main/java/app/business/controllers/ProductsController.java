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

import app.data.repositories.OrganizationRepository;
import app.entities.Organization;
import app.entities.Product;
import app.entities.ProductType;

@Controller
@RequestMapping("/web/{org}")
public class ProductsController {

	@Autowired
	OrganizationRepository organizationRepository;

	@RequestMapping(value="/productsPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String productsPage(@PathVariable String org, Model model) {
		Organization organization = organizationRepository.findByAbbreviation(org);
		List<ProductType> productTypes = new ArrayList<ProductType>(organization.getProductTypes());
		List<Product> products = new ArrayList<Product>();

		for (ProductType productType : productTypes) {
			products.addAll(productType.getProducts());
		}
		model.addAttribute("productTypes", productTypes);
		model.addAttribute("products", products);
		return "productList";
	}

}
