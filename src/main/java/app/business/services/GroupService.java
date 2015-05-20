package app.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.entities.Group;
import app.data.repositories.GroupRepository;

@Service
public class GroupService {
	
	@Autowired
	GroupRepository groupRepository;
	
	public Group getGroup(int groupId){
	
		Group group = groupRepository.findOne(groupId);
		return group;
	}
}
