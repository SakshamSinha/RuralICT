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
import org.springframework.web.bind.annotation.RequestMethod;

import app.business.services.OrganizationService;
import app.business.services.ProductTypeService;
import app.entities.Organization;
import app.entities.ProductType;

@Controller
@RequestMapping("/web/{org}")
public class ProductTypeController 
{

	@Autowired
	OrganizationService organizationService;
	@Autowired
	ProductTypeService productTypeService;
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@RequestMapping(value="/productTypePage",method = RequestMethod.GET)
	public String productsPage(@PathVariable String org, Model model) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<ProductType> productTypes = new ArrayList<ProductType>(organization.getProductTypes());
		model.addAttribute("organization",organization);
		model.addAttribute("productTypes", productTypes);
		//No change in model here yet
		return "productTypeList";
	}
	
}

