package app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/web")
public class UsersController {

	@RequestMapping(value="/{org}/usersPage")
	public String usersPage(@PathVariable String org, Model model) {
		return "users";
	}

}