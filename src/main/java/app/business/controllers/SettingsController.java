package app.business.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import app.business.services.OrganizationService;
import app.entities.Organization;

@Controller
@RequestMapping("/web/{org}")
public class SettingsController {
	
	@Autowired
	OrganizationService organizationService;

	@RequestMapping(value="/settingsPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String settingsPage(@PathVariable String org, Model model) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		model.addAttribute("organization", organization);
		return "settings";
	}
	
	@RequestMapping(value="/upload/welcomeMessage", method=RequestMethod.POST)
	@ResponseBody
    public void handleFileUpload(HttpServletRequest request){
		MultipartHttpServletRequest mRequest;
	    mRequest = (MultipartHttpServletRequest) request;
	    
	    Iterator<String> itr = mRequest.getFileNames();
	    while (itr.hasNext()) {
	        //org.springframework.web.multipart.MultipartFile
	        MultipartFile mFile = mRequest.getFile(itr.next());
	        String fileName = mFile.getOriginalFilename();
	        System.out.println("*****"+ fileName);
	        String workingDir = System.getProperty("user.dir");
	 	    System.out.println("Current working directory : " + workingDir + "/"+ fileName);

	        //To copy the file to a specific location in machine.
	        File file = new File(workingDir + "/" + fileName);
	        try {
				FileCopyUtils.copy(mFile.getBytes(), file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //This will copy the file to the specific location.
	    }
	}
    
	

}