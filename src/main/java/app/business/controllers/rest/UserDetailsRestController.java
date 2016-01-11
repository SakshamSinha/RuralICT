package app.business.controllers.rest;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.OrganizationMembershipService;
import app.business.services.OrganizationService;
import app.business.services.ProductTypeService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.ProductType;
import app.entities.User;
import app.entities.UserPhoneNumber;

@RestController
@RequestMapping ("/api/user")
public class UserDetailsRestController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserPhoneNumberService userPhoneNumberService;
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired 
	OrganizationMembershipService organizationMembershipService;
	
	@Autowired
	ProductTypeService productTypeService;
	
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
	public String getUserDetailsNumber(@RequestParam (value="phoneNumber") String phoneNumber) {
		JSONObject responseJsonObject = new JSONObject();
		JSONArray prodTypesArray= new JSONArray();
		UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPhoneNumber(phoneNumber);
		User user = userPhoneNumber.getUser();
		OrganizationMembership organizationMembership = organizationMembershipService.getOrganizationMembershipByUserAndIsAdmin(user, true);
		Organization organization = organizationMembership.getOrganization();
		try {
			responseJsonObject.put("name", user.getName());
			responseJsonObject.put("address",user.getAddress());
			responseJsonObject.put("phoneNumber", userPhoneNumber.getPhoneNumber());
			responseJsonObject.put("abbr", organization.getAbbreviation());
			responseJsonObject.put("email",user.getEmail());
		}
		catch(JSONException e){
			e.printStackTrace();
		}
		List<ProductType> productTypeList = productTypeService.getAllByOrganisation(organization);
		Iterator<ProductType> iterator = productTypeList.iterator();
		while(iterator.hasNext()) {
			ProductType productType = iterator.next();
			prodTypesArray.put(productType.getName());
		}
		try {
			responseJsonObject.put("productTypes", prodTypesArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return responseJsonObject.toString();
	}
	
	
	
}
