package app.business.controllers;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import app.business.services.OrganizationService;
import app.business.services.ProductService;
import app.entities.Organization;
import app.entities.Product;
import app.entities.ProductType;
import app.util.Utils;

@Controller
@RequestMapping("/web/{org}/")
public class ProductsController {

	@Autowired
	OrganizationService organizationService;
	@Autowired
	ProductService productService;
	
	@Transactional
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@RequestMapping(value="/productsPage",method = RequestMethod.GET)
	public String productsPageInitial(@PathVariable String org, Model model) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		List<ProductType> productTypes = productService.getProductTypeList(organization);
		List<Product> products = productService.getProductList(productTypes);
		model.addAttribute("organization",organization);
		model.addAttribute("productTypes",productTypes);
		model.addAttribute("products",products);
		return "productList";
	}
	
	@Transactional
	@RequestMapping(value="/uploadpicture", method=RequestMethod.POST, produces = "text/plain")
	public @ResponseBody String handleFileUpload(HttpServletRequest request) {
		MultipartHttpServletRequest mRequest;
		mRequest = (MultipartHttpServletRequest) request;
		Iterator<String> itr = mRequest.getFileNames();
		
		//only one iteration i.e itr.next() as it has only one file
		MultipartFile mFile = mRequest.getFile(itr.next());
		String fileName = mFile.getOriginalFilename();
		File temp = Utils.saveFile("temp.jpg", Utils.getImageDir(), mFile);
		File serverFile = new File(Utils.getImageDir() +File.separator+ fileName);
		Random randomint = new Random();
		int flag=1;
		do{
			try 
			{
				Files.copy(temp.toPath(), serverFile.toPath());
				flag=1;
			}	
			catch (FileAlreadyExistsException e)
			{
				System.out.println("File already exist. Renaming file and trying again.");
				fileName = fileName.substring(0,fileName.length()-4);
				fileName = fileName + "_" + Integer.toString(randomint.nextInt()) + ".jpg";
				serverFile = new File(Utils.getImageDir() +File.separator+ fileName);
				flag=0;
			}
			catch (IOException e) {
				e.printStackTrace();
				flag=1;
			}
		}while(flag==0);
		
		
		String url = Utils.getImageDirURL() + fileName;
		System.out.println(url);
		return url;
	    
	}

}
