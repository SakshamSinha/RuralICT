package app.entities.broadcast;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import app.entities.Group;
import app.entities.Organization;
import app.entities.User;

import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@DiscriminatorValue("text")
@JsonTypeName("text")
public class TextBroadcast extends Broadcast {
	private static final long serialVersionUID = 1L;

	@Column(name="text_content")
	private String textContent;

	public TextBroadcast() {
	}

	public TextBroadcast(Organization organization, Group group, User user, String mode, boolean askFeedback,
			boolean askOrder, boolean askResponse, boolean appOnly, String textContent) {

		super(organization, group, user, "text", mode, askFeedback, askOrder, askResponse, appOnly);
		this.textContent = textContent;
	}

	public String getTextContent() {
		return this.textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

}
