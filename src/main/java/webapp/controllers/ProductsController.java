package webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductsController {

	@RequestMapping("/{org}/products")
	public String productsList(@PathVariable String org, Model model) {
		return "productList";
	}

}
