package app.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.LatestRecordedVoiceRepository;
import app.entities.LatestRecordedVoice;
import app.entities.Organization;

@Service
public class LatestRecordedVoiceService {
	
	@Autowired
	LatestRecordedVoiceRepository latestRecordedVoiceRepository;
	
	public LatestRecordedVoice getLatestRecordedVoiceByOrganization(Organization organization){
		return latestRecordedVoiceRepository.findByOrganization(organization);
	}
	
	public LatestRecordedVoice addLatestRecordedVoice(LatestRecordedVoice latestRecordedVoice){
		return latestRecordedVoiceRepository.save(latestRecordedVoice);
	}
	
	public LatestRecordedVoice getLatestRecordedVoice(int latestRecordedVoiceId){
		return latestRecordedVoiceRepository.findOne(latestRecordedVoiceId);
	}
	
	public void removeLatestRecordedVoice(LatestRecordedVoice latestRecordedVoice){
		latestRecordedVoiceRepository.delete(latestRecordedVoice);
	}

}
