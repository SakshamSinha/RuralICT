package app.entities.message;

import java.sql.Timestamp;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import app.entities.Group;
import app.entities.Order;
import app.entities.User;
import app.entities.broadcast.Broadcast;

@Entity
@DiscriminatorValue("binary")
public class BinaryMessage extends Message {
	private static final long serialVersionUID = 1L;

	public BinaryMessage() {
	}

	public BinaryMessage(User user, Broadcast broadcast,Group group, Timestamp time, String mode, String type, boolean response,
			Order order) {
		super(user, broadcast, group,time, mode, "binary", type, response, order);
	}

}
