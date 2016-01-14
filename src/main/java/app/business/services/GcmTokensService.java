package app.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.GcmTokensRepository;
import app.entities.GcmTokens;

@Service
public class GcmTokensService {
	
	@Autowired
	GcmTokensRepository gcmTokensRepository;
	
	public GcmTokens getByPhoneNumber(String number) {
		return gcmTokensRepository.findByNumber(number);
	}

	public void addToken(GcmTokens gcmToken) {
		gcmTokensRepository.save(gcmToken);
	}
	
	public void removeToken(GcmTokens gcmToken) {
		gcmTokensRepository.delete(gcmToken);
	}
}
