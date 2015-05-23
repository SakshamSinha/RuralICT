package app.business.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import app.data.repositories.GroupMembershipRepository;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.User;


public class GroupMembershipService {
	@Autowired
	GroupMembershipRepository groupMembershipRepository;
	
	public static Comparator<GroupMembership> GroupMembershipUserComparator = new Comparator<GroupMembership>() {

		public int compare(GroupMembership g1, GroupMembership g2) {
		   String UserName1 = g1.getUser().getName().toLowerCase();	   
		   String UserName2 = g2.getUser().getName().toLowerCase();

		   //ascending order
		   return UserName1.compareTo(UserName2);  
	    }};
	    
	public static Comparator<GroupMembership> GroupMembershipGroupComparator = new Comparator<GroupMembership>() {

			public int compare(GroupMembership g1, GroupMembership g2) {
			   String GroupName1 = g1.getGroup().getName().toLowerCase();	   
			   String GroupName2 = g2.getUser().getName().toLowerCase();

			   //ascending order
			   return GroupName1.compareTo(GroupName2);   
		    }};
		
		
		
	boolean isUserGroupMembership(User user, Group group){
		List<GroupMembership> bool= groupMembershipRepository.findByUserAndGroup(user,group);
		if (bool.equals("null"))
			return false;
		else
			return true;
		
	}
	
	List<GroupMembership> getGroupMembershipListByUser(User user){
		return (new ArrayList<GroupMembership>(groupMembershipRepository.findByUser(user)));
	}
	List<GroupMembership> getGroupMembershipListByUserSorted(User user){
		//There can be a better method to do this using custom queries
		List<GroupMembership> groupMembershipListSortedByUserName = getGroupMembershipListByUser(user);
		Collections.sort(groupMembershipListSortedByUserName,GroupMembershipUserComparator);
		return groupMembershipListSortedByUserName;
	}
	
	List<GroupMembership> getGroupMembershipListByGroup(Group group){
		return (new ArrayList<GroupMembership>(groupMembershipRepository.findByGroup(group)));
	}
	
	List<GroupMembership> getGroupMembershipListByGroupSorted(Group group){
		List<GroupMembership> groupMembershipListSortedByGroupName = getGroupMembershipListByGroup(group);
		Collections.sort(groupMembershipListSortedByGroupName,GroupMembershipGroupComparator);
		return groupMembershipListSortedByGroupName;
	}
	
	void addGroupMembership(GroupMembership groupMembership){
		groupMembershipRepository.save(groupMembership);
	}
	
	void removeGroupMembership(GroupMembership groupMembership){
		groupMembershipRepository.delete(groupMembership);
	}
	
	GroupMembership getGroupMembershipById(int groupMembershipId){
		return groupMembershipRepository.findOne(groupMembershipId);
	}
	
	List<GroupMembership> getAllGroupMembershipList(){
		return (new ArrayList<GroupMembership>(groupMembershipRepository.findAll()));
	}
	
	List<GroupMembership> getAllGroupMembershipListSortedByUserName(){
		List<GroupMembership> allGroupMembershipListSortedByUserName = getAllGroupMembershipList();
		Collections.sort(allGroupMembershipListSortedByUserName,GroupMembershipUserComparator);
		return allGroupMembershipListSortedByUserName;
	}
	List<GroupMembership> getAllGroupMembershipListSortedByGroupName(){
		List<GroupMembership> allGroupMembershipListSortedByGroupName = getAllGroupMembershipList();
		Collections.sort(allGroupMembershipListSortedByGroupName,GroupMembershipGroupComparator);
		return allGroupMembershipListSortedByGroupName;
		
	}
	

	int getGroupMembershipId(GroupMembership groupMembership){
		return groupMembership.getGroupMembershipId();
	}
	

	

}
