package app.business.controllers.rest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.OrganizationService;
import app.business.services.ProductService;
import app.business.services.ProductTypeService;
import app.entities.Organization;
import app.entities.Product;
import app.entities.ProductType;


@RestController
@RequestMapping("/api")

public class AddProductRestController {
	
	@Autowired 
	OrganizationService organizationService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductTypeService productTypeService;
	
	@RequestMapping(value ="/product/add", method = RequestMethod.POST )
	public String addProduct(@RequestBody String requestBody)
	{
		JSONObject jsonObject = null;
		JSONObject responseJsonObject = new JSONObject();
		String organizationabbr = null;
		Organization organization;
		Product product = new Product();
		try {
			jsonObject = new JSONObject(requestBody);
			organizationabbr=jsonObject.getString("orgabbr");
			product.setName(jsonObject.getString("name"));
			organization = organizationService.getOrganizationByAbbreviation(organizationabbr);
			ProductType productType = productTypeService.getProductTypeByNameAndOrg(jsonObject.getString("productType"), organization);
			product.setUnitRate(Float.parseFloat(jsonObject.getString("rate")));
			product.setProductType(productType);
			product.setQuantity(Integer.parseInt(jsonObject.getString("qty")));
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		try{
			productService.addProduct(product);
		}
		catch(Exception e){
			try {
				responseJsonObject.put("upload", "failure");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			return responseJsonObject.toString();
		}
		try {
			responseJsonObject.put("upload", "success");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return responseJsonObject.toString();
	}

}
