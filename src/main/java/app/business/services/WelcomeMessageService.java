package app.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.WelcomeMessageRepository;
import app.entities.Organization;
import app.entities.Voice;
import app.entities.WelcomeMessage;

@Service
public class WelcomeMessageService {

	@Autowired
	WelcomeMessageRepository welcomeMessageRepository;
	
	public WelcomeMessage getbyOrganizationAndLocale(Organization organization, String locale) {
		return welcomeMessageRepository.findByOrganizationAndLocale(organization, locale);
	}
	
	public Voice getVoice(WelcomeMessage welcomeMessage){
		return welcomeMessage.getVoice();
	}
}