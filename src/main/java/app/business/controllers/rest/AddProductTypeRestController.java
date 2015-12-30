package app.business.controllers.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.PresetQuantityService;
import app.business.services.ProductTypeService;
import app.data.repositories.OrganizationRepository;
import app.entities.Organization;
import app.entities.PresetQuantity;
import app.entities.ProductType;


@RestController
@RequestMapping("/api")

public class AddProductTypeRestController {
	
	@Autowired
	OrganizationRepository organizationRepository;
	
	@Autowired
	ProductTypeService productTypeService;
	
	@Autowired
	PresetQuantityService presetQuantityService;
	
	@RequestMapping(value= "/producttype/add" ,method = RequestMethod.POST)
	public String addProductType(@RequestBody String requestBody) {
		JSONObject jsonObject = null;
		String organizationabbr = null;
		String productTypeName = null;
		JSONObject responseJsonObject = new JSONObject();
		List <PresetQuantity> presetQuantities = new ArrayList<PresetQuantity>();
		JSONArray presetArray = null;
		try {
			jsonObject = new JSONObject(requestBody);
			organizationabbr=jsonObject.getString("orgabbr");
			productTypeName = jsonObject.getString("name");
			presetArray = jsonObject.getJSONArray("preset");
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		//Float [] preset = new Float[presetArray.length()];
		
		
		ProductType productType = new ProductType();
		Organization organization = organizationRepository.findByAbbreviation(organizationabbr);
		productType.setOrganization(organization);
		productType.setName(productTypeName);
		System.out.println(presetArray.length());
		for(int i=0; i < presetArray.length();i++)
		{
			PresetQuantity presetQuantity;
			try {
				presetQuantity = new PresetQuantity(organization, productType, Float.parseFloat(presetArray.getString(i)));
				presetQuantities.add(presetQuantity);
				//presetQuantityService.addPresetQuantity(presetQuantity);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Adding...");
		
		productType.setPresetQuantities(presetQuantities);
		try {
			productTypeService.addProductType(productType);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try {
				responseJsonObject.put("upload", "failure");
				return responseJsonObject.toString();
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		
		}
		Iterator <PresetQuantity>iterator = presetQuantities.iterator();
		while(iterator.hasNext())
		{
			presetQuantityService.addPresetQuantity(iterator.next());
		}
		try {
			responseJsonObject.put("upload", "success");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return responseJsonObject.toString();
		
	}
}