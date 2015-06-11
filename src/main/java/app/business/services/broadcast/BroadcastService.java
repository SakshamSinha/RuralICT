package app.business.services.broadcast;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import app.data.repositories.BroadcastRepository;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.Organization;
import app.entities.User;
import app.entities.broadcast.Broadcast;
import app.entities.broadcast.VoiceBroadcast;

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
	
	public Broadcast getTopBroadcast(User user, Organization organization) {
		List<GroupMembership> groupMembershipList = user.getGroupMemberships();
		List<Group> groupList = new ArrayList<Group>();
		for(GroupMembership groupMembership: groupMembershipList) {
			groupList.add(groupMembership.getGroup());
		}
		
		return broadcastRepository.findTopByGroupInAndOrganization(groupList, organization, (new Sort(Sort.Direction.DESC, "broadcastedTime")));
		
	}
	
	
}
