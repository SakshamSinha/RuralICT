package app.data.eventhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import app.data.repositories.GroupMembershipRepository;
import app.entities.Group;
import app.entities.GroupMembership;

@RepositoryEventHandler
public class GroupMembershipEventHandler {

	@Autowired
	GroupMembershipRepository membershipRepository;

	/**
	 * When a user is added to a group, also add that user to the group's parent group if any.
	 * @param membership The group membership that was created.
	 */
	@HandleAfterCreate
	public void handleGroupMembershipCreate(GroupMembership membership) {
		Group group = membership.getGroup();
		Group parentGroup = group.getParentGroup();

		if (parentGroup != null) {
			membershipRepository.save(new GroupMembership(parentGroup, membership.getUser()));
		}
	}

}
