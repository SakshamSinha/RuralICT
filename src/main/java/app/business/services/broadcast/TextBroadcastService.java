package app.business.services.broadcast;

import java.util.List;

import app.entities.Group;
import app.entities.Organization;
import app.entities.broadcast.Broadcast;

public class TextBroadcastService extends BroadcastService{
	public List<Broadcast> getTextBroadcast(Organization organization){
		return broadcastRepository.findByOrganizationAndFormat(organization, "text");
	}
	List<Broadcast> getTextBroadcast(Group group){
		return broadcastRepository.findByGroupAndFormat(group, "text");
	}
}
