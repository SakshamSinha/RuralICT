package app.entities.broadcast;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import app.entities.Group;
import app.entities.Organization;
import app.entities.User;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@DiscriminatorValue("video")
@JsonTypeName("video")
public class VideoBroadcast extends Broadcast {
	private static final long serialVersionUID = 1L;

	public VideoBroadcast() {
	}

	public VideoBroadcast(Organization organization, Group group, User user, String mode, boolean askFeedback,
			boolean askOrder, boolean askResponse) {

		super(organization, group, user, "text", mode, askFeedback, askOrder, askResponse, true);
	}

}
