package app.business.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.data.repositories.ProductRepository;
import app.entities.Product;

@RequestMapping("/productPages")
@RestController
public class PaginationController {
	@Autowired
    private ProductRepository productRepository;
	@Autowired
    private ProductResourceAssembler productResourceAssembler;
    
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    public PagedResources<Product> products(PagedResourcesAssembler assembler) {
    	System.out.println("Reached the PaginationController method");
    	Pageable pageable = new PageRequest(0, 3, Direction.ASC, "name");
        Page<Product> products = productRepository.findAll(pageable);
        System.out.println("Problems are not caused by Product statement!!");
        return assembler.toResource(products, productResourceAssembler);
    }
}
