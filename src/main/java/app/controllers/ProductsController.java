package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class ProductsController {

	@RequestMapping(value="/{org}/productsPage")
	public String productsPage(@PathVariable String org, Model model) {
		return "productList";
	}

}
