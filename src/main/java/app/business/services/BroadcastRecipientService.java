package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.BroadcastRecipientRepository;
import app.entities.BroadcastRecipient;
import app.entities.User;
import app.entities.broadcast.Broadcast;

@Service
public class BroadcastRecipientService {

	@Autowired
	BroadcastRecipientRepository broadcastRecipientRepository;
	
	public BroadcastRecipient getBroadcastRecipientById(int broadcastRecipientId){
		return broadcastRecipientRepository.findOne(broadcastRecipientId);
	}
	
	public List<BroadcastRecipient> getAllBroadcastRecipientList(){
		return broadcastRecipientRepository.findAll();
	}
	
	public void addBroadcastRecipient(BroadcastRecipient broadcastRecipient){
		broadcastRecipientRepository.save(broadcastRecipient);
	}
	
	public void removeBroadcastRecipient(BroadcastRecipient broadcastRecipient){
		broadcastRecipientRepository.delete(broadcastRecipient);
	}
	
	public BroadcastRecipient getBroadcastRecipientByUserAndBroadcast(User user, Broadcast broadcast) {
		return broadcastRecipientRepository.findByUserAndBroadcast(user, broadcast);
	}
}
