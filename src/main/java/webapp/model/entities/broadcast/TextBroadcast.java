package webapp.model.entities.broadcast;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import webapp.model.entities.Group;
import webapp.model.entities.Organization;
import webapp.model.entities.User;

@Entity
@DiscriminatorValue("text")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="broadcastId")
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
