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
import app.business.services.VoiceService;
import app.business.services.WelcomeMessageService;
import app.entities.Organization;
import app.entities.Voice;
import app.entities.WelcomeMessage;

@Controller
@RequestMapping("/web/{org}")
public class SettingsController {
	
	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	WelcomeMessageService welcomeMessageService;
	
	@Autowired
	VoiceService voiceService;

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
		
		// Convert for getting the files
		MultipartHttpServletRequest mRequest;
		mRequest = (MultipartHttpServletRequest) request;
		
		// Get Parameters passed from AngularJS using FormData
		int organizationid = Integer.parseInt(request.getParameter("orgid"));
		System.out.println("organization id is: " + organizationid);
		Organization organization = organizationService.getOrganizationById(organizationid);
		
		String locale = request.getParameter("locale");
		System.out.println("locale is: " + locale);
		
		// Create Required Entity Objects
		WelcomeMessage welcomeMessage = welcomeMessageService.getbyOrganizationAndLocale(organization, locale);
		
		Voice voice = welcomeMessageService.getVoice(welcomeMessage);
		
		System.out.println("Got the Voice Object");
		System.out.println("Now Uploading the File");
		
		// Upload the file to the given location
		Iterator<String> itr = mRequest.getFileNames();
		while (itr.hasNext()) {
			//org.springframework.web.multipart.MultipartFile
			MultipartFile mFile = mRequest.getFile(itr.next());
			String fileName = mFile.getOriginalFilename();
			System.out.println("*****"+ fileName);
			String workingDir = System.getProperty("user.dir");
			System.out.println("Current working directory : " + workingDir + "/"+ fileName);
			 
			String filePath = workingDir + "/" + fileName;
			//To copy the file to a specific location in machine.
			File file = new File(filePath);
			try {
				FileCopyUtils.copy(mFile.getBytes(), file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //This will copy the file to the specific location.
			
			//Update the Voice Table
			System.out.println("File was successfully uploaded.");
			System.out.println("Now, updating the Voice Table");
			
			// Set the url of the uploaded file in voice table
			voice.setUrl(filePath);
			
			// Finally, Update the voice object in the database
			voiceService.addVoice(voice);
			
			System.out.println("Voice ID is "+voice.getVoiceId());
			System.out.println("Voice URL is " + voice.getUrl());
			
			System.out.println("File URL was updated Successfully in the database");
			
			/*
			 * TODO: What if the organzation does not have entries in the voice table and Welcome Message Table
			 *  We will have to create them first
			 */
			
			
		}
	}
	
	

}