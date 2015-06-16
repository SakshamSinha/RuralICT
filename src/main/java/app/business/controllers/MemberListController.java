package app.business.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import app.business.services.UserPhoneNumberService;
import app.business.services.springcontext.SpringContextBridge;
import app.data.repositories.GroupRepository;
import app.data.repositories.UserPhoneNumberRepository;
import app.entities.Group;
import app.entities.GroupMembership;
import app.entities.User;
import app.entities.UserPhoneNumber;

@Controller
@RequestMapping("/web/{org}")
public class MemberListController {

	GroupRepository groupRepository;

	UserPhoneNumberRepository userPhoneNumberRepository;

	static private class UserViewService {
		private User user;
		private UserPhoneNumber phone;

		public UserViewService(User user, UserPhoneNumber primaryPhoneNo ) {
			this.user = user;
			this.phone = primaryPhoneNo;

		}
		public User getUser() {
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public UserPhoneNumber getPhone() {
			return phone;
		}
		public void setPhone(UserPhoneNumber phone) {
			this.phone = phone;

		}
	}



	@RequestMapping(value="/memberList/{groupId}")
	@PreAuthorize("hasRole('ADMIN'+#org)")
	@Transactional
	public String memberList(@PathVariable String org, @PathVariable int groupId, Model model) {

		Group group = groupRepository.findOne(groupId);
		List<UserViewService> rows = new ArrayList<UserViewService>();
		List<GroupMembership> groupMemberShipList = group.getGroupMemberships();
		for(GroupMembership memberShip : groupMemberShipList)
		{
	        
			UserPhoneNumberService userPhoneNumberService = SpringContextBridge.services().getUserPhoneNumberService();
			UserPhoneNumber users = userPhoneNumberService.getUserPrimaryPhoneNumber(memberShip.getUser());
			UserViewService row = new UserViewService(memberShip.getUser(), users);
			rows.add(row);

		}
		model.addAttribute("groupWiseMemberShip",rows);

		return "groupWiseMember";
	}

}
