package app.entities.message;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import app.entities.Group;
import app.entities.Order;
import app.entities.User;
import app.entities.broadcast.Broadcast;

@Entity
@DiscriminatorValue("text")
public class TextMessage extends Message {
	private static final long serialVersionUID = 1L;

	@Column(name="text_content")
	private String textContent;

	public TextMessage() {
	}

	public TextMessage(User user, Broadcast broadcast, Group group, String mode, String type, boolean response, Order order,
			String textContent, Timestamp textTime) {

		super(user, broadcast, textTime, group, mode, "text", type, response, order);
		this.textContent = textContent;
	}

	public String getTextContent() {
		return this.textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

}
