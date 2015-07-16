package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.BillLayoutSettingsRepository;
import app.entities.BillLayoutSettings;
import app.entities.Organization;

@Service
public class BillLayoutSettingsService {
	
	@Autowired
	BillLayoutSettingsRepository billLayoutSettingsRepository;
	
	public BillLayoutSettings getBillLayoutSettings(int billLayoutSettingsId){
		return billLayoutSettingsRepository.findOne(billLayoutSettingsId);
	}
	
	public List<BillLayoutSettings> getAllBillLayoutSettings(){
		return billLayoutSettingsRepository.findAll();
	}
	
	public BillLayoutSettings getBillLayoutSettingsByOrganization(Organization organization){
		return billLayoutSettingsRepository.findByOrganization(organization);
	}
	
	public BillLayoutSettings addBillLayoutSettings(BillLayoutSettings billLayoutSettings){
		return billLayoutSettingsRepository.save(billLayoutSettings);
	}
	
	public void removeBillLayoutSettings(BillLayoutSettings billLayoutSettings){
		billLayoutSettingsRepository.delete(billLayoutSettings);
	}
	
	public BillLayoutSettings updateBillLayoutSettingsHeaderContent(BillLayoutSettings billLayoutSettings, String headerContent){
		billLayoutSettings.setHeaderContent(headerContent);
		return billLayoutSettingsRepository.save(billLayoutSettings);
	}
	
	public BillLayoutSettings updateBillLayoutSettingsFooterContent(BillLayoutSettings billLayoutSettings, String footerContent){
		billLayoutSettings.setFooterContent(footerContent);
		return billLayoutSettingsRepository.save(billLayoutSettings);
	}
}
