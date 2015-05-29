package app.business.services.broadcast;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;

import app.data.repositories.BroadcastRepository;
import app.entities.broadcast.Broadcast;

public class BroadcastService {
	
	@Autowired
	BroadcastRepository broadcastRepository;
	
	public void setBroadcastTime(Timestamp timestamp, Broadcast broadcast) {
		
		broadcast.setBroadcastedTime(timestamp);
		broadcastRepository.save(broadcast);
	}
	
	public void addBroadcast(Broadcast broadcast) {
		
		broadcastRepository.save(broadcast);
	}
	
	public void deleteBroadcast(Broadcast broadcast) {
		
		broadcastRepository.delete(broadcast);
	}
	
	
}
