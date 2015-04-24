package app.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.entities.Group;
import app.entities.broadcast.Broadcast;
import app.rest.repositories.GroupRepository;



@Controller
@RequestMapping("/web/{org}")
public class OutgoiningSmsList {

	@Autowired
	GroupRepository groupRepository;


	@RequestMapping(value="/outgoiningSmsList/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String outgoingSmsList(@PathVariable String org, @PathVariable int groupId, Model model ) {

		Group group = groupRepository.findOne(groupId);
		List<Broadcast> broadcastList = group.getBroadcasts();
		List broadcastedMessage =  new ArrayList<>();
		for(Broadcast message : broadcastList){
			System.out.println(message.getFormat());
			
			if(message.getFormat().equalsIgnoreCase("text")){
				broadcastedMessage.add(message);
				model.addAttribute("outgoiningSms", broadcastedMessage);

			}


		}


		return "outgoiningSmsList";
	}

}
