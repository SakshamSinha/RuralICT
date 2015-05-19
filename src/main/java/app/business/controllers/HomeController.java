package app.business.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web/{org}")
public class HomeController {

	@RequestMapping(value="/homePage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String homePage(@PathVariable String org, Model model) {
		return "home";
	}

}
