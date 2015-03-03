package webapp.controllers.rest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RESTController {

	@RequestMapping("/")
	public String test(Model model) {
		model.addAttribute("displayString", "API test");
		return "welcome";
	}

}
