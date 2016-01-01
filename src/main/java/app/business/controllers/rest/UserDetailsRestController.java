package app.business.controllers.rest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.entities.User;
import app.entities.UserPhoneNumber;

@RestController
@RequestMapping ("/api/user")
public class UserDetailsRestController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserPhoneNumberService userPhoneNumberService;
	
	@RequestMapping(value = "/details/email",method = RequestMethod.GET)
	public String getUserDetails(@RequestParam String email) {
		JSONObject responseJsonObject = new JSONObject();
		User user = userService.getUserFromEmail(email);
		UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(user);
		try {
			responseJsonObject.put("name", user.getName());
			responseJsonObject.put("addess",user.getAddress());
			responseJsonObject.put("phone number", userPhoneNumber.getPhoneNumber());
		}
		catch(JSONException e){
			e.printStackTrace();
		}
		return responseJsonObject.toString();
	}
	@RequestMapping(value = "/details/number",method = RequestMethod.GET)
	public String getUserDetailsNumber(@RequestParam String phoneNumber) {
		JSONObject responseJsonObject = new JSONObject();
		UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPhoneNumber(phoneNumber);
		System.out.println(userPhoneNumber.getPhoneNumber());
		User user = userPhoneNumber.getUser();
		try {
			responseJsonObject.put("name", user.getName());
			responseJsonObject.put("addess",user.getAddress());
			responseJsonObject.put("phone number", userPhoneNumber.getPhoneNumber());
		}
		catch(JSONException e){
			e.printStackTrace();
		}
		return responseJsonObject.toString();
	}
	
	
	
}
