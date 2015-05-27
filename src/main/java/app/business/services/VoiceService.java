package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.VoiceRepository;
import app.entities.Voice;

@Service
public class VoiceService {

	@Autowired
	VoiceRepository voiceRepository;
	
	public void addVoice(Voice voice){
		voiceRepository.save(voice);
	}
	
	public void removeVoice(Voice voice){
		voiceRepository.delete(voice);
	}
	
	public Voice getVoice(int id){
		return voiceRepository.findOne(id);
	}
	
	public List<Voice> getAllVoiceList(){
		return voiceRepository.findAll();
	}
	
	public List<Voice> getUndownloadedVoiceList(){
		return voiceRepository.findByIsDownloaded(false);
	}
}