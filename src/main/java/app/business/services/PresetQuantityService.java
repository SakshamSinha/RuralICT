package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.PresetQuantityRepository;
import app.entities.Organization;
import app.entities.PresetQuantity;

@Service
public class PresetQuantityService {
	
	@Autowired
	PresetQuantityRepository presetQuantityRepository;
	
	public void addPresetQuantity(PresetQuantity presetQuantity){
		presetQuantityRepository.save(presetQuantity);
	}
	
	public void removePresetQuantity(PresetQuantity presetQuantity){
		presetQuantityRepository.delete(presetQuantity);
	}
	
	public PresetQuantity getPresetQuantityById(int presetQuantityId){
		return presetQuantityRepository.findOne(presetQuantityId);
	}
	
	public List<PresetQuantity> getAllPresetQuantityList(){
		return presetQuantityRepository.findAll();
	}
	
	public List<PresetQuantity> getPresetQuantityList(Organization organization){
		return presetQuantityRepository.findByOrganization(organization);
	}
}
