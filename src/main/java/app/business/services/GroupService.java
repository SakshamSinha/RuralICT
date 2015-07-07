package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.data.repositories.GroupRepository;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.Organization;

@Service
public class GroupService {
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	GroupMembershipService groupMembershipService;
	
	public Group getGroup(int groupId){
		return groupRepository.findOne(groupId);
	}
	
	public void addGroup(Group group){
		groupRepository.save(group);
	}
	
	@Transactional
	public void removeGroup(Group group){
		List<GroupMembership> groupMembershipList = group.getGroupMemberships();
		for(GroupMembership groupMembership: groupMembershipList) {
			groupMembershipService.removeGroupMembership(groupMembership);
		}
		
		groupRepository.delete(group);
	}
	
	public void updateParentGroup(Group group,Group newParentGroup){
		group.getParentGroup().removeSubGroup(group);
		newParentGroup.addSubGroup(group);
	}
	
	public List<Group> getAllGroupList(){
		return groupRepository.findAll();
	}
	
	public List<Group> getAllGroupListSortedByName(){
		return groupRepository.findAllByOrderByNameAsc();
	}

	public List<Group> getGroupListByOrganization(Organization organization){
		return groupRepository.findByOrganization(organization);
	}
}
