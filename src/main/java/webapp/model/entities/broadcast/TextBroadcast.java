package webapp.model.entities.broadcast;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("text")
public class TextBroadcast extends Broadcast {
	private static final long serialVersionUID = 1L;

	@Column(name="text_content")
	private String textContent;

	public TextBroadcast() {
	}

	public String getTextContent() {
		return this.textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

}
