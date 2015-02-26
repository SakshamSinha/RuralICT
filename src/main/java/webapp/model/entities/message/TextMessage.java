package webapp.model.entities.message;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import webapp.model.entities.Order;
import webapp.model.entities.User;
import webapp.model.entities.broadcast.Broadcast;

@Entity
@DiscriminatorValue("text")
public class TextMessage extends Message {
	private static final long serialVersionUID = 1L;

	@Column(name="text_content")
	private String textContent;

	@Column(name="text_time")
	private Timestamp textTime;

	public TextMessage() {
	}

	public TextMessage(User user, Broadcast broadcast, String mode, String type, boolean response, Order order,
			String textContent, Timestamp textTime) {

		super(user, broadcast, mode, "text", type, response, order);
		this.textContent = textContent;
		this.textTime = textTime;
	}

	public String getTextContent() {
		return this.textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public Timestamp getTextTime() {
		return this.textTime;
	}

	public void setTextTime(Timestamp textTime) {
		this.textTime = textTime;
	}

}
