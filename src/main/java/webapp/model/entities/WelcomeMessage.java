package webapp.model.entities;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the welcome_message database table.
 * 
 */
@Entity
@Table(name="welcome_message")
public class WelcomeMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="welcome_message_id")
	private int welcomeMessageId;

	private String locale;

	//bi-directional many-to-one association to Organization
	@ManyToOne
	@JoinColumn(name="organization_id")
	private Organization organization;

	//bi-directional many-to-one association to Voice
	@ManyToOne
	@JoinColumn(name="voice_id")
	private Voice voice;

	public WelcomeMessage() {
	}

	public WelcomeMessage(Organization organization, String locale, Voice voice) {
		this.organization = organization;
		this.locale = locale;
		this.voice = voice;
	}

	public int getWelcomeMessageId() {
		return this.welcomeMessageId;
	}

	public void setWelcomeMessageId(int welcomeMessageId) {
		this.welcomeMessageId = welcomeMessageId;
	}

	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Voice getVoice() {
		return this.voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

}