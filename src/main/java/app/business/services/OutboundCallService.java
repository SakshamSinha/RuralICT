package app.business.services;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.BroadcastScheduleRepository;
import app.data.repositories.OutboundCallRepository;
import app.entities.BroadcastRecipient;
import app.entities.BroadcastSchedule;
import app.entities.Group;
import app.entities.Organization;
import app.entities.OutboundCall;
import app.entities.broadcast.Broadcast;

@Service
public class OutboundCallService {
	
	@Autowired
	OutboundCallRepository outboundCallRepository;
	
	@Autowired
	BroadcastScheduleRepository	broadcastScheduleRepository;
	
	public List<OutboundCall> getOutboundCallList(Organization organization){		
		return outboundCallRepository.findAll();
	}
	
	public List<OutboundCall> getOutboundCallList(Group group){
		List<OutboundCall> outboundCallList = new ArrayList<OutboundCall>(outboundCallRepository.findByBroadcastRecipient_Broadcast_Group(group));
		return outboundCallList;
	}
	
	public OutboundCall addOutboundCall(OutboundCall outboundCall){
		return outboundCallRepository.save(outboundCall);
	}
	
	public void removeOutboundCall(OutboundCall outboundCall){
		outboundCallRepository.delete(outboundCall);
	}
	
	public OutboundCall getOutboundCallByBroadcastScheduleAndBroadcastRecipient(BroadcastSchedule broadcastSchedule, BroadcastRecipient broadcastRecipient){
		
		return outboundCallRepository.findByBroadcastScheduleAndBroadcastRecipient(broadcastSchedule, broadcastRecipient);
	}
	
	public void scheduleNextOutboundCall(OutboundCall prevOutboundCall) {
		
		Broadcast broadcast = prevOutboundCall.getBroadcastSchedule().getBroadcast();
		BroadcastRecipient broadcastRecipient = prevOutboundCall.getBroadcastRecipient();
		BroadcastSchedule nextBroadcastSchedule = new BroadcastSchedule();
				
		if(prevOutboundCall.getStatus().equals("answered")) {
			
			/*
			 * if call was picked, get all broadcast schedules with send to all fields true and
			 * which are scheduled after broadcast and return the first one 
			 */
			List<BroadcastSchedule> forcedBroadcastScheduleList = broadcastScheduleRepository.findByBroadcastAndSendToAllTrueAndTimeGreaterThanOrderByTimeAsc(broadcast, broadcast.getBroadcastedTime());
			nextBroadcastSchedule = forcedBroadcastScheduleList.iterator().next();
		}
		else {
	
			/*
			 * If call was not picked at all, get the list of all schedules 
			 * after last broadcast time and return the first one
			 */
			List<BroadcastSchedule> backupBroadcastScheduleList = broadcastScheduleRepository.findByBroadcastAndTimeGreaterThanOrderByTimeAsc(broadcast, broadcast.getBroadcastedTime());
			nextBroadcastSchedule = backupBroadcastScheduleList.iterator().next();

		}
		this.addOutboundCall(new OutboundCall(broadcastRecipient, nextBroadcastSchedule, null, null, 0));
	}
	
	public void setOutboundCallStatus(OutboundCall outboundCall, String status){
		
		outboundCall.setStatus(status);
		outboundCallRepository.save(outboundCall);
	}
	
	public void setOutboundCallStatusDetail(OutboundCall outboundCall, String statusDetail){
		
		outboundCall.setStatusDetail(statusDetail);
		outboundCallRepository.save(outboundCall);
	}
	
	public void setOutboundCallDuration(OutboundCall outboundCall, int duration){
		
		outboundCall.setDuration(duration);
		outboundCallRepository.save(outboundCall);
	}


}