package app.business.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import app.business.services.OrganizationMembershipService;
import app.business.services.OrganizationService;
import app.business.services.UserPhoneNumberService;
import app.business.services.UserService;
import app.business.services.VoiceService;
import app.business.services.WelcomeMessageService;
import app.entities.Organization;
import app.entities.User;
import app.entities.UserPhoneNumber;
import app.entities.Voice;
import app.entities.WelcomeMessage;
import app.util.Utils;

@Controller
@RequestMapping("/web/{org}")
public class SettingsController {
	
	@Autowired
	OrganizationService organizationService;

	@Autowired
	WelcomeMessageService welcomeMessageService;
	
	@Autowired
	OrganizationMembershipService organizationMembershipService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserPhoneNumberService userPhoneNumberService;

	@Autowired
	VoiceService voiceService;

	@RequestMapping(value="/settingsPage")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	public String settingsPage(@PathVariable String org, Model model) {
		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		User user = userService.getCurrentUser();
		UserPhoneNumber userPhoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(user);
		model.addAttribute("user",user);
		model.addAttribute("organization", organization);
		model.addAttribute("userPhoneNumber",userPhoneNumber);
		return "settings";
	}

	@RequestMapping(value="/getwelcomeMessageUrl", method=RequestMethod.POST)
	@Transactional
	public @ResponseBody List<String> getVoiceObject(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		// Get Parameters passed from AngularJS using FormData
		int organizationid = Integer.parseInt(request.getParameter("orgid"));
		Organization organization = organizationService.getOrganizationById(organizationid);

		WelcomeMessage englishMessage = welcomeMessageService.getbyOrganizationAndLocale(organization,"en");
		WelcomeMessage marathiMessage = welcomeMessageService.getbyOrganizationAndLocale(organization, "mr");
		WelcomeMessage hindiMessage = welcomeMessageService.getbyOrganizationAndLocale(organization, "hi");

		List<String> voices = new ArrayList<String>();
		voices.add(englishMessage.getVoice().getUrl());
		voices.add(marathiMessage.getVoice().getUrl());
		voices.add(hindiMessage.getVoice().getUrl());

		return voices;
		}
	
	@RequestMapping(value="/updateUser", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	@ResponseBody
	public void updateUser(@PathVariable String org, @RequestBody Map<String,String> profileSettingDetails) {

		Organization organization = organizationService.getOrganizationByAbbreviation(org);
		
  
		// Get the input parameters from AngularJS
		String name = profileSettingDetails.get("name");
		String email = profileSettingDetails.get("email");
		String phone = profileSettingDetails.get("phone");
		String address = profileSettingDetails.get("city");
		String password = profileSettingDetails.get("password");
       String role ="admin";
       
       User user = userService.getCurrentUser();

   		// Add the new User to database
		user.setName(name);
		user.setAddress(address);
		user.setEmail(email);
		user.setSha256Password(password);
		userService.addUser(user);

	//TODO -- convert password in sha256 format
		
		
		// First Remove the Previous Primary Phone Number
		UserPhoneNumber previousPrimaryPhoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(user);
		userPhoneNumberService.removeUserPhoneNumber(previousPrimaryPhoneNumber);
				
		// Then add the new Primary number to the database
		UserPhoneNumber newPrimaryPhoneNumber = new UserPhoneNumber(user, phone, true);
		userPhoneNumberService.addUserPhoneNumber(newPrimaryPhoneNumber);
		

		
	}

	@RequestMapping(value="/upload/welcomeMessage", method=RequestMethod.POST, produces = "text/plain")
	@Transactional
	public @ResponseBody String handleFileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		// Convert for getting the files
		MultipartHttpServletRequest mRequest;
		mRequest = (MultipartHttpServletRequest) request;

		// Get Parameters passed from AngularJS using FormData
		int organizationid = Integer.parseInt(request.getParameter("orgid"));
		Organization organization = organizationService.getOrganizationById(organizationid);

		// Get the uploaded file and its name
		try
		{
			Iterator<String> itr = mRequest.getFileNames();
			MultipartFile uploadedAudioFile = mRequest.getFile(itr.next());

			// Check if the file is Empty or not
			if(uploadedAudioFile.isEmpty())
			{
				return "-1";
			}
			
			if(!uploadedAudioFile.getContentType().equals("audio/wav"))
			{
				// Take some action in the frontend Part
				// like an alert box saying please upload wav file
				return "-3";
			}

			if(uploadedAudioFile.getSize() > 10000000)
			{
				return "-2";
			}

			String fileName = uploadedAudioFile.getOriginalFilename();

			String locale = request.getParameter("locale");

			// Create Required Entity Objects
			WelcomeMessage welcomeMessage = welcomeMessageService.getbyOrganizationAndLocale(organization, locale);

			String serverFolder = "/home/ruralivrs/Ruralict/apache-tomcat-7.0.42/webapps/Downloads/voices/welcomeMessage";

			// Save as Temporary File and Convert to Kuckoo Format
			File temp = Utils.saveFile("temp.wav", serverFolder, uploadedAudioFile);

			// Change the 'serverFile' variable to path on the server when application is deployed on server
			File serverFile = new File(serverFolder + File.separator + fileName);
			serverFile = Utils.convertToKookooFormat(temp, serverFile);

			// The below code will actually copy the uploaded file to specific location on the server
			FileCopyUtils.copy(uploadedAudioFile.getBytes(), serverFile);

			// Get the current Working Directory and the full Filepath
			String databaseFileUrl = "http://ruralict.cse.iitb.ac.in/Downloads/voice/welcomeMessage/" + fileName;

			// Create a new Voice Object
			Voice voice = new Voice(databaseFileUrl, true);

			// Add the voice object in the database
			voiceService.addVoice(voice);

			// Update the Welcome Message
			welcomeMessage.setVoice(voice);
			welcomeMessageService.addWelcomeMessage(welcomeMessage);

			System.out.println("Welcome Message was successfully Uploaded and Updated.");
			return voice.getUrl();
		}
		catch(NoSuchElementException e)
		{
			return "-1";
		}
	}
}
