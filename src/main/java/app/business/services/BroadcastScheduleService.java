package app.business.services;

import java.sql.Timestamp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.BroadcastScheduleRepository;
import app.entities.BroadcastRecipient;
import app.entities.BroadcastSchedule;
import app.entities.broadcast.Broadcast;

@Service
public class BroadcastScheduleService {
	
	@Autowired
	BroadcastScheduleRepository broadcastScheduleRepository;
	
	@Autowired
	OutboundCallService outboundCallService;
	
	public BroadcastSchedule getBroadcastScheduleById(int broadcastScheduleId){
		return broadcastScheduleRepository.findOne(broadcastScheduleId);
	}
	
	public BroadcastSchedule getBroadcastScheduleByBroadcastId(Broadcast broadcast, Timestamp time){
		return broadcastScheduleRepository.findByBroadcastAndTime(broadcast, time);
	}
	
	public List<BroadcastSchedule> getAllBroadcastScheduleList(){
		return broadcastScheduleRepository.findAll();
	}
	
	public List<BroadcastSchedule> getAllBroadcastScheduleListByBroadcast(Broadcast broadcast){
		return broadcastScheduleRepository.findByBroadcast(broadcast);
	}
	
	public void addBroadcastSchedule(BroadcastSchedule broadcastSchedule){
		broadcastScheduleRepository.save(broadcastSchedule);
	}
	
	public void removeBroadcastSchedule(BroadcastSchedule broadcastSchedule){
		broadcastScheduleRepository.delete(broadcastSchedule);
	}
	
	public BroadcastSchedule getNextBroadcastSchedule(BroadcastRecipient broadcastRecipient, Broadcast broadcast) {
		
		List<BroadcastSchedule> broadcastScheduleList =  broadcastScheduleRepository.findByBroadcastOrderByTimeAsc(broadcast);
		
		if(broadcast.getBroadcastedTime() == null) {
			
			/*
			 * No broadcast has been sent, so get list of schedule and return the first one.
			 */
			return broadcastScheduleList.iterator().next();
		}
		else{
			for(BroadcastSchedule broadcastSchedule : broadcastScheduleList) {
				
				/*
				 * substitute string in next line with the correct string that 
				 * kookoo passes for received calls.
				 */
				if(outboundCallService.getOutboundCallByBroadcastScheduleAndBroadcastRecipient(broadcastSchedule, broadcastRecipient).getStatus() == "") {
					
					/*
					 * if call was picked, get all broadcast schedules with send to all fields true and
					 * which are scheduled after broadcast and return the first one 
					 */
					List<BroadcastSchedule> forcedBroadcastScheduleList = broadcastScheduleRepository.findByBroadcastAndSendToAllTrueAndTimeGreaterThanOrderByTimeAsc(broadcast, broadcast.getBroadcastedTime());
					return forcedBroadcastScheduleList.iterator().next();
				}
			}
			
			/*
			 * If call was not picked at all, get the list of all schedules 
			 * after last broadcast time and return the first one
			 */
			List<BroadcastSchedule> backupBroadcastScheduleList = broadcastScheduleRepository.findByBroadcastAndTimeGreaterThanOrderByTimeAsc(broadcast, broadcast.getBroadcastedTime());
			return backupBroadcastScheduleList.iterator().next();
		
		}
	}
	
	public void setBroadcastScheduleTime(BroadcastSchedule broadcastSchedule, Timestamp time) {
		
		broadcastSchedule.setTime(time);
		broadcastScheduleRepository.save(broadcastSchedule);
	}
	
	public void setBroadcastScheduleSendToAll(BroadcastSchedule broadcastSchedule, boolean sendToAll) {
		
		broadcastSchedule.setSendToAll(sendToAll);
		broadcastScheduleRepository.save(broadcastSchedule);
	}
	
	public void setBroadcastScheduleCancelled(BroadcastSchedule broadcastSchedule, boolean cancelled) {
		
		broadcastSchedule.setCancelled(cancelled);
		broadcastScheduleRepository.save(broadcastSchedule);
	}
	
	 
}
