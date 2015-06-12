package app.business.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import app.entities.Product;

@Component
public class ProductResourceAssembler extends ResourceAssemblerSupport<Product, Resource> {
 
    public ProductResourceAssembler() {
        super(PaginationController.class, Resource.class);
    }
 
    @Override
    public List<Resource> toResources(Iterable<? extends Product> products) {
        List<Resource> resources = new ArrayList<Resource>();
        for(Product product : products) {
            resources.add(new Resource<Product>(product, linkTo(PaginationController.class).slash(product.getProductId()).withSelfRel()));
        }
        System.out.println("Resource assembler workin fine.");
        return resources;
    }
 
    @Override
    public Resource toResource(Product product) {
        return new Resource<Product>(product, linkTo(PaginationController.class).slash(product.getProductId()).withSelfRel());
    }
}
