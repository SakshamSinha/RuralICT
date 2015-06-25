package app.business.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import app.business.services.VoiceService;
import app.entities.Voice;
import app.util.Utils;

@Controller
public class UploadController {
	
	@Autowired
	VoiceService voiceService;
	
	@Transactional
	@RequestMapping(value="/ruralict/web/{org}/upload", method=RequestMethod.POST)
	public @ResponseBody int handleFileUpload(HttpServletRequest request){
		MultipartHttpServletRequest mRequest;
		mRequest = (MultipartHttpServletRequest) request;
		
		Iterator<String> itr = mRequest.getFileNames();
		
		//only one iteration i.e itr.next() as it has only one file
		MultipartFile mFile = mRequest.getFile(itr.next());
		String fileName = mFile.getOriginalFilename();
		File temp = Utils.saveFile("temp.wav", Utils.getVoiceDir(), mFile);
		File serverFile = new File(Utils.getVoiceDir() +File.separator+ fileName);
		serverFile = Utils.convertToKookooFormat(temp, serverFile);
		
		String url = Utils.getVoiceDirURL() + fileName;
		Voice voice = new Voice(url, true);
		voiceService.addVoice(voice);
		int voiceId = voice.getVoiceId();
		//voiceId returned so that LatestRecordedVoice Table is updated using this field.
		return voiceId;
	    
	}
		
	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public String uploadFile(Model model) {
		return "form";
	}
}