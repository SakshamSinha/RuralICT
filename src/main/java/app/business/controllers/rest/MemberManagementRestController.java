package app.business.controllers.rest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.OrganizationMembershipService;
import app.business.services.OrganizationService;
import app.business.services.UserService;
import app.entities.Organization;
import app.entities.OrganizationMembership;
import app.entities.User;

@RestController
@RequestMapping("/app")
public class MemberManagementRestController {
	
	@Autowired
	OrganizationMembershipService organizationMembershipService;
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	UserService userService;
	
	
	@RequestMapping(value = "/approve",method = RequestMethod.POST )
	public String approveMember(@RequestBody String requestBody) {
		JSONObject responseJsonObject = new JSONObject();
		String organizationabbr = null;
		int userId = 0;
		try{
			JSONObject object = new JSONObject(requestBody);
			organizationabbr = object.getString("orgabbr");
			userId = object.getInt("userId");
		}
		catch(JSONException e) {
			e.printStackTrace();
		}
		try {
		Organization organization = organizationService.getOrganizationByAbbreviation(organizationabbr);
		User user = userService.getUser(userId);
		OrganizationMembership organizationMembership = organizationMembershipService.getUserOrganizationMembership(user, organization);
		organizationMembership.setStatus(1);
		organizationMembershipService.addOrganizationMembership(organizationMembership);
		}
		catch (Exception e) {
			try {
				responseJsonObject.put("response", "Failed");
				return responseJsonObject.toString();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		try {
			responseJsonObject.put("response", "Member Approved");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return responseJsonObject.toString();	
	}
	
	
	@RequestMapping(value = "/approveAll",method = RequestMethod.POST )
	public String approveAllMembers(@RequestBody String requestBody) {
		
		JSONObject responseJsonObject = new JSONObject();
		String organizationabbr = null;
		try {
			JSONObject object = new JSONObject(requestBody);
			organizationabbr = object.getString("orgabbr");
			Organization organization = organizationService.getOrganizationByAbbreviation(organizationabbr);
			JSONArray jsonArray = object.getJSONArray("userIds");
			for(int i=0; i < jsonArray.length();i++) {
				int userId = jsonArray.getInt(i);
				User user = userService.getUser(userId);
				OrganizationMembership organizationMembership = organizationMembershipService.getUserOrganizationMembership(user, organization);
				organizationMembership.setStatus(1);
				organizationMembershipService.addOrganizationMembership(organizationMembership);
				
			}
		}
		catch(Exception e) {
			try {
				e.printStackTrace();
				responseJsonObject.put("response", "Failed");
				return responseJsonObject.toString();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		try {
			responseJsonObject.put("response", "Members Approved");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return responseJsonObject.toString();	
	}
	
}
