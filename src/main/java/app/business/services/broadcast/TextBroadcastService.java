package app.business.services.broadcast;

import java.util.List;

import org.springframework.stereotype.Service;

import app.entities.Group;
import app.entities.Organization;
import app.entities.broadcast.Broadcast;

@Service
public class TextBroadcastService extends BroadcastService{
	public List<Broadcast> getTextBroadcast(Organization organization){
		return broadcastRepository.findByOrganizationAndFormat(organization, "text");
	}
	public List<Broadcast> getTextBroadcast(Group group){
		return broadcastRepository.findByGroupAndFormat(group, "text");
	}
}
