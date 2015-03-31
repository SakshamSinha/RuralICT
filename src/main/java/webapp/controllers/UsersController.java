package webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UsersController {

	@RequestMapping("/{org}/users")
	public String users(@PathVariable String org, Model model) {
		return "users";
	}

}