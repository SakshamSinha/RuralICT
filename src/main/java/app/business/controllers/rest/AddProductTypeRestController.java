package app.business.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.ProductTypeService;
import app.data.repositories.OrganizationRepository;


@RestController
@RequestMapping("/api")

public class AddProductTypeRestController {
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	ProductTypeService productTypeService;

}
