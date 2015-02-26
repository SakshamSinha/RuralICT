package webapp.model.entities.broadcast;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("video")
public class VideoBroadcast extends Broadcast {
	private static final long serialVersionUID = 1L;

	public VideoBroadcast() {
	}

}
