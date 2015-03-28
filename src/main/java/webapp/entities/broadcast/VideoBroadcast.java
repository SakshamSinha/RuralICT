package webapp.entities.broadcast;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import webapp.entities.Group;
import webapp.entities.Organization;
import webapp.entities.User;

@Entity
@DiscriminatorValue("video")
public class VideoBroadcast extends Broadcast {
	private static final long serialVersionUID = 1L;

	public VideoBroadcast() {
	}

	public VideoBroadcast(Organization organization, Group group, User user, String mode, boolean askFeedback,
			boolean askOrder, boolean askResponse) {

		super(organization, group, user, "text", mode, askFeedback, askOrder, askResponse, true);
	}

}
