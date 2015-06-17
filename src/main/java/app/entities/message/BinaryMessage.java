package app.entities.message;

import java.sql.Timestamp;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import app.entities.Order;
import app.entities.User;
import app.entities.broadcast.Broadcast;

@Entity
@DiscriminatorValue("binary")
@JsonTypeName("binary")
public class BinaryMessage extends Message {
	private static final long serialVersionUID = 1L;

	public BinaryMessage() {
	}

	public BinaryMessage(User user, Broadcast broadcast, Timestamp time, String mode, String type, boolean response,
			Order order) {
		super(user, broadcast, time, mode, "binary", type, response, order);
	}

	
	


}
