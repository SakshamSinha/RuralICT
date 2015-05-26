package app.business.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import app.data.repositories.GroupRepository;
import app.entities.Group;
import app.entities.Organization;

public class GroupService {
	@Autowired
	GroupRepository groupRepository;
	
	Group getGroup(int groupId){
		return groupRepository.findOne(groupId);
	}
	
	void addGroup(Group group){
		groupRepository.save(group);
	}
	
	void removeGroup(Group group){
		groupRepository.delete(group);
	}
	
	void updateParentGroup(Group group,Group newParentGroup){
		//what if the newParentGroup doesnt exist
		group.getParentGroup().removeSubGroup(group);
		group.setParentGroup(newParentGroup);
	}
	
	public List<Group> getAllGroupList(){
		return groupRepository.findAll();
	}
	
	public List<Group> getAllGroupListSortedByName(){
		/*
		 * The query can also be done by the statement public List<Organization> findAllByOrderByNameAsc(); in the repository as well. 
		 */
		return groupRepository.findAllByOrderByNameAsc();
	}

}
