package app.business.services.broadcast;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.data.repositories.BroadcastRepository;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.Organization;
import app.entities.User;
import app.entities.broadcast.Broadcast;
import app.entities.broadcast.VoiceBroadcast;

@Service
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
	


	@Transactional
	public Broadcast getTopBroadcast(User user, Organization organization, String format) {

		List<Group> groupList = new ArrayList<Group>();
		for(GroupMembership groupMembership: user.getGroupMemberships()) {
			groupList.add(groupMembership.getGroup());
		}
		

		
		return broadcastRepository.findTopByGroupInAndOrganizationAndFormat(groupList, organization, format , (new Sort(Sort.Direction.DESC, "broadcastedTime")));

	}
	
	
}
