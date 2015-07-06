package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.BroadcastDefaultSettingsRepository;
import app.entities.BroadcastDefaultSettings;
import app.entities.Organization;

@Service
public class BroadcastDefaultSettingsService {
	@Autowired
	BroadcastDefaultSettingsRepository broadcastDefaultSettingsRepository;
	
	public BroadcastDefaultSettings getBroadcastDefaultSettings(int broadcastDefaultSettingsId){
		return broadcastDefaultSettingsRepository.findOne(broadcastDefaultSettingsId);
	}
	
	public List<BroadcastDefaultSettings> getAllBroadcastDefaultSettingsService(){
		return broadcastDefaultSettingsRepository.findAll();
	}
	
	public BroadcastDefaultSettings getBroadcastDefaultSettingByOrganization(Organization organization){
		return broadcastDefaultSettingsRepository.findByOrganization(organization);
	}
}
