package app.business.controllers;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import app.business.services.VoiceService;
import app.entities.Voice;
import app.util.Utils;

@Controller
public class UploadController {
	
	@Autowired
	VoiceService voiceService;
	
	@Transactional
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public @ResponseBody int uploadAudio(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
		try {
			File temp = Utils.saveFile("temp.wav", Utils.getVoiceDir(), file);
			File serverFile = new File(Utils.getVoiceDir() + File.separator + name);
					
			serverFile = Utils.convertToKookooFormat(temp, serverFile);
			
			String url = Utils.getVoiceDirURL() + name;
			
			Voice voice = new Voice(url, true);
			return voiceService.addVoice(voice).getVoiceId();
			
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public String uploadFile(Model model) {
		return "form";
	}
}
