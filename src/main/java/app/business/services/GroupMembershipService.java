package app.business.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.data.repositories.GroupMembershipRepository;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.Organization;
import app.entities.User;

@Service
public class GroupMembershipService {
	@Autowired
	GroupMembershipRepository groupMembershipRepository;
		
	public GroupMembership getUserGroupMembership(User user, Group group){
		return groupMembershipRepository.findByUserAndGroup(user,group);
	}
	
	public List<GroupMembership> getGroupMembershipListByUser(User user){
		return groupMembershipRepository.findByUser(user);
	}
	
	public List<GroupMembership> getGroupMembershipListByUserSortedByGroupName(User user){
		return groupMembershipRepository.findByUserOrderByGroup_NameAsc(user);
	}
	
	public List<GroupMembership> getGroupMembershipListByGroup(Group group){
		return groupMembershipRepository.findByGroup(group);
	}
	
	public List<GroupMembership> getGroupMembershipListByGroupSortedByUserName(Group group){
		return groupMembershipRepository.findByGroupOrderByUser_NameAsc(group);
	}
	
	@Transactional
	public void addGroupMembership(GroupMembership groupMembership){
		groupMembershipRepository.save(groupMembership);
		
		/**
		 * To ensure that no null groups are added once parent group is added
		 */
		if(groupMembership.getGroup().getParentGroup() != null) {
			/**
			 * This mimicks the event handler for groupMemberships
			 */
			this.addGroupMembership(new GroupMembership(groupMembership.getGroup().getParentGroup(), groupMembership.getUser()));
		}
	}
	
	public void removeGroupMembership(GroupMembership groupMembership){
		groupMembershipRepository.delete(groupMembership);
	}
	
	public GroupMembership getGroupMembershipById(int groupMembershipId){
		return groupMembershipRepository.findOne(groupMembershipId);
	}
	
	public List<GroupMembership> getAllGroupMembershipList(){
		return groupMembershipRepository.findAll();
	}
	
	public List<GroupMembership> getAllGroupMembershipListSortedByUserName(){
		return groupMembershipRepository.findAllByOrderByUser_NameAsc();
	}
	
	public List<GroupMembership> getAllGroupMembershipListSortedByGroupName(){
		return groupMembershipRepository.findAllByOrderByGroup_NameAsc();
	}
	
	public List<GroupMembership> getGroupMembershipListByUserAndOrganization(User user,Organization organization){
		return groupMembershipRepository.findByUserAndGroup_Organization(user,organization);
	}
	
	public List<Group> getGroupListByUserAndOrganization(User user,Organization organization){
		List<GroupMembership> groupMembershipList = getGroupMembershipListByUserAndOrganization(user, organization);
		List<Group> groupList = new ArrayList<Group>();
		for(GroupMembership groupMembership : groupMembershipList) {
			groupList.add(groupMembership.getGroup());
		}
		return groupList;
	}
	
	public List<GroupMembership> getGroupsByUserAndOrganizationSorted(User user,Organization organization){
		return groupMembershipRepository.findByUserAndGroup_OrganizationOrderByGroup_NameAsc(user,organization);
	}
	

}
