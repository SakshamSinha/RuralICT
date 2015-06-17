package app.business.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import app.data.repositories.GroupRepository;
import app.entities.Group;
import app.entities.broadcast.Broadcast;


@Controller
@RequestMapping("/web/{org}")
public class BroadcastVoiceController {


	@Autowired
	GroupRepository groupRepository;

	@RequestMapping(value="/broadcastVoiceMessages/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String broadcastVoiceMessages(@PathVariable String org, @PathVariable int groupId, Model model) {

		Group group = groupRepository.findOne(groupId);
		List<Broadcast> broadcastList = group.getBroadcasts();

		for(Broadcast broadcast: broadcastList){
			if(broadcast.getFormat().equalsIgnoreCase("voice") )
			{
				model.addAttribute("broadcast",broadcast);
			}
		}

		return "broadcastVoice";
	}

}
