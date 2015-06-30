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
import app.entities.Organization;
import app.entities.PresetQuantity;
import app.entities.ProductType;

@Controller
@RequestMapping("/web/{org}")
public class ProductQuantityController {

	@Autowired
	OrganizationService organizationService;

	@RequestMapping(value="/productQuantityPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String productsPage(@PathVariable String org, Model model) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<ProductType> productTypes = new ArrayList<ProductType>(organization.getProductTypes());
		List<PresetQuantity> presetQuantity = new ArrayList<PresetQuantity>();

		for (ProductType productType : productTypes) {
			presetQuantity.addAll(productType.getPresetQuantities());
		}
		model.addAttribute("organization",organization);
		model.addAttribute("productTypes", productTypes);
		model.addAttribute("presetQuantities", presetQuantity);
		//No change in model here yet
		return "productQuantityList";
	}
}
