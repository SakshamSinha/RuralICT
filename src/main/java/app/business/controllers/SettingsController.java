package app.business.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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

	@Autowired
	PasswordEncoder passwordEncoder;

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

		WelcomeMessage englishMessage = welcomeMessageService.getByOrganizationAndLocale(organization,"en");
		WelcomeMessage marathiMessage = welcomeMessageService.getByOrganizationAndLocale(organization, "mr");
		WelcomeMessage hindiMessage = welcomeMessageService.getByOrganizationAndLocale(organization, "hi");

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
		UserPhoneNumber previousPrimaryPhoneNumber = userPhoneNumberService.getUserPrimaryPhoneNumber(user);

		// Add the new User to database
		user.setName(name);
		user.setAddress(address);
		user.setEmail(email);
		user.setSha256Password(passwordEncoder.encode(password));


		// Find if the number is already present in the database 
		// If present report it to the Frontend
		if(!userPhoneNumberService.findPreExistingPhoneNumber(phone))
		{
			userService.addUser(user);
		}

		else {

			// Then add the new Primary number to the database
			previousPrimaryPhoneNumber.setPhoneNumber(phone);
			userPhoneNumberService.addUserPhoneNumber(previousPrimaryPhoneNumber);
		}
	}

	@RequestMapping(value="/resetwelcomeMessageUrl")
	@Transactional
	public @ResponseBody List<String> resetWelcomeMessageUrl(@PathVariable String org) {

		Organization organization = organizationService.getOrganizationByAbbreviation(org);

		WelcomeMessage englishMessage = welcomeMessageService.getByOrganizationAndLocale(organization,"en");
		WelcomeMessage marathiMessage = welcomeMessageService.getByOrganizationAndLocale(organization, "mr");
		WelcomeMessage hindiMessage = welcomeMessageService.getByOrganizationAndLocale(organization, "hi");

		welcomeMessageService.setWelcomeMessageVoice(englishMessage, 1);
		welcomeMessageService.setWelcomeMessageVoice(hindiMessage, 2);
		welcomeMessageService.setWelcomeMessageVoice(marathiMessage, 3);

		String englishMessageUrl = englishMessage.getVoice().getUrl();
		String hindiMessageUrl = hindiMessage.getVoice().getUrl();
		String marathiMessageUrl = marathiMessage.getVoice().getUrl();

		List<String> defaultVoiceUrl = new ArrayList<String>();
		defaultVoiceUrl.add(englishMessageUrl);
		defaultVoiceUrl.add(marathiMessageUrl);
		defaultVoiceUrl.add(hindiMessageUrl);

		return defaultVoiceUrl;

	}

	@RequestMapping(value="/upload/welcomeMessage", method=RequestMethod.POST, produces = "text/plain")
	@Transactional
	public @ResponseBody String handleFileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{

		// Convert for getting the files
		MultipartHttpServletRequest mRequest;
		mRequest = (MultipartHttpServletRequest) request;

		String[] supportedAudioFiletypes = new String[]{"audio/wav"};

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

			// Check if the file is of supported audio formats
			if(!Arrays.asList(supportedAudioFiletypes).contains(uploadedAudioFile.getContentType()))
			{
				// Take some action in the frontend Part
				// like an alert box saying please upload wav file
				return "-3";
			}

			//Check if the File Size is greater than 5MB
			// As if we upload a audio file much greater in size, kuckoo takes a long time to load
			if(uploadedAudioFile.getSize() > 5000000)
			{
				return "-2";
			}

			// Get the File Name
			String fileName = uploadedAudioFile.getOriginalFilename();

			String locale = request.getParameter("locale");


			String serverFolder = "/home/ruralivrs/Ruralict/apache-tomcat-7.0.42/webapps/Downloads/voices/welcomeMessage";

			// Save as Temporary File and Convert to Kuckoo Format
			File temp = new File(serverFolder + File.separator + "temp.wav" );

			// Remove spaces as kuckoo doesn't allow filename with spaces
			fileName = fileName.replaceAll("\\s+","");

			// Change Extension of the file to wav
			fileName = fileName.substring(0,fileName.length()-3);
			fileName = fileName + "wav";

			// Get the current Working Directory and the full Filepath
			String databaseFolder = "http://ruralict.cse.iitb.ac.in/Downloads/voices/welcomeMessage/";
			String databaseFileUrl = databaseFolder + fileName;


			// Check if the file is already present or not and rename it accordingly
			Voice previousFileSameName  = voiceService.getVoicebyUrl(databaseFileUrl);

			if(previousFileSameName != null)
			{
				// Insert some-random number to automatically rename the file
				fileName = fileName.substring(0,fileName.length()-4);
				Random randomint = new Random();
				fileName = fileName + "_" + Integer.toString(randomint.nextInt()) + ".wav";
				databaseFileUrl = databaseFolder + fileName;

			}

			// Create a new file and convert the file to appropriate Kuckoo Format
			File serverFile = new File(serverFolder + File.separator + fileName);
			uploadedAudioFile.transferTo(temp);
			serverFile = Utils.convertToKookooFormat(temp, serverFile);

			// Create a new Voice Object
			Voice voice = new Voice(databaseFileUrl, true);

			// Add the voice object in the database
			voiceService.addVoice(voice);

			// Update the Welcome Message
			WelcomeMessage welcomeMessage = welcomeMessageService.getByOrganizationAndLocale(organization, locale);
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
