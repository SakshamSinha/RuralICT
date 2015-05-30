package app.business.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.OutboundCallRepository;
import app.entities.BroadcastRecipient;
import app.entities.BroadcastSchedule;
import app.entities.Group;
import app.entities.Organization;
import app.entities.OutboundCall;

@Service
public class OutboundCallService {
	@Autowired
	OutboundCallRepository outboundCallRepository;
	
	public List<OutboundCall> getOutboundCallList(Organization organization){
		//**********************************************
		return null;
	}
	
	public List<OutboundCall> getOutboundCallList(Group group){
		//************************************************
		return null;
	}
	
	public void addOutboundCall(OutboundCall outboundCall){
		outboundCallRepository.save(outboundCall);
	}
	
	public void removeOutboundCall(OutboundCall outboundCall){
		outboundCallRepository.delete(outboundCall);
	}
	
	public OutboundCall getOutboundCallByBroadcastScheduleAndBroadcastRecipient(BroadcastSchedule broadcastSchedule, BroadcastRecipient broadcastRecipient){
		
		return outboundCallRepository.findByBroadcastScheduleAndBroadcastRecipient(broadcastSchedule, broadcastRecipient);
	}
}