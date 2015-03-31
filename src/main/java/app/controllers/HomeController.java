package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class HomeController {

	@RequestMapping(value="/{org}/homePage")
	public String homePage(@PathVariable String org, Model model) {
		return "home";
	}

}
