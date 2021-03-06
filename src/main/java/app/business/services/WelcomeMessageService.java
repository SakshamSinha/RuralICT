package app.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.VoiceRepository;
import app.data.repositories.WelcomeMessageRepository;
import app.entities.Organization;
import app.entities.Voice;
import app.entities.WelcomeMessage;

@Service
public class WelcomeMessageService {

	@Autowired
	WelcomeMessageRepository welcomeMessageRepository;
	
	@Autowired
	VoiceRepository voiceRepository;
	
	public WelcomeMessage getByOrganizationAndLocale(Organization organization, String locale) {
		return welcomeMessageRepository.findByOrganizationAndLocale(organization, locale);
	}
	
	public Voice getVoice(WelcomeMessage welcomeMessage){
		return welcomeMessage.getVoice();
	}
	
	public WelcomeMessage addWelcomeMessage(WelcomeMessage welcomeMessage){
		return welcomeMessageRepository.save(welcomeMessage);
	}
	
	public void setWelcomeMessageVoice(WelcomeMessage welcomeMessage, int voiceId){
		Voice voice = voiceRepository.findOne(voiceId);
		welcomeMessage.setVoice(voice);
		welcomeMessageRepository.save(welcomeMessage);
	}
}