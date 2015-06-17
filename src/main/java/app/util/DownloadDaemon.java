package app.util;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import app.business.services.VoiceService;
import app.business.services.springcontext.SpringContextBridge;
import app.entities.Voice;


public class DownloadDaemon extends Thread {
	
	Long sleepDuration;
	
	public DownloadDaemon() {}
	
	@Override
	public void run(){
		
		//sleep duration set to 24 hours.
		this.setSleepDuration(new Long(1000*60*60*24));
		
		Timer timer = new Timer();
		System.out.println("Download Daemon Started Successfully");
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("Download Daemon Activated at " + (new Date()));
				try {
					VoiceService voiceService = SpringContextBridge.services().getVoiceService();
					
					List<Voice> undownloadedVoiceFiles = new ArrayList<Voice>(voiceService.getUndownloadedVoiceList());
					
					for(Voice voiceFile : undownloadedVoiceFiles) {
						
						System.out.println("Downloading file at " + voiceFile.getUrl());
						String fileName = Utils.downloadFile(voiceFile.getUrl(), "./voices/");
						
						if(fileName != null) {
							System.out.println("Voice File at " + voiceFile.getUrl() + " downloaded to http://www.ruralict.cse.iitb.ac.in/voices/" +fileName);
							voiceFile.setUrl("http://www.ruralict.cse.iitb.ac.in/voices/" +fileName);
							//voiceFile.setIsDownloaded(true);
							//voiceService.addVoice(voiceFile);
						}
					}
				}
				catch(MalformedURLException e) {
					System.out.println("File could not be downloaded because of error in URL: (" + e.getLocalizedMessage()+")");
				}
				catch(Exception e) {
					System.out.println("Error while downloading file.");
					e.printStackTrace();
				}
				System.out.println("Deactivating Download Daemon");
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
