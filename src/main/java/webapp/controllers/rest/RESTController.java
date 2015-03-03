package webapp.controllers.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RESTController {

	@RequestMapping("/api")
	public String test(Model model) {
		model.addAttribute("displayString", "API test");
		return "welcome";
	}

}
