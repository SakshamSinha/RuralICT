package app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;

import app.business.services.VoiceService;
import app.entities.Voice;


public class DownloadDaemon extends Thread {
	
	
	@Autowired
	VoiceService voiceService;
	
	Long sleepDuration;
	
	
	@Override
	public void run(){
		
		//sleep duration set to 24 hours.
		this.setSleepDuration(new Long(1000*60*60*24));
		
		Timer timer = new Timer();
		System.out.println("Start");
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				
				try {
					List<Voice> undownloadedVoiceFiles = new ArrayList<Voice>(voiceService.getUndownloadedVoiceList());
					
					for(Voice voiceFile : undownloadedVoiceFiles) {
						String fileName = Utils.downloadFile(voiceFile.getUrl(), "/voices/");
						if(fileName != null) {
							System.out.println("Voice File at " + voiceFile.getUrl() + "downloaded to http://www.ruralict.cse.iitb.ac.in/voices/" +fileName);
							voiceFile.setUrl("http://www.ruralict.cse.iitb.ac.in/voices/" +fileName);
							voiceFile.setIsDownloaded(true);
							voiceService.addVoice(voiceFile);
						}
					}
				}
				catch(Exception e) {
					System.out.println("Not Done");
					e.printStackTrace();
				}
			}
		}, 5, this.getSleepDuration());
	}
	
	void setSleepDuration(Long sleepDuration) {
		this.sleepDuration = sleepDuration;
	}
	
	Long getSleepDuration() {
		return this.sleepDuration;
	}
		
}
