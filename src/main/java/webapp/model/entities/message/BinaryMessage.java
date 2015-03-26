package webapp.model.entities.message;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import webapp.model.entities.Order;
import webapp.model.entities.User;
import webapp.model.entities.broadcast.Broadcast;

@Entity
@DiscriminatorValue("binary")
public class BinaryMessage extends Message {
	private static final long serialVersionUID = 1L;

	public BinaryMessage() {
	}

	public BinaryMessage(User user, Broadcast broadcast, String mode, String type, boolean response, Order order) {
		super(user, broadcast, mode, "binary", type, response, order);
	}

}
