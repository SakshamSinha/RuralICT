package app.entities.message;

import java.sql.Timestamp;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonTypeName;

=======
import app.entities.Group;
>>>>>>> f5475c82924bfb9e2e4cb69e5c519ca13a0b054a
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

	public BinaryMessage(User user, Broadcast broadcast, Timestamp time, Group group, String mode, String type, boolean response,
			Order order) {
		super(user, broadcast, time, group, mode, "binary", type, response, order);
	}

	
	


}
