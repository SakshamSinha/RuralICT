package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.LatestBroadcastableVoiceRepository;
import app.entities.Group;
import app.entities.InboundCall;
import app.entities.LatestBroadcastableVoice;
import app.entities.Organization;

@Service
public class LatestBroadcastableVoiceService {
	
	@Autowired
	LatestBroadcastableVoiceRepository latestBroadcastableVoiceRepository;
	
	public LatestBroadcastableVoice getLatestBroadcastableVoiceByTime(Organization organization, Group group){
		return latestBroadcastableVoiceRepository.findTopByOrganizationAndGroupOrderByTimeDesc(organization,group);
	}
	
	public LatestBroadcastableVoice getLatestBroadcastableVoiceByOrganizationAndGroup(Organization organization, Group group){
		return latestBroadcastableVoiceRepository.findByOrganizationAndGroup(organization,group);
	}
	
	public LatestBroadcastableVoice addLatestBroadcastableVoice(LatestBroadcastableVoice latestBroadcastableVoice){
		return latestBroadcastableVoiceRepository.save(latestBroadcastableVoice);
	}
	
	public LatestBroadcastableVoice getLatestBroadcastableVoice(int latestBroadcastableVoiceId){
		return latestBroadcastableVoiceRepository.findOne(latestBroadcastableVoiceId);
	}
	
	public void removeLatestBroadcastableVoice(LatestBroadcastableVoice latestBroadcastableVoice){
		latestBroadcastableVoiceRepository.delete(latestBroadcastableVoice);
	}

}
