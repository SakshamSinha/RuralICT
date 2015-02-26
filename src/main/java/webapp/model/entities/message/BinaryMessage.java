package webapp.model.entities.message;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("binary")
public class BinaryMessage extends Message {
	private static final long serialVersionUID = 1L;

	public BinaryMessage() {
	}

}
