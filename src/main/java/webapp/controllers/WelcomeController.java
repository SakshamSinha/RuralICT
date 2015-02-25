package webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import webapp.model.repositories.UserRepository;

@Controller
public class WelcomeController {

	@Autowired
	UserRepository userRepository;

	@RequestMapping("/")
	public String welcome(Model model) {
		model.addAttribute("displayString", userRepository.findByName("Ankit Vani").get(0).getName());
		return "welcome";
	}
}
