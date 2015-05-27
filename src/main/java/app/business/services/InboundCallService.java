package app.business.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.repositories.InboundCallRepository;
import app.entities.Organization;
import app.entities.InboundCall;
import app.entities.Group;

@Service
public class InboundCallService {
	@Autowired
	InboundCallRepository inboundCallRepository;
	
	
	public List<InboundCall> getInboundCallList(Organization organization){
	 	return inboundCallRepository.findByOrganization(organization);
	}
	
	public List<InboundCall> getInboundCallList(Group group){
		//************************************************
		return null;
	}
	
	public void addInboundCall(InboundCall inboundCall){
		inboundCallRepository.save(inboundCall);
	}
	
	public void removeInboundCall(InboundCall inboundCall){
		inboundCallRepository.delete(inboundCall);
	}
	
}