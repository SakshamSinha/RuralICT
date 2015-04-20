package app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.entities.Organization;
import app.entities.Product;
import app.entities.ProductType;
import app.rest.repositories.OrganizationRepository;

@Controller
@RequestMapping("/web/{org}")
public class ProductQuantityController {

	@Autowired
	OrganizationRepository organizationRepository;

	@RequestMapping(value="/productQuantityPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String productsPage(@PathVariable String org, Model model) {
		Organization organization = organizationRepository.findByAbbreviation(org);
	
		return "productQuantityList";
	}
	
	


}
