package app.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import app.data.repositories.GroupRepository;
import app.entities.Group;

public class GroupService {
	@Autowired
	GroupRepository groupRepository;
	
	public Group getGroup(int groupId){
		return groupRepository.findOne(groupId);
	}
	
	public void addGroup(Group group){
		groupRepository.save(group);
	}
	
	public void removeGroup(Group group){
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

}
