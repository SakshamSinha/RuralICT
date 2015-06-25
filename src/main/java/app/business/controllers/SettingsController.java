package app.business.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import app.util.Utils;

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
	
	@RequestMapping(value="/getwelcomeMessageUrl", method=RequestMethod.POST)
	@Transactional
	public @ResponseBody List<String> getVoiceObject(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
	
		
		// Get Parameters passed from AngularJS using FormData
		int organizationid = Integer.parseInt(request.getParameter("orgid"));
		System.out.println("organization id is: " + organizationid);
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
	
	@RequestMapping(value="/upload/welcomeMessage", method=RequestMethod.POST, produces = "text/plain")
	@Transactional
	public @ResponseBody String handleFileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		// Convert for getting the files
		MultipartHttpServletRequest mRequest;
		mRequest = (MultipartHttpServletRequest) request;
		
		// Get Parameters passed from AngularJS using FormData
		int organizationid = Integer.parseInt(request.getParameter("orgid"));
		System.out.println("organization id is: " + organizationid);
		Organization organization = organizationService.getOrganizationById(organizationid);
		
		// Get the uploaded file and its name
		Iterator<String> itr = mRequest.getFileNames();
        MultipartFile uploadedAudioFile = mRequest.getFile(itr.next());
        
        
        if(!uploadedAudioFile.getContentType().equals("audio/wav"))
        {
        	// Take some action in the frontend Part
        	// like an alert box saying please upload wav file
        	return "-1";
        }
        
        String fileName = uploadedAudioFile.getOriginalFilename();
 		
		//MultipartFile uploadFile = mRequest.getFile(request.getPart("file"));
		System.out.println("MultiPart file name = " + fileName);
		
		String locale = request.getParameter("locale");
		System.out.println("locale is: " + locale);
		
		// Create Required Entity Objects
		WelcomeMessage welcomeMessage = welcomeMessageService.getbyOrganizationAndLocale(organization, locale);
		
		File currentAudioFile = new File(welcomeMessage.getVoice().getUrl());
		String currentAudioFileName = currentAudioFile.getName();
		
		System.out.println("Current Audio File Name: " + currentAudioFileName);
		
		System.out.println("Got the Voice Object");
		System.out.println("Now Uploading the File");
		
		System.out.println("Content Type: " + uploadedAudioFile.getContentType());
		
		// Save as Temporary File and Convert to Kuckoo Format
		File temp = Utils.saveFile("temp.wav", Utils.getVoiceDir(), uploadedAudioFile);
		
		// Change the 'serverFile' variable to path on the server when application is deployed on server
		File serverFile = new File(Utils.getVoiceDir() + File.separator + fileName);
		serverFile = Utils.convertToKookooFormat(temp, serverFile);
		
		// The below code will actually copy the uploaded file to specific location on the server
		//FileCopyUtils.copy(uploadedAudioFile.getBytes(), serverFile);
		
		// Get the current Working Directory and the full Filepath
		String serverFilePath = "http://ruralict.cse.iitb.ac.in/Downloads/voice/" + fileName;

		// Check if the newly uploaded file is same as the previous File
		if(currentAudioFileName == fileName)
		{
			System.out.println("You Uploaded the same File");
		}
	
		//Update the Voice Table
		System.out.println("File was successfully uploaded.");
		
		System.out.println("Now, updating the Voice Table");
		
		// Create a new Voice Object
		Voice voice = new Voice(serverFilePath, true);
		
		// Add the voice object in the database
		voiceService.addVoice(voice);
	
		System.out.println("Now, Updating Welcome Message Table");
		
		// Update the Welcome Message
		welcomeMessage.setVoice(voice);
		welcomeMessageService.addWelcomeMessage(welcomeMessage);
		
		System.out.println("Welcome Message was successfully Uploaded and Updated.");
		return voice.getUrl();
			
		}
	}
