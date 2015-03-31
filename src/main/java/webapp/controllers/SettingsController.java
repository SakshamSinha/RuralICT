package webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SettingsController {

	@RequestMapping("/{org}/settings")
	public String settings(@PathVariable String org, Model model) {
		return "settings";
	}

}