package app.business.controllers.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.business.services.OrderService;
import app.business.services.OrganizationService;
import app.business.services.PresetQuantityService;
import app.business.services.ProductService;
import app.business.services.ProductTypeService;
import app.entities.Organization;
import app.entities.PresetQuantity;
import app.entities.Product;
import app.entities.ProductType;


@RestController
@RequestMapping("/api")

public class AddProductTypeRestController {
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	ProductTypeService productTypeService;
	
	@Autowired
	PresetQuantityService presetQuantityService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;
	
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
			/* Enable in future
			 * presetArray = jsonObject.getJSONArray("preset");
			 */
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		/*Dummy value as preset quantity currently serves no purpose*/
		//float preset = (float) 5; 
		ProductType productType = new ProductType();
		Organization organization = organizationService.getOrganizationByAbbreviation(organizationabbr);
		productType.setOrganization(organization);
		productType.setName(productTypeName);
		//System.out.println(presetArray.length());
	/*	Enable in future
	 * for(int i=0; i < presetArray.length();i++)
		{
			PresetQuantity presetQuantity;
			try {
				presetQuantity = new PresetQuantity(organization, productType, Float.parseFloat(presetArray.getString(i)));
				presetQuantities.add(presetQuantity);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Adding...");
		PresetQuantity presetQuantity;
		presetQuantity = new PresetQuantity(organization, productType,preset);
		presetQuantities.add(presetQuantity);
		productType.setPresetQuantities(presetQuantities);  */
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
	
	@RequestMapping(value= "/producttype/edit" ,method = RequestMethod.POST)
	public String editProductType(@RequestBody String requestBody) {
		JSONObject jsonObject = null;
		String organizationabbr = null;
		String productTypeName = null, productTypeNewName=null;
		JSONObject responseJsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(requestBody);
			organizationabbr=jsonObject.getString("orgabbr");
			productTypeName = jsonObject.getString("oldname");
			productTypeNewName = jsonObject.getString("newname");
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		Organization organization = organizationService.getOrganizationByAbbreviation(organizationabbr);
		ProductType productType = productTypeService.getByOrganizationAndName(organization, productTypeName);
		productType.setName(productTypeNewName);
		try{
		productTypeService.addProductType(productType);
		}
		catch(Exception e){
			try {
				responseJsonObject.put("edit", "failure");
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			return responseJsonObject.toString();
		}
		try {
			responseJsonObject.put("edit", "success");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return responseJsonObject.toString();
	
	
	}
	
	@RequestMapping(value= "/producttype/delete" ,method = RequestMethod.POST)
	public String deleteProductType(@RequestBody String requestBody) {
		JSONObject jsonObject = null;
		String organizationabbr = null;
		String productTypeName = null;
		JSONObject responseJsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(requestBody);
			organizationabbr=jsonObject.getString("orgabbr");
			productTypeName = jsonObject.getString("name");
		}
		catch(JSONException e)
		{
			e.printStackTrace();
		}
		Organization organization = organizationService.getOrganizationByAbbreviation(organizationabbr);
		ProductType productType = productTypeService.getByOrganizationAndName(organization, productTypeName);
		try{
		productTypeService.removeProductType(productType);
		}
		catch(Exception e) {
			try {
			deleteProducts(productType);
			ProductType productType2 = productTypeService.getByOrganizationAndName(organization, productTypeName);
			productTypeService.removeProductType(productType2);
			}
			catch(Exception e1){
				try {
					responseJsonObject.put("message", "Product Type cannot be deleted as products of this type have been oredered");
				} catch (JSONException e2) {
					e2.printStackTrace();
				}
				return responseJsonObject.toString();
			}
		}
		try {
			responseJsonObject.put("message","Successfully deleted Product Type");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return responseJsonObject.toString();
		
	}
	
	@Transactional
	public void deleteProducts(ProductType productType) throws Exception {
		List<ProductType> productTypeList = new ArrayList<ProductType>();
		productTypeList.add(productType);
		List <Product> products = productType.getProducts();
		Iterator<Product>iterator = products.iterator();
		while(iterator.hasNext()) {
			Product product = iterator.next();
			productService.removeProduct(product);
		}
	}
}