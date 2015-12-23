package app.business.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.business.services.OrganizationService;
import app.business.services.ProductTypeService;
import app.entities.Organization;
import app.entities.ProductType;

@Controller
@RequestMapping("/web/{org}")
public class ProductTypeController 
{

	@Autowired
	OrganizationService organizationService;
	@Autowired
	ProductTypeService productTypeService;

	@Transactional
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@RequestMapping(value="/productTypePage",method = RequestMethod.GET)
	public String productsPage(@PathVariable String org, Model model) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<ProductType> productTypes = new ArrayList<ProductType>(organization.getProductTypes());
		model.addAttribute("organization",organization);
		model.addAttribute("productTypes", productTypes);
		//No change in model here yet
		return "productTypeList";
	}
	
	@PreAuthorize("hasRole('ADMIN'+#org)") 
	@RequestMapping(value="/prodtypes",method = RequestMethod.GET)
	public @ResponseBody String productList(@PathVariable String org) {

		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<ProductType> prod= productTypeService.getAllByOrganisation(organization);
		Iterator<ProductType> iterator = prod.iterator();
		JSONObject responseJsonObject = new JSONObject();
		JSONArray prodArray=new JSONArray();
		while(iterator.hasNext())
		{
			
			ProductType p = iterator.next();
			JSONObject obj = new JSONObject();
			try{
			obj.put("id",p.getProductTypeId());
			obj.put("name", p.getName());
			}
			catch(JSONException e)
			{
			}
			prodArray.put(obj);
		}
		try {
			responseJsonObject.put("products",prodArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return responseJsonObject.toString();
	}
	@PreAuthorize("hasRole('ADMIN'+#org)") 
	@RequestMapping(value="/prodtypesid",method = RequestMethod.GET)

	public @ResponseBody String productId(@PathVariable String org, @RequestParam("name") String name) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		System.out.println("rec name: "+name);
		ProductType product = productTypeService.getByOrganizationAndName(organization, name);
		System.out.println("Id: "+product.getProductTypeId());
		return "productTypes/"+product.getProductTypeId();
	}
	
}

