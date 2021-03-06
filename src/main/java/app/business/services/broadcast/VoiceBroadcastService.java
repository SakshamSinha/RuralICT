package app.business.services.broadcast;

import java.util.List;

import org.springframework.stereotype.Service;

import app.entities.Group;
import app.entities.Organization;
import app.entities.broadcast.Broadcast;

@Service
public class VoiceBroadcastService extends BroadcastService{
	public List<Broadcast> getVoiceBroadcast(Organization organization){
		return broadcastRepository.findByOrganizationAndFormat(organization, "voice");
	}
	List<Broadcast> getVoiceBroadcast(Group group){
		return broadcastRepository.findByGroupAndFormat(group, "voice");
	}

}
